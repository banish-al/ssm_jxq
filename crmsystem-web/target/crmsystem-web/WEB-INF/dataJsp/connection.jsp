<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/4
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户管理</title>
</head>
<body>
<div id="connection_div">
    姓名：<input type="text" id="connection_selName">
    电话：<input type="text" id="connection_selPhone">
    <a id="connection_selBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <a id="connection_addBut" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
    <a id="connection_uptBut" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
    <a id="connection_delBut" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
</div>
<table id="connection_table"></table>
<!-- 添加窗口-->
<div id="connection_addWin" class="easyui-window" title="添加用户"
     style="width:600px;height:350px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'dataIndex.action?url=addConnectionWin',closed:true">
</div>
<!-- 编辑窗口-->
<div id="connection_uptWin" class="easyui-window" title="添加用户"
     style="width:600px;height:350px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'dataIndex.action?url=uptConnectionWin',closed:true">
</div>
<script>
    $(function () {
        var param = {
            url: 'queryConnection.action',
            columns: [[
                {field: 'connectionId', title: '客户id', hidden: true},
                {field: 'connectionName', title: '姓名', width: 40, align: 'center'},
                {field: 'connectionPhone', title: '电话', width: 60, align: 'center'},
                {field: 'connectionAddr', title: '地址', width: 40, align: 'center'},
                {field: 'connectionCount', title: '交易次数', width: 60, align: 'center'},
            ]],
            fitColumns: true,
            toolbar: '#connection_div',
            striped: true,   //是否显示斑马线效果
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15, 20, 30]   //初始化页面大小选择列表。
        };
        $("#connection_table").datagrid(param);

        $("#connection_selBut").click(function () {
            $("#connection_table").datagrid('load', {
                connectionName: $("#connection_selName").val(),
                connectionPhone: $("#connection_selPhone").val()
            });
        })

        //添加
        $("#connection_addBut").click(function () {
            $("#connection_addWin").window("open");
            $('#connection_addWin').window('refresh');
        })

        //修改
        $("#connection_uptBut").click(function () {
            //获取所有选中行的记录数组
            var selrows = $("#connection_table").datagrid("getSelected");
            if (selrows == null) {
                $.messager.alert('消息', '请选择一条记录！', 'info');
                return;
            }
            $("#connection_uptWin").window("open");
            $('#connection_uptWin').window('refresh');
        })

        //删除
        $("#connection_delBut").click(function () {
            var selrows = $("#connection_table").datagrid("getSelected");
            if (selrows == null) {
                $.messager.alert('消息', '请选择一条记录！', 'info');
                return;
            }
            $.post("delConnectionByCid.action", {"cid": selrows.connectionId}, function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#connection_table").datagrid('load', {
                    connectionName: $("#connection_selName").val(),
                    connectionPhone: $("#connection_selPhone").val()
                });
            }, "text")
        })
    })
</script>
</body>
</html>
