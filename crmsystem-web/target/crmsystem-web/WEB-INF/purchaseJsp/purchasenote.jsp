<%@ page import="com.zilong.vo.systemVo.Users" %>
<%@ page import="com.zilong.vo.systemVo.Role" %><%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/1
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>采购单管理</title>
</head>
<body>
<table id="purchasenote_table"></table>
<%--右键菜单--%>
<div id="purchasenote_menu" class="easyui-menu" style="width: 80px; display: none;">
    <div class="purchasenote_lookCommodity" data-options="iconCls:'icon-edit'">查看采购单详情</div>
    <%
        Users users = (Users) session.getAttribute("user");
        for (Role o : users.getRoles()) {
            if (o.getRoleId() == 1 || o.getRoleId() == 2) {
    %>
    <div data-options="iconCls:'icon-ok'" id="ruku">入库</div>
    <%
            }
        }
    %>
</div>
<div id="purchasenote_menuOk" class="easyui-menu" style="width: 80px; display: none;">
    <div class="purchasenote_lookCommodity" data-options="iconCls:'icon-edit'">查看采购单详情</div>
</div>
<%--查看采购单详情jsp--%>
<div id="purchasenoteDetails_Win" class="easyui-window" title="查看采购订单的商品"
     style="width:800px;height:550px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'purchaseIndex.action?url=purchasenoteDetails',closed:true">
</div>
<%--选择入库仓库--%>
<div id="ruku_warehouseId" class="easyui-window" title="入库仓库"
     style="width:300px;height:150px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,closed:true,href:'purchaseIndex.action?url=ruku'">
</div>
<script>
    $(function () {
        var param = {
            url: 'queryPurchasenote.action',
            columns: [[
                {field: 'purchasenoteId', title: '采购单id', hidden: true},
                {
                    field: 'purchaseorder', title: '采购单编号', width: 50, align: 'center', formatter: function (value) {
                        return value.purchaseorderNumber;
                    }
                },
                {
                    field: 'users', title: '审批人', width: 50, align: 'center', formatter: function (value) {
                        return value.userName;
                    }
                },
                {field: 'purchasenoteDate', title: '审批时间', width: 50, align: 'center'},
                {field: 'purchasenoteState', title: '采购状态', width: 50, align: 'center'},
                {
                    field: 'purchasenoteText', title: '备注', width: 50, align: 'center', formatter: function (value) {
                        return "<textarea disabled=disabled style='width:170px;height:50px;resize:none;'>"
                            + value + "</textarea>";
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
            pageList: [5, 10, 15, 20, 30],   //初始化页面大小选择列表。
            onRowContextMenu: function (e, rowIndex, rowData) {
                e.preventDefault(); //阻止浏览器捕获右键事件
                $(this).datagrid("clearSelections"); //取消所有选中项
                $(this).datagrid("selectRow", rowIndex); //根据索引选中该行
                if (rowData.purchasenoteState == "采购中") {
                    $('#purchasenote_menu').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                } else {
                    $('#purchasenote_menuOk').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                }
                e.preventDefault();  //阻止浏览器自带的右键菜单弹出
            }
        };
        $("#purchasenote_table").datagrid(param);

        // 查看采购详情
        $(".purchasenote_lookCommodity").click(function () {
            $("#purchasenoteDetails_Win").window("open");
            $("#purchasenoteDetails_Win").window("refresh");
        })

        //入库
        $("#ruku").click(function () {
            $("#ruku_warehouseId").window("open");
            $("#ruku_warehouseId").window("refresh");
        })
    })
</script>
</body>
</html>
