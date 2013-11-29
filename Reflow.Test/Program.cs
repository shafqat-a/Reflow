using Reflow.Tasks;
using System;
using System.Collections.Generic;
using System.Text;

namespace Reflow.Test
{
    class Program
    {
        static void Main(string[] args)
        {
            // Read a table from sql server via a query, then make an exact copy 
            // of that table.
            // Step 1. Read source table schema
            // Step 2. Create exact same schema on destination
            // Step 3. Copy Data

            // Prepare reflow engine
            ReflowEngine engine = new ReflowEngine();
            string sourceConnectionString = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=f:\\Users\\shafqat\\Downloads\\AdventureWorks.accdb;Persist Security Info=False;";
            ILinkProvider sqlProvider = new SqlServer.SqlLinkProvider();
            ILinkProvider oleProvider = new OleDb.OleDbLinkProvider();
            IDataLink linkSource = oleProvider.CreateLink ( sourceConnectionString);

            string destConnectionString = "Server=localhost;Database=AdventureWorks2012;Trusted_Connection=True;";
            IDataLink linkDestination = sqlProvider.CreateLink(destConnectionString);

            string selectQuery = "Select * from HumanResources_Employee";
            //string selectQuery = "Select * from Production_BillOfMaterials";

            // First lets discover the schema of the query [source table]
            DiscoverSchemaTask task1 = new DiscoverSchemaTask();
            task1.Name = "DiscoverSchema";
            task1.Query = selectQuery;
            task1.Link = linkSource;

            // Then we need to create a table in the destinaton database. 
            TableCreateTask task2 = new TableCreateTask();
            task2.Name = "CreateTableAbc";
            task2.TableName = "abc";
            task2.Link = linkDestination;
            task2.ShouldDropExisting = true;
            // Now we need to map task1 output to be the input of task2 since
            // task1 will discover column names and we need to map discovered 
            // columns to the Columns property of task 2. We can do this one of 
            // two way. 

            // First, we can use a delegate to call onbeforeexecute for task2 and 
            // assign task result of task 1 to the column property of task 2. But that 
            // would be problematic for the scenario where we want to serialize the task
            // and deserialize.

            // Second, We can we task link object map output of task1 to go into certain
            // properties of task 2. This can be serialized. We would use this technique

            TaskLink link = new TaskLink();
            link.LastTask = task1;
            link.NextTask = task2;
            // Map the output called DiscoveredColumns ( found in TaskResult.Output["DiscoveredColumns"]) to 
            // task2.Columns once task1 has been executed
            link.TaskPipe["Columns"] = "Columns";
            link.Bind();

            // Now we will execute the data copy task. Will need to do the column mapping
            // Interestingly, since our output columns are same as input columns at source, 
            // we can use the automap property to map the columns automatically.


            DataFlowTask task3 = new DataFlowTask() { Name = "DataCopyTask" };
            ILinkReader reader = oleProvider.CreateReader(linkSource, selectQuery); // Since we are using same query 
            ILinkWriter writer = sqlProvider.CreateWriter(linkDestination, task2.TableName); //  Dest table
            task3.Input = reader;
            task3.Output = writer;          
            /* We would notmally create column mapping here and and map source and 
             * destination and put custom expressions if needed. But since we are doing a 
             * direct table copy, we can just use Automap property. */
            // ColumnMappings maps = new ColumnMappings();
            task3.IsAutoMap = true;
            task3.TableName = task2.TableName;

            engine.Tasks.Add(task1);
            engine.Tasks.Add(task2);
            engine.Tasks.Add(task3);
            
            ExecutionEventListener eventListener = new ExecutionEventListener();
            eventListener.OnTaskExecutionEvent+= delegate (string taskname, string eventName, string description)
            {
                Console.WriteLine(string.Format("{0,15} |{1,10} | {2}", taskname, eventName, description));
            };
            eventListener.LoggingLevel = ExecutionEventListener.LogLevel.Verbose;
            engine.Execute(eventListener);
            

        }
    }
}
