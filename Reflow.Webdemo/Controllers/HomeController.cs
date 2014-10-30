using Reflow.Provider;
using Reflow.Provider.SqlServer;
using Reflow.Provider.Text;
using Reflow.Tasks;
using Reflow.Transformation;
using Reflow.WebDemo.Models;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Mvc;
using System.Web.Script.Serialization;

namespace Reflow.WebDemo.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Upload()
        {
            string tempfile = System.IO.Path.GetTempFileName();
            
            HttpPostedFileBase csv = Request.Files["csv"];
            
            if (csv != null)
                csv.SaveAs(tempfile);
            Session["import_file_path"] = tempfile;

            return RedirectToAction("Index");
        }

        [HttpPost]
        public ActionResult Mapping()
        {
            string tempfileName = Session["import_file_path"].ToString();
            bool isFirstRowHeader = Request["IsFirstRowHeader"] == "on" ? true : false;
            string rowDelimiter = Request["RowDelimiter"];
            string columnDelimiter= Request["ColumnDelimiter"];
            string tableName = Request["TableName"];
            
            ImportInfo imfo = new ImportInfo();
            imfo.DestinationTableName = tableName;
            imfo.ImportFileLocation = tempfileName;
            imfo.FirstRowHasColumnNames = isFirstRowHeader;
            imfo.RowDelimiter = TemplateToString(rowDelimiter);
            imfo.ColumnDelimiter = TemplateToString(columnDelimiter);

            StringBuilder sbConn = new StringBuilder();
            sbConn.Append("@File=");
            sbConn.Append(tempfileName);
            sbConn.Append(";@Type=Delimited;");
            sbConn.Append("RowSeperator=");
            sbConn.Append(imfo.RowDelimiter);
            sbConn.Append(";ColumnSeperator=");
            sbConn.Append(imfo.ColumnDelimiter);
            sbConn.Append(";FirstRowHasNames=");
            sbConn.Append(imfo.FirstRowHasColumnNames.ToString());

            SqlDataLink sqlLink = new SqlDataLink();
            sqlLink.Initialize(ConfigurationManager.AppSettings["SqlConnection"]);
            sqlLink.Connect();
            ColumnDefinition[] sqlCds = sqlLink.GetSchema("SELECT * FROM " + imfo.DestinationTableName);
            imfo.DestinationColumns = sqlCds;


            TextLink tl = new TextLink();
            tl.Initialize(sbConn.ToString());
            tl.Connect();
            
            ColumnDefinition[] cds = tl.GetSchema("");
            List<string> columns = new List<string>();
            foreach (ColumnDefinition cd in cds)
            {
                columns.Add(cd.ColumnName);
            }
            imfo.SourceColumns = columns.ToArray();
            return View(imfo);
             
        }

        [HttpPost]
        public ActionResult Import()
        {
            string importJson = Request["hidImport"];
            JavaScriptSerializer serializer = new JavaScriptSerializer();
            dynamic jsonObject = serializer.Deserialize<dynamic>(importJson);
            //ImportInfo jsonObject;
            // Do the import
            string sourceConnectionString = "@File=" + jsonObject["ImportFileLocation"] + ";@Type=Delimited;RowSeperator=" +
               jsonObject["RowDelimiter"]  +";ColumnSeperator="+ jsonObject["ColumnDelimiter"] +";FirstRowHasNames=" + 
               jsonObject["FirstRowHasColumnNames"].ToString();

            string destConnectionString = ConfigurationManager.AppSettings["SqlConnection"];

            ILinkProvider sourceProvider = null;
            ILinkProvider destProvider = null;

            destProvider = new SqlLinkProvider();
            sourceProvider = new TextProvider();

            IDataLink linkSource = sourceProvider.CreateLink(sourceConnectionString);
            IDataLink linkDestination = destProvider.CreateLink(destConnectionString);


            SqlDataLink sqlLink = new SqlDataLink();
            sqlLink.Initialize(ConfigurationManager.AppSettings["SqlConnection"]);
            sqlLink.Connect();

            DataFlowTask copy = new DataFlowTask() { Name = "DataCopyTask" };
            ILinkReader reader = sourceProvider.CreateReader(linkSource, ""); // Since we are using same query 
            string destTable = jsonObject["DestinationTableName"].ToString();
            ILinkWriter writer = destProvider.CreateWriter(linkDestination, destTable); //  Dest table

            copy.Input = reader;
            copy.Output = writer;
            copy.TableName = destTable;
            copy.IsAutoMap = false;

            dynamic maps = jsonObject["Maps"];
            foreach (dynamic map in maps)
            {
                ColumnMap cmap = new ColumnMap();
                cmap.Destination = map["FieldName"];
                Expression exp = new Expression() { Code = map["TargetExpression"] };
                cmap.TransformExpression = exp;
                copy.Mapping.Add(cmap);
            }

            StringBuilder sb = new StringBuilder();

            ReflowEngine engine = new ReflowEngine();
            engine.Tasks.Add(copy);
            ExecutionEventListener eventListener = new ExecutionEventListener();
            eventListener.OnTaskExecutionEvent += delegate(string taskname, string eventName, string description)
            {
                sb.Append(string.Format("{0,15} |{1,10} | {2} <br>", taskname, eventName, description));
            };
            eventListener.LoggingLevel = ExecutionEventListener.LogLevel.Verbose;
            engine.Execute(eventListener);

            linkSource.Disconnect();
            linkDestination.Disconnect();

            //System.IO.File.Delete(jsonObject["ImportFileLocation"]);
            Session.Remove("import_file_path");


            this.ViewBag.Log= sb.ToString();
            return View();
        }




        private string TemplateToString(string value)
        {
            return value.Replace("{tab}", "\t").Replace("{cr lf}", "\r\n");
        }


    }
}
