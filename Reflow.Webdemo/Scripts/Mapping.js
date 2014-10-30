$(function () {

    var selectString = "<option value='_unmapped_' selected>(Unmapped)</option>";
    $.each(importInfo.SourceColumns, function (index, colName) {
        selectString = selectString +
            "<option value='" + colName + "'>" + colName + "</option>";
    });
    selectString = selectString + "<option value='$'>{Custom Expression}</option></select>";
    tbl = $("#tblMap")
    $.each(importInfo.DestinationColumns, function (index, destCol) {
        $("<tr><td>" + destCol.ColumnName + "</td>" +
            "<td><select id='src"+ destCol.ColumnName + "'>" + selectString + "</td>" +
            "<td><input type='text' id='txtEx" + destCol.ColumnName +  "' style='display:none;width:500px;'/></td>" +
            "</tr>").appendTo(tbl);
        $("#src" + destCol.ColumnName).change(function () {
            if($("#src" + destCol.ColumnName).val() == "$")
            {
                $("#txtEx" + destCol.ColumnName).show();
            }
            else
            {
                $("#txtEx" + destCol.ColumnName).hide();
            }
        });
    });

    $("#btnProcess").click(function () {

        dic = {};

        $.each(importInfo.SourceColumns, function (index, colname) {

        });
        var errorMessage = null;

        maps = [];
        
        $.each(importInfo.DestinationColumns, function (index, destCol) {
            var map = { FieldName: destCol.ColumnName, TargetExpression:null };
            selectValue = $("#src" + destCol.ColumnName).val();

            if (selectValue == "$") {
                map.TargetExpression = $("#txtEx" + destCol.ColumnName).val();
                maps.push(map);
            }
            else {
                if (selectValue == "_unmapped_") {
                    errorMessage = "Please map all fields."
                    return;
                } else {
                    map.TargetExpression = selectValue;
                    maps.push(map);
                }
            }

        });

        if ( errorMessage!=null){
            alert(errorMessage);
        }
        else{
            importInfo.Maps = maps;
            $("#hidImport").val(JSON.stringify(importInfo));
            console.log(importInfo);
            $("#formImport")[0].submit();
        }
    });

    $("#btnAutomap").click(function () {

        dic = {};
        $.each(importInfo.SourceColumns, function (index, colName) {
            dic[colName] = 1;
        });

        $.each(importInfo.DestinationColumns, function (index, destCol) {
            selectValue = $("#src" + destCol.ColumnName).val();
            if (selectValue == "_unmapped_") {
                if (dic[destCol.ColumnName]==1) {
                    $("#src" + destCol.ColumnName).val(destCol.ColumnName);
                }
            }
        });
    });

});