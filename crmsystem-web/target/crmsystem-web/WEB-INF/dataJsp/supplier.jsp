<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/24
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>供货商管理</title>
</head>
<body>
<div id="supplier_selDiv">
    名称：<input type="text" id="supplier_supplierName">
    地址：<input type="text" id="supplier_supplierAddr">
    邮箱：<input type="text" id="supplier_supplierMailbox">
    <a id="supplier_selBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br>
    <a id="supplier_addBut" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
    <a id="supplier_uptBut" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
</div>
<!-- 添加窗口-->
<div id="supplier_addWin" class="easyui-window" title="添加供货商"
     style="width:600px;height:350px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'dataIndex.action?url=addSupplierWin',closed:true">
</div>
<!-- 修改窗口-->
<div id="supplier_uptWin" class="easyui-window" title="修改供货商"
     style="width:600px;height:400px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'dataIndex.action?url=uptSupplierWin',closed:true">
</div>
<table id="supplier_table"></table>
<script>
    var param = {
        url: 'queryAllSupplier.action',
        columns: [[
            {field: 'supplierId', title: 'id', hidden: true},
            {field: 'supplierName', title: '名称', width: 60, align: 'center'},
            {field: 'supplierAddr', title: '地址', width: 60, align: 'center'},
            {field: 'supplierMailbox', title: '邮箱', width: 60, align: 'center'}
        ]],
        fitColumns: true,
        toolbar: '#supplier_selDiv',
        striped: true,   //是否显示斑马线效果
        pagination: true,  //则在DataGrid控件底部显示分页工具栏。
        rownumbers: true,   //如果为true，则显示一个行号列。
        singleSelect: true,  //如果为true，则只允许选择一行。
        pageSize: 5,   //在设置分页属性的时候初始化页面大小
        pageList: [5, 10, 15, 20, 30]   //初始化页面大小选择列表。
    };
    $("#supplier_table").datagrid(param);

    //刷新表格
    $("#supplier_selBut").click(function () {
        $("#supplier_table").datagrid('load', {
            supplierName: $("#supplier_supplierName").val(),
            supplierAddr: $("#supplier_supplierAddr").val(),
            supplierMailbox: $("#supplier_supplierMailbox").val()
        });
    });

    $("#supplier_addBut").click(function () {
        $("#supplier_addWin").window("open");
    })

    $("#supplier_uptBut").click(function () {
        //获取所有选中行的记录数组
        var selrows = $("#supplier_table").datagrid("getSelected");
        if (selrows == null) {
            $.messager.alert('消息', '请选择一条记录！', 'info');
            return;
        }
        $("#supplier_uptWin").window("open");
        $('#supplier_uptWin').window('refresh');
    })
</script>
</body>
</html>
