<%@ page import="com.zilong.vo.systemVo.Users" %>
<%@ page import="com.zilong.vo.systemVo.Role" %><%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/4
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>销售订单</title>
</head>
<body>
<table id="salesorder_table" style="width: 100%;"></table>
<%--右键菜单--%>
<div id="salesorder_menu" class="easyui-menu" style="width: 80px; display: none;">
    <div class="salesorder_lookCommodity" data-options="iconCls:'icon-edit'">查看采购订单详情</div>
    <%
        Users users = (Users) session.getAttribute("user");
        for (Role o : users.getRoles()) {
            if (o.getRoleId() == 1 || o.getRoleId() == 2) {
    %>
    <div data-options="iconCls:'icon-ok'" id="salesOk">同意</div>
    <div data-options="iconCls:'icon-no'" id="salesNo">拒绝</div>
    <%
            }
        }
    %>
</div>
<div id="salesorder_menuOk" class="easyui-menu" style="width: 80px; display: none;">
    <div class="purchaseorder_lookCommodity" data-options="iconCls:'icon-edit'">查看采购订单详情</div>
</div>
<%--查看采购详情jsp--%>
<div id="salesorderDetails_Win" class="easyui-window" title="查看采购订单的商品"
     style="width:800px;height:550px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'salesJsp.action?url=salesorderDetailsWin',closed:true">
</div>
<script>
    $(function () {
        var param = {
            url: 'querySalesorder.action',
            columns: [[
                {field: 'salesorderId', title: '销售订单id', hidden: true},
                {field: 'salesorderNumber', title: '销售订单编号', width: 60, align: 'center'},
                {field: 'salesorderCreatedate', title: '生成日期', width: 60, align: 'center'},
                {field: 'salesorderValiddate', title: '有效日期', width: 60, align: 'center'},
                {field: 'salesorderTotalprice', title: '订单总价', width: 60, align: 'center'},
                {
                    field: 'connection', title: '客户名', width: 60, align: 'center', formatter: function (value) {
                        return value.connectionName;
                    }
                },
                {field: 'salesorderState', title: '订单状态', width: 60, align: 'center'},
                {
                    field: 'salesorderText', title: '备注', width: 60, align: 'center', formatter: function (value) {
                        return "<textarea disabled=disabled style='width:170px;height:50px;resize:none;'>"
                            + value + "</textarea>";
                    }
                },
            ]],
            fitColumns: true,
            toolbar: '#',
            striped: true,   //是否显示斑马线效果
            checkOnSelect: false, //点ck才能选中
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15],   //初始化页面大小选择列表。
            onRowContextMenu: function (e, rowIndex, rowData) {
                e.preventDefault(); //阻止浏览器捕获右键事件
                $(this).datagrid("clearSelections"); //取消所有选中项
                $(this).datagrid("selectRow", rowIndex); //根据索引选中该行
                if (rowData.salesorderState == "待审核") {
                    $('#salesorder_menu').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                } else {
                    $('#salesorder_menuOk').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                }
                e.preventDefault();  //阻止浏览器自带的右键菜单弹出
            }
        }
        //设置表格属性
        $("#salesorder_table").datagrid(param);

        $(".salesorder_lookCommodity").click(function () {
            $("#salesorderDetails_Win").window("open");
            $("#salesorderDetails_Win").window("refresh");
        })

        //同意销售订单 生成销售单
        $("#salesOk").click(function () {
            var row = $("#salesorder_table").datagrid("getSelected");
            $.post("addSalesticket.action", {"salesorderId": row.salesorderId}, function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#salesorder_table").datagrid("load");
            }, "text")
        })

        // 拒绝销售订单
        $("#salesNo").click(function () {
            var row = $("#salesorder_table").datagrid("getSelected");
            $.post("noSalesticket.action", {"salesorderId": row.salesorderId}, function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#salesorder_table").datagrid("load");
            }, "text")
        })
    })
</script>
</body>
</html>
