<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/2
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
选择入库仓库
<select id="ruku_warehouses"></select>
<a id="rukuOk" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">确认入库</a>
<script>
    $(function () {
        $.get("queryAllWarehouse.action",function (data) {
            $("#ruku_warehouses").html("");
            var op = "";
            $(data).each(function (i) {
                op += "<option value='"+data[i].warehouseId+"'>"+data[i].warehouseName+"</option>"
            })
            $("#ruku_warehouses").append(op);
        },"json")

        //确认入库
        $("#rukuOk").click(function () {
            var purchasenoteSelRow = $("#purchasenote_table").datagrid("getSelected");
            var purchaseorderId = purchasenoteSelRow.purchaseorder.purchaseorderId;//得到采购订单id
            $.post("rukuOk.action", {"purchaseorderId": purchaseorderId,"warehouseId":$("#ruku_warehouses").val()}, function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#ruku_warehouseId").window("close");
                $("#purchasenote_table").datagrid("load");
            }, "text")
        })
    })
</script>
</body>
</html>
