<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/24
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>仓库管理</title>
</head>
<body>
<div id="warehouse_seldiv">
    <a id="warehouse_upt" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
</div>
<!-- 修改窗口-->
<div id="warehouse_uptWin" class="easyui-window" title="修改仓库信息"
     style="width:600px;height:200px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'dataIndex.action?url=uptWarehouseWin',closed:true">
</div>
<table id="warehouse_table"></table>
<script>
    $(function () {
        var param = {
            url: 'queryLikeWarehouse.action',
            columns: [[
                {field: 'ck', title: '复选框', checkbox: true, width: 100},
                {field: 'warehouseId', title: 'id', hidden: true},
                {field: 'warehouseName', title: '仓库名', width: 60, align: 'center'},
                {field: 'warehouseMaxinventory', title: '最大容量', width: 60, align: 'center'},
                {
                    field: 'nowInventory', title: '已用容量', align: 'center', formatter: function (value, row) {
                        var v = "";
                        $.ajax({
                            url: "queryWarehouseCountByWid.action",
                            data: {"wid": row.warehouseId}, type: "get", async: false, success: function (result) {
                                v = result;
                            },
                        });
                        return v;
                    }
                },
            ]],
            fitColumns: true,
            toolbar: '#',
            striped: true,   //是否显示斑马线效果
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15, 20, 30]   //初始化页面大小选择列表。
        };
        $("#warehouse_table").datagrid(param);


        //修改按钮  绑定事件  打开添加窗口
        $("#warehouse_upt").click(function () {
            //获取所有选中行的记录数组
            var selrows = $("#warehouse_table").datagrid("getSelected");
            if (selrows == null) {
                $.messager.alert('消息', '请选择一条记录！', 'info');
                return;
            }
            $("#warehouse_uptWin").window("open");
            $('#warehouse_uptWin').window('refresh');
        });
    });

    function queryWarehouseCountByWid(wid) {
        var v = "";
        $.get("queryWarehouseCountByWid.action", {"wid": wid}, function (data) {
            v = data;
        }, "text");
        return v;
    }
</script>
</body>
</html>
