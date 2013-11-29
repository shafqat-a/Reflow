using System;
using System.Collections.Generic;
using System.Data.OleDb;
using System.Linq;
using System.Text;

namespace Reflow.Helper
{
    internal class TableGenerator
    {
        private IColumnTypeTranslator _translator = null;

        public TableGenerator(IColumnTypeTranslator translator)
        {
            _translator = translator;
        }

        /// <summary>
        /// Drops the table
        /// </summary>
        /// <param name="objectType"></param>
        /// <returns></returns>
        public string GenerateTableDropScript(string tableName)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(string.Format(" DROP TABLE {0}{1}{2}", _translator.OpeningIdentifier, tableName, _translator.ClosingIdentifier));
            return sb.ToString();
        }


        /// <summary>
        /// Generates table creation script for a database
        /// </summary>
        /// <param name="objectType"></param>
        /// <returns></returns>
        public string GenerateTableScript(string tableName, ColumnDefinition[] columns)
        {
            StringBuilder sb = new StringBuilder();

            StringBuilder sbAfterTable = new StringBuilder();
            StringBuilder sbBeforeTable = new StringBuilder();
            StringBuilder sbPostGeneration = new StringBuilder();

            sb.Append(string.Format(" CREATE TABLE {0}{1}{2}", _translator.OpeningIdentifier, tableName, _translator.ClosingIdentifier));
            sb.Append(" ( \n ");


            foreach (ColumnDefinition column in columns)
            {
                StringBuilder sbField = new StringBuilder();

                ProcessColumnForConstraint(column, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
                string sql = GetCreateSQLForColumn(column);
                if (!sql.Equals(string.Empty)) 
                {
                    sb.Append("\t");
                    sb.Append(sql);
                    if (sbField.Length > 0)
                    {
                        sb.Append(" " + sbField.ToString() + " ");
                    }
                    sb.Append(",\n");
                }
            }
            sb.Remove(sb.Length - 2, 1);

            // TODO : Later
            //ProcessTypeForConstraints(objectType, sbBeforeTable, null, sbAfterTable, sbPostGeneration);

            StringBuilder sbFinal = new StringBuilder();
            if (sbBeforeTable.Length > 0)
            {
                sbFinal.Append(sbBeforeTable.ToString());
            }
            sbFinal.Append(sb.ToString());
            if (sbAfterTable.Length > 0)
            {
                sbFinal.Append(sbAfterTable.ToString());
            }
            sbFinal.Append(" ) \n");

            if (sbPostGeneration.Length > 0)
            {
                sbFinal.Append(sbPostGeneration.ToString());
            }

            return sbFinal.ToString();
        }

        /*
        public void ProcessTypeForConstraints(Type typ, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration)
        {
            ProcessTypeForConstraint<UniqueFieldsConstraintAttribute>(typ, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
            ProcessTypeForConstraint<IndexAttribute>(typ, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
        }

        public bool ProcessTypeForConstraint<T>(Type typ, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration)
            where T : ConstraintAttribute
        {
            T constraint = (T)Util.CommonHelper.GetAttribute(typ, typeof(T));
            if (constraint != null)
            {
                constraint.GenerateSql(_provider, typ, null, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
                return true;
            }
            return false;
        }*/

        private void ProcessColumnForConstraint(ColumnDefinition pinfo, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration)
        {
            //ProcessColumnForConstraint<PrimaryKeyAttribute>(pinfo, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
            //ProcessColumnForConstraint<ForiegnKeyAttribute>(pinfo, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
        }


        /// <summary>
        /// Generate the constatn string 
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="pinfo"></param>
        /// <param name="sbBeforeTable"></param>
        /// <param name="sbField"></param>
        /// <param name="sbAfterTable"></param>
        /// <returns></returns>
        //private bool ProcessColumnForConstraint<T>(ColumnDefinition pinfo, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration) where T : ConstraintAttribute
        //{            
           // T constraint = (T)Util.CommonHelper.GetAttribute(pinfo, typeof(T));
           // if (constraint != null)
           // {
           //     constraint.GenerateSql(_provider, pinfo.DeclaringType, pinfo, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
           //     return true;
            //}
        //    return false;
        //}

        /*
        private void GetExtraSqlForType(Type objectType, StringBuilder sb)
        {
            object[] attributes = objectType.GetCustomAttributes(typeof(UniqueFieldsConstraintAttribute), true);
            if (attributes.Length > 0)
            {
                UniqueFieldsConstraintAttribute attrib = attributes[0] as UniqueFieldsConstraintAttribute;
                string[] properties = attrib.Properties;
                sb.Append("UNIQUE (");
                ISqlDataTypeTranslator translator = _provider.GetDataTypeTranslator();
                int i = 0;
                foreach (string propertyName in properties)
                {
                    PropertyInfo pinfo = objectType.GetProperty(propertyName);
                    string fieldName = propertyName;
                    if (i > 0)
                    {
                        sb.Append(",");
                    }
                    sb.Append(string.Format("{0}{1}{2}", translator.OpeningIdentifier, fieldName, translator.ClosingIdentifier));
                    i++;
                }
                sb.Append(")");
            }
        }*/


        private string GetCreateSQLForColumn(ColumnDefinition pinfo)
        {

            
            string columnName = string.Format("{0}{1}{2}", _translator.OpeningIdentifier, pinfo.ColumnName, _translator.ClosingIdentifier);

            //if (dta == null)
            //{
            //    dta = GetDefaultDataTypeProperty(pinfo);
            //}

            // return columnName + " " + dta.SqlTypeString;
            return columnName + " " + _translator.GetDataTypeExpression(pinfo);

        }

        /*
        private DataTypeAttribute GetDefaultDataTypeProperty(PropertyInfo pinfo)
        {
            StringBuilder sb = new StringBuilder();
            if (!
                    (
                    pinfo.PropertyType.IsPrimitive
                    | pinfo.PropertyType.Equals(typeof(string))
                    | pinfo.PropertyType.Equals(typeof(DateTime))
                    | pinfo.PropertyType.Equals(typeof(System.Guid))
                    )
                )
            {
                return new AnsiStringAttribute(2045);
            }
            else
            {
                switch (pinfo.PropertyType.FullName)
                {
                    case "System.String":
                        return new AnsiStringAttribute(2045);
                        break;
                    case "System.Char":
                        return new AnsiStringAttribute(100);
                        break;
                    case "System.Int32":
                        if (IsPrimary(pinfo))
                        {
                            return new Int32Attribute(true, false, true);
                        }
                        else
                        {
                            return new Int32Attribute();
                        }
                        break;
                    case "System.Int64":
                        if (IsPrimary(pinfo))
                        {
                            return new Int64Attribute(false, true, 0);
                        }
                        else
                        {
                            return new Int64Attribute();
                        }
                        break;

                    case "System.Decimal":
                        if (IsPrimary(pinfo))
                        {
                            return new DecimalAttribute(15, 0);
                        }
                        else
                        {
                            return new DecimalAttribute(15, 3);
                        }
                        break;
                    case "System.Double":
                        throw new InvalidOperationException("Double value now allowed yet");
                        break;
                    case "System.DateTime":
                        return new DateTimeAttribute(true);
                        break;
                    case "System.int64":
                        return new Int16Attribute();
                        break;
                    case "System.Byte[]":
                        return new BinaryAttribute(1024, true);
                        break;
                    case "System.Guid":
                        return new GuidAttribute(true);
                        break;
                    case "System.Boolean":
                        return new BooleanAttribute(true);
                        break;
                    default:
                        throw new InvalidOperationException("Data type not supported");
                }


            }
        }

        private bool IsPrimary(PropertyInfo pinfo)
        {
            PrimaryKeyAttribute pk = CommonHelper.GetAttribute(pinfo,
                typeof(PrimaryKeyAttribute)) as PrimaryKeyAttribute;
            if (pk == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

         */
    }

}
