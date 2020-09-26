<%@ page import="com.zilong.vo.systemVo.Users" %>
<%@ page import="com.zilong.vo.systemVo.Role" %><%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/28
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="purchaseorder_div">
    <a id="purchaseorder_caigouBut" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">去采购</a>
</div>
<table id="purchaseorder_table"></table>
<!-- 供货商商品 -->
<div id="purchaseorder_caigouWin" class="easyui-window" title="采购商品"
     style="width:800px;height:650px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'purchaseIndex.action?url=purchaseorderCaigouWin',closed:true">
</div>
<%--右键菜单--%>
<div id="purchaseorder_menu" class="easyui-menu" style="width: 80px; display: none;">
    <div class="purchaseorder_lookCommodity" data-options="iconCls:'icon-edit'">查看采购订单详情</div>
    <%
        Users users = (Users) session.getAttribute("user");
        for (Role o : users.getRoles()) {
            if (o.getRoleId() == 1 || o.getRoleId() == 2) {
    %>
    <div data-options="iconCls:'icon-ok'" id="orderOk">同意</div>
    <div data-options="iconCls:'icon-no'" id="orderNo">拒绝</div>
    <%
            }
        }
    %>
</div>
<div id="purchaseorder_menuOk" class="easyui-menu" style="width: 80px; display: none;">
    <div class="purchaseorder_lookCommodity" data-options="iconCls:'icon-edit'">查看采购订单详情</div>
</div>
<%--查看采购详情jsp--%>
<div id="PurchaseorderDetails_Win" class="easyui-window" title="查看采购订单的商品"
     style="width:800px;height:550px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'purchaseIndex.action?url=purchaseorderDetails',closed:true">
</div>
<script>
    $(function () {
        $("#purchaseorder_caigouBut").click(function () {
            $("#purchaseorder_caigouWin").window("open");
            $("#purchaseorder_caigouWin").window("refresh");
        })

        //同意采购订单
        $("#orderOk").click(function () {
            var selrows = $("#purchaseorder_table").datagrid("getSelected");
            $.post("purchaseorderOk.action",{"purchaseorferId":selrows.purchaseorderId},function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#purchaseorder_table").datagrid("load");
            },"text")
        })

        //拒绝采购
        $("#orderNo").click(function () {
            var selrows = $("#purchaseorder_table").datagrid("getSelected");
            $.post("purchaseorderNo.action",{"purchaseorferId":selrows.purchaseorderId},function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#purchaseorder_table").datagrid("load");
            },"text")
        })
        var param = {
            url: 'queryPurchaseorder.action',
            columns: [[
                {field: 'purchaseorderId', title: '订单id', hidden: true},
                {field: 'purchaseorderNumber', title: '订单编号', width: 50, align: 'center'},
                {
                    field: 'users', title: '采购申请人', width: 50, align: 'center', formatter: function (value) {
                        return value.userName;
                    }
                },
                {field: 'purchaseorderTotalprice', title: '总价格', width: 50, align: 'center'},
                {field: 'purchaseorderCreatedate', title: '生成日期', width: 50, align: 'center'},
                {field: 'purchaseorderValiddate', title: '有效日期', width: 50, align: 'center'},
                {field: 'purchaseorderState', title: '订单状态', width: 50, align: 'center'},
                {
                    field: 'purchaseorderText', title: '备注', width: 60, align: 'center', formatter: function (value) {
                        return "<textarea disabled=disabled style='width:170px;height:50px;resize:none;'>"
                            + value + "</textarea>";
                    }
                },
            ]],
            fitColumns: true,
            toolbar: '#purchaseorder_div',
            striped: true,   //是否显示斑马线效果
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15, 20, 30],   //初始化页面大小选择列表。
            onRowContextMenu: function (e, rowIndex, rowData) {
                e.preventDefault(); //阻止浏览器捕获右键事件
                $(this).datagrid("clearSelections"); //取消所有选中项
                $(this).datagrid("selectRow", rowIndex); //根据索引选中该行
                if(rowData.purchaseorderState == "待审核"){
                    $('#purchaseorder_menu').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                }else {
                    $('#purchaseorder_menuOk').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                }
                e.preventDefault();  //阻止浏览器自带的右键菜单弹出
            }
        };
        $("#purchaseorder_table").datagrid(param);

        // 查看采购订单详情
        $(".purchaseorder_lookCommodity").click(function () {
            $("#PurchaseorderDetails_Win").window("open");
            $("#PurchaseorderDetails_Win").window("refresh");
        })
    })
</script>
</body>
</html>
