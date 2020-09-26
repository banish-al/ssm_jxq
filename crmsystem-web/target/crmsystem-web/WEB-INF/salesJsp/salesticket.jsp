<%@ page import="com.zilong.vo.systemVo.Users" %>
<%@ page import="com.zilong.vo.systemVo.Role" %><%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/5
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>销售单</title>
</head>
<body>
<table id="salesticket_table" style="width: 100%;"></table>
<div id="chuku_div" class="easyui-menu" style="width: 80px; display: none;">
    <div class="salesticket_lookCommodity" data-options="iconCls:'icon-edit'">查询销售详情</div>
    <%
        Users users = (Users) session.getAttribute("user");
        for (Role o : users.getRoles()) {
            if (o.getRoleId() == 1 || o.getRoleId() == 2) {
    %>
    <div id="chuku_but" data-options="iconCls:'icon-edit'">出库</div>
    <%
            }
        }
    %>
</div>
<div id="chuku_div2" class="easyui-menu" style="width: 80px; display: none;">
    <div class="salesticket_lookCommodity" data-options="iconCls:'icon-edit'">查看销售详情</div>
</div>
<%--查看销售单详情jsp--%>
<div id="salesticketDetails_Win" class="easyui-window" title="查看销售的商品"
     style="width:800px;height:550px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'salesJsp.action?url=salesticketDetails',closed:true">
</div>
<script>
    $(function () {
        var param = {
            url: 'querySalesticket.action',
            columns: [[
                {field: 'salesticketId', title: '销售单id', hidden: true},
                {
                    field: 'salesorder', title: '销售单编号', width: 60, align: 'center', formatter: function (value) {
                        return value.salesorderNumber;
                    }
                },
                {
                    field: 'users', title: '销售人员', width: 60, align: 'center', formatter: function (value) {
                        return value.userName;
                    }
                },
                {field: 'salesticketDate', title: '销售时间', width: 60, align: 'center'},
                {field: 'salesticketState', title: '处理结果', width: 60, align: 'center'},
                {field: 'salesticketText', title: '备注', width: 60, align: 'center'},
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
                if(rowData.salesticketState == "揽货中"){
                    $('#chuku_div').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                }else {
                    $('#chuku_div2').menu('show', {
                        left: e.pageX,//在鼠标点击处显示菜单
                        top: e.pageY
                    });
                }
                e.preventDefault();  //阻止浏览器自带的右键菜单弹出
            }
        }
        //设置表格属性
        $("#salesticket_table").datagrid(param);

        // 查看销售详情
        $(".salesticket_lookCommodity").click(function () {
            $("#salesticketDetails_Win").window("open");
            $("#salesticketDetails_Win").window("refresh");
        })

        //出库
        $("#chuku_but").click(function () {
            var salesticketRow = $("#salesticket_table").datagrid("getSelected");
            var salesorderId = salesticketRow.salesorder.salesorderId;
            $.post("chuku.action",{"salesorderId":salesorderId},function (data) {
                if(data == "库存不足"){
                    $.messager.alert('消息', '某商品库存不足，请去采购商品');
                    return;
                }
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#salesticket_table").datagrid("load");
            },"text")
        })
    })
</script>
</body>
</html>
