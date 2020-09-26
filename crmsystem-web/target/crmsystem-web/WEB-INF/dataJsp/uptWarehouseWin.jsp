<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/25
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="uptWarehouseWin_form">
    <table id="uptWarehouseWin_table" style="width: 100%;" class="table_form">
        <tr style="display: none">
            <td><label>仓库id:</label></td>
            <td><input id="uptWarehouseWin_supplierId" name="warehouseId"
                       class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>仓库名称:</label></td>
            <td><input id="uptWarehouseWin_warehouseName" name="warehouseName" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>最大容量:</label></td>
            <td><input id="uptWarehouseWin_warehouseMaxinventory" name="warehouseMaxinventory"
                       class="easyui-numberspinner"
                       required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="uptWarehouseWin_uptOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
        </tr>
    </table>
</form>
<script>
    var warehouseSelRows = $("#warehouse_table").datagrid("getSelected");
    $("#uptWarehouseWin_supplierId").val(warehouseSelRows.warehouseId);
    $("#uptWarehouseWin_warehouseName").val(warehouseSelRows.warehouseName);
    $("#uptWarehouseWin_warehouseMaxinventory").val(warehouseSelRows.warehouseMaxinventory);

    $("#uptWarehouseWin_uptOk").click(function () {
        $('#uptWarehouseWin_form').form('submit', {
            url: "uptWarehouse.action",
            onSubmit: function () {
                if (isNull($("#uptWarehouseWin_warehouseName").val()) || isNull($("#uptWarehouseWin_warehouseMaxinventory").val())) {
                    $.messager.alert('消息', '输入有误不能为空');
                    return false;
                }
                var v = "";
                $.ajax({
                    url: "queryWarehouseCountByWid.action",
                    data: {"wid": warehouseSelRows.warehouseId}, type: "get", async: false, success: function (result) {
                        v = result;
                    },
                });
                if($("#uptWarehouseWin_warehouseMaxinventory").val() < v){
                    $.messager.alert('消息', '最大容量不能小于已用容量');
                    return false;
                }
            },
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#warehouse_uptWin").window("close");
                $("#warehouse_table").datagrid('load');
            }
        });
    })
</script>
</body>
</html>
