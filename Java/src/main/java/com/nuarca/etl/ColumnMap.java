package com.nuarca.etl;

import com.nuarca.etl.transformation.Expression;

public class ColumnMap {

    
    private String _SourceColumn;
    public String getSourceColumn() {
        return _SourceColumn;
    }

    public void setSourceColumn(String value) {
        _SourceColumn = value;
    }

    private String _Destination;
    public String getDestination() {
        return _Destination;
    }

    public void setDestination(String value) {
        _Destination = value;
    }

    private Expression _TransformExpression;
    public Expression getTransformExpression() {
        return _TransformExpression;
    }

    public void setTransformExpression(Expression value) {
        _TransformExpression = value;
    }
}
