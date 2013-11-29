using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;

namespace Reflow.Helper
{
 
    public abstract class ColumnTypeTranslatorBase  : IColumnTypeTranslator
	{
		#region ColumnDefinitionTranslator Members

		public string GetDataTypeExpression(ColumnDefinition dataType)
        {
            switch (dataType.DataType)
            {
                case DbType.AnsiString:
                    return this.GetAnsiStringExpression(dataType);
                    break;
                case DbType.AnsiStringFixedLength:
                    return this.GetAnsiStringFixedLengthExpression(dataType);
                    break;
                case DbType.Binary:
                    return this.GetBinaryExpression(dataType);
                    break;
                case DbType.Boolean:
                    return this.GetBooleanExpression(dataType);
                    break;
                case DbType.Byte:
                    return this.GetByteExpression(dataType);
                    break;
                case DbType.Currency:
                    return this.GetCurrencyExpression(dataType);
                    break;
                case DbType.DateTime:
                    return this.GetDateTimeExpression(dataType);
                    break;
                case DbType.Decimal:
                    return this.GetDecimalExpression(dataType);
                    break;
                case DbType.Double:
                    return this.GetDoubleExpression(dataType);
                    break;
                case DbType.Guid:
                    return this.GetGuidExpression(dataType);
                    break;
                case DbType.Int16:
                    return this.GetInt16Expression(dataType);
                    break;
                case DbType.Int32:
                    return this.GetInt32Expression(dataType);
                    break;
                case DbType.Int64:
                    return this.GetInt64Expression(dataType);
                    break;
                case DbType.Single:
                    return this.GetSingleExpression(dataType);
                    break;
                case DbType.String:
                    return this.GetStringExpression(dataType);
                    break;
                case DbType.StringFixedLength:
                    return this.GetStringFixedLengthExpression(dataType);
                    break;
                case DbType.UInt16:
                    return this.GetUInt16Expression(dataType);
                    break;
                case DbType.UInt32:
                    return this.GetUInt32Expression(dataType);
                    break;
                case DbType.UInt64:
                    return this.GetUInt64Expression(dataType);
                    break;
            }
            throw new NotSupportedException("Data type not supported");            
        }

        public virtual string NextStatementIdentifier 
        {
            get { return Environment.NewLine; } 
        }
        

        public virtual string GetGuidExpression(ColumnDefinition dataType)
        {
            return pGetStringExpression("UNIQUEIDENTIFIER", 0, dataType.IsNullable, null);
        }


        public virtual string GetAnsiStringExpression(ColumnDefinition dataType)
        {            
            if (dataType.Length>0)
            {
                return this.pGetStringExpression("VARCHAR", dataType.Length, dataType.IsNullable,
                    dataType.DefaultValue as string, true, dataType.IsIdentity, false,dataType.IsUnique, dataType.IsLong);
            }
            else
            {
                return this.pGetStringExpression ("TEXT",0,dataType.IsNullable, dataType.DefaultValue as string, true);
            }
        }

        public virtual string GetAnsiStringFixedLengthExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("CHAR", dataType.Length, dataType.IsNullable,
                dataType.DefaultValue as string, true, dataType.IsIdentity,dataType.IsPrimary);
        }

        public virtual string GetBinaryExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("BINARY", dataType.Length, dataType.IsNullable,
                Convert.ToString(dataType.DefaultValue));
        }

        public virtual string GetBooleanExpression(ColumnDefinition dataType)
        {
            if (dataType.DefaultValue == null)
            {
                return this.pGetStringExpression("BIT", 0, dataType.IsNullable,
                    null);
            }
            else
            {
                if ((bool)dataType.DefaultValue == true)
                {
                    return this.pGetStringExpression("BIT", 0, dataType.IsNullable,"1");
                }
                else
                {
                    return this.pGetStringExpression("BIT", 0, dataType.IsNullable,
                        "0");
                }
            }

        }

        public virtual string GetByteExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("IMAGE", 0, dataType.IsNullable,
                null);
        }

        public virtual string GetCurrencyExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("MONEY", 0, dataType.IsNullable,
                Convert.ToString(dataType.DefaultValue));
        }

        public virtual string GetDateTimeExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("DATETIME", 0, dataType.IsNullable,
                Convert.ToString(dataType.DefaultValue), true);
        }

        public virtual string GetDecimalExpression(ColumnDefinition dataType)
        {
            return pGetStringExpression("DECIMAL", dataType.Precision, dataType.Scale, dataType.IsNullable, Convert.ToString(dataType.DefaultValue));
        }

        public virtual string GetDoubleExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("REAL", 0, dataType.IsNullable, Convert.ToString(dataType.DefaultValue ), 
                false, dataType.IsIdentity, dataType.IsPrimary);
        }

        public virtual string GetInt16Expression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("SMALLINT", 0, dataType.IsNullable, Convert.ToString(dataType.DefaultValue ),
                false, dataType.IsIdentity, dataType.IsPrimary);
        }

        public virtual string GetInt32Expression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("INT", 0, dataType.IsNullable, Convert.ToString(dataType.DefaultValue) ,
                false, dataType.IsIdentity, dataType.IsPrimary);
        }

        public virtual string GetInt64Expression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("BIGINT", 0, dataType.IsNullable, Convert.ToString(dataType.DefaultValue) ,
                false, dataType.IsIdentity, dataType.IsPrimary);

        }

        public virtual string GetSingleExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("SINGLE", 0, dataType.IsNullable, Convert.ToString(dataType.DefaultValue), 
                false, dataType.IsIdentity, dataType.IsPrimary);
        }

        public virtual string GetStringExpression(ColumnDefinition dataType)
        {
            if (dataType.Length > 0)
            {
                return this.pGetStringExpression("NVARCHAR", dataType.Length, dataType.IsNullable,
                    dataType.DefaultValue as string, true, dataType.IsIdentity, dataType.IsPrimary);
            }
            else
            {
                return this.pGetStringExpression("NTEXT" ,0, dataType.IsNullable,
                    dataType.DefaultValue as string, true, dataType.IsIdentity, dataType.IsPrimary);
            }
        }

        public virtual string GetStringFixedLengthExpression(ColumnDefinition dataType)
        {
            return this.pGetStringExpression("NCHAR", dataType.Length, dataType.IsNullable,
                dataType.DefaultValue as string, true, dataType.IsIdentity, dataType.IsPrimary);
        }

        public virtual string GetUInt16Expression(ColumnDefinition dataType)
        {
            return this.GetInt16Expression(dataType);
        }

        public virtual string GetUInt32Expression(ColumnDefinition dataType)
        {
            return this.GetInt32Expression(dataType);
        }

        public virtual string GetUInt64Expression(ColumnDefinition dataType)
        {
            return this.GetInt64Expression(dataType);
        }

        public abstract char OpeningIdentifier
        {
            get ;
        }

        public abstract char ClosingIdentifier
        {
            get ;
        }

		public abstract char ParameterIdentifier
		{
			get ;
		}

		public abstract char ParameterPlaceholderIdentifier
		{
			get ;
		}


        #endregion

        private string pGetStringExpression(string typeName, int length, bool isNullable, string defaultValue)
        {
            return this.pGetStringExpression(typeName, length, isNullable, defaultValue, false, false, false);
        }

        private string pGetStringExpression(string typeName, int length, bool isNullable, bool isPrimary, string defaultValue)
        {
            return this.pGetStringExpression(typeName, length, isNullable, defaultValue, false, false, isPrimary);
        }


        private string pGetStringExpression(string typeName, int length, bool isNullable, string defaultValue, bool needsQuote)
        {
            return this.pGetStringExpression(typeName, length, isNullable, defaultValue, needsQuote, false, false);
        }

        private string pGetStringExpression(string typeName, int length, bool isNullable, string defaultValue, bool needsQuote, bool isIdentity, bool isPrimary)
        {
            return pGetStringExpression(typeName, length, isNullable, defaultValue, needsQuote, isIdentity, isPrimary, false, false);
        }

        public virtual string IdentityExpression
        {
            get { return "IDENTITY"; }
        }

        public virtual string GetDefaultExpression(string defaultValue, bool needsQuote)
        {
            string quoteString = string.Empty;
            if (!string.IsNullOrEmpty(defaultValue))
            {
                if (needsQuote)
                {
                    quoteString = "'";
                }
                string defaultExpression = string.Format("DEFAULT ({1}{0}{1})", defaultValue, quoteString);
                return defaultExpression;
            }
            else
            {
                return string.Empty;
            }
        }

        private string pGetStringExpression(string typeName, int length, bool isNullable, string defaultValue, bool needsQuote, bool isIdentity, bool isPrimary, bool unique, bool isLong )
        {
            string lengthExpression = string.Empty;
            string nullExpression = "NULL";
            string defaultExpression = string.Empty;
            string quoteString = string.Empty;
            string uniqueString = string.Empty;

            if (needsQuote)
            {
                quoteString = "'";
            }
            if (!isIdentity)
            {
                if (defaultValue != null)
                {
                    if (defaultValue.Length > 0)
                    {
                        defaultExpression = GetDefaultExpression(defaultValue, needsQuote);
                    }
                }
            }
            else
            {
                //if (!isPrimary)
                //{
                    defaultExpression = IdentityExpression;
                //}
                //else
                //{
                //    defaultExpression = string.Format( "PRIMARY KEY {0}", IdentityExpression);
                //}
            }
            if (isLong)
            {
                lengthExpression = "(MAX)";
            }
            else if (length > 0)
            {
                lengthExpression = string.Format("({0})", length);
            }
            if (!isNullable)
            {
                nullExpression = "NOT NULL";
            }
            if (unique)
            {
                uniqueString = "UNIQUE";
            }

            char openingIdentifier = this.OpeningIdentifier;
            char closingIdentifier = this.ClosingIdentifier;

            if (!SupportsIdentifierAroundTypename)
            {
                openingIdentifier =' ';
                closingIdentifier=' ';
            }

            return string.Format("{0}{1}{2} {3} {4} {5}{6}", openingIdentifier,
                typeName, closingIdentifier, lengthExpression, nullExpression, defaultExpression, uniqueString);
        }

        public virtual bool SupportsIdentifierAroundTypename { get { return true; } }

        private string pGetStringExpression(string typeName, int precison, int scale, bool isNullable, object defaultValue)
        {
            string nullExpression = "NULL";
            string defaultValueString = defaultValue.ToString();
            string defaultExpression = GetDefaultExpression(defaultValueString, false);
            //string defaultString = string.Empty;
            
            if (!isNullable)
            {
                nullExpression = "NOT NULL";
            }

            char openingIdentifier = this.OpeningIdentifier;
            char closingIdentifier = this.ClosingIdentifier;

            if (!SupportsIdentifierAroundTypename)
            {
                openingIdentifier = ' ';
                closingIdentifier = ' ';
            }

            return string.Format("{0}{1}{2} ({3},{4}) {5} {6}", openingIdentifier,
                typeName, closingIdentifier, precison, scale, nullExpression, defaultExpression);
        }

        public DbType GetDbTypeFromSystemTypeString(string systemType)
        {
            switch (systemType)
            {
                case "System.String":
                    return DbType.AnsiString;
                case "System.Char":
                    return DbType.AnsiStringFixedLength;
                case "System.Int32":
                    return DbType.Int32;
                case "System.Int64":
                    return DbType.Int64;
                case "System.Decimal":
                    return DbType.Decimal;
                case "System.Double":
                    return DbType.Double;
                case "System.DateTime":
                    return DbType.DateTime;
                case "System.int16":
                    return DbType.Int16;
                case "System.Byte[]":
                    return DbType.Binary;
                case "System.Guid":
                    return DbType.Guid;
                case "System.Boolean":
                    return DbType.Boolean;
            }
            return DbType.AnsiString;
        }
    }
}
