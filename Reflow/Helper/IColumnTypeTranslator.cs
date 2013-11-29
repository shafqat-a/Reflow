using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;

namespace Reflow.Helper
{
    public interface IColumnTypeTranslator
    {
        DbType GetDbTypeFromSystemTypeString(string systemType);
        
        string GetDataTypeExpression(ColumnDefinition dataType);

        string GetAnsiStringExpression(ColumnDefinition dataType);
        string GetAnsiStringFixedLengthExpression(ColumnDefinition dataType);
        string GetBinaryExpression(ColumnDefinition dataType);
        string GetBooleanExpression(ColumnDefinition dataType);
        string GetByteExpression(ColumnDefinition dataType);
        string GetCurrencyExpression(ColumnDefinition dataType);
        string GetDateTimeExpression(ColumnDefinition dataType);
        string GetDecimalExpression(ColumnDefinition dataType);
        string GetDoubleExpression(ColumnDefinition dataType);
        string GetInt16Expression(ColumnDefinition dataType);
        string GetInt32Expression(ColumnDefinition dataType);
        string GetInt64Expression(ColumnDefinition dataType);
        string GetSingleExpression(ColumnDefinition dataType);
        string GetStringExpression(ColumnDefinition dataType);
        string GetStringFixedLengthExpression(ColumnDefinition dataType);
        string GetUInt16Expression(ColumnDefinition dataType);
        string GetUInt32Expression(ColumnDefinition dataType);
        string GetUInt64Expression(ColumnDefinition dataType);
        string GetGuidExpression(ColumnDefinition dataType);

        char OpeningIdentifier { get; }
        char ClosingIdentifier { get; }
        char ParameterIdentifier { get; }
        char ParameterPlaceholderIdentifier { get; }

        string NextStatementIdentifier { get; }
        string IdentityExpression { get; }
        bool SupportsIdentifierAroundTypename { get; }
        string GetDefaultExpression(string defaultValue, bool needsQuote);

    }
}
