using Reflow.Provider;
using Reflow.Serialization;
using Reflow.Tasks;
using Reflow.Transformation;
using System;
using System.Collections.Generic;
using System.IO;
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
            // Test DB Path

            string test = "text"; // "access";
            string sourceConnectionString = string.Empty;

            ILinkProvider sourceProvider = null;
            if (test == "access")
            {
                string accessDBPath = AppDomain.CurrentDomain.BaseDirectory + "db1.accdb";
                sourceConnectionString = string.Format("Provider=Microsoft.ACE.OLEDB.12.0;Data Source={0};Persist Security Info=False;",
                    accessDBPath);
                sourceProvider = new Provider.Access.AccessLinkProvider();
            }
            else if (test == "text")
            {
                // TODO: Test and implement text driver
                string txtDBPath = AppDomain.CurrentDomain.BaseDirectory + "text\\HumanResources_Employee.txt";
                sourceConnectionString = "@File="+txtDBPath + ";@Type=Delimited;RowSeperator=\r\n;ColumnSeperator=,;FirstRowHasNames=True";
                // TODO: Fixed length - Need to implement
                //sourceConnectionString = "@File="+txtDBPath + ";@Type=Fixed;";
                sourceProvider = new Provider.Text.TextProvider();
            } 

            IDataLink linkSource = sourceProvider.CreateLink ( sourceConnectionString);

            ILinkProvider sqlProvider = new Provider.SqlServer.SqlLinkProvider();
            string destConnectionString = "Server=localhost;Database=REflow;Trusted_Connection=True;";
            IDataLink linkDestination = sqlProvider.CreateLink(destConnectionString);

            // Driver={Microsoft Text Driver (*.txt; *.csv)};Dbq=c:\txtFilesFolder\;Extensions=asc,csv,tab,txt;

            string selectQuery = "Select * from HumanResources_Employee";

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
            ILinkReader reader = sourceProvider.CreateReader(linkSource, selectQuery); // Since we are using same query 
            ILinkWriter writer = sqlProvider.CreateWriter(linkDestination, task2.TableName); //  Dest table
            task3.Input = reader;
            task3.Output = writer;          
            /* We would notmally create column mapping here and and map source and 
             * destination and put custom expressions if needed. But since we are doing a 
             * direct table copy, we can just use Automap property. */
            // ColumnMappings maps = new ColumnMappings();
            task3.IsAutoMap = true;
            task3.TableName = task2.TableName;
            
            // TODO: Add column mapping support
            // Add scripting transformation 
            ColumnMap map = new ColumnMap();
            map.Destination = "Title";
            Expression exp = new Expression() { Code = "NationalIDNumber & \" \" &  UCASE(Title) & CSTR(LEN(Title))"};
            map.TransformExpression = exp;
            task3.Mapping.Add(map);

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

/*
ReflowJob job = new ReflowJob();
job.Tasks.Add(task1);
;8job.Tasks.Add(task2);
job.Tasks.Add(task3);

string filepath = AppDomain.CurrentDomain.BaseDirectory + "pack.xml";
if (File.Exists ( filepath)) { File.Delete(filepath);}
using (FileStream fs = new FileStream(filepath, FileMode.CreateNew))
{
    ReflowPackage.SavePackage(fs, job);
}*/

