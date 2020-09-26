<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/4
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table id="mySalesCommodity_table" style="width: 100%;"></table>
<div id="mySalesCommodityOrderDetails" class="easyui-menu" style="width: 80px; display: none;">
    <div id="mySalesCommodityOrderDetails_look" data-options="iconCls:'icon-edit'">查看采购订单详情</div>
</div>
<%--查看采购详情jsp--%>
<div id="mySalesCommodityOrderDetails_Win" class="easyui-window" title="查看采购订单的商品"
     style="width:800px;height:550px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'mysalesJsp.action?url=mySalesorderDetailsWin',closed:true">
</div>
<script>
    $(function () {
        var param = {
            url: 'querySalesorderByConnectionId.action',
            columns: [[
                {field: 'salesorderId', title: '销售订单id', hidden: true},
                {field: 'salesorderNumber', title: '销售订单编号', width: 60, align: 'center'},
                {field: 'salesorderCreatedate', title: '生成日期', width: 60, align: 'center'},
                {field: 'salesorderValiddate', title: '有效日期', width: 60, align: 'center'},
                {field: 'salesorderTotalprice', title: '订单总价', width: 60, align: 'center'},
                {
                    field: 'shenheren', title: '审核人', width: 60, align: 'center', formatter: function (value,row) {
                        if(row.salesorderState == "待审核"){
                            return "暂无审核";
                        }
                        if(row.salesorderState == "已同意"){
                            var v = "";
                            $.ajax({
                                url: "queryUserName.action",
                                data: {"sid": row.salesorderId},
                                type: "get",
                                async: false,
                                success: function (result) {
                                    v =  result ;
                                },
                            });
                            return v;
                        }
                        if(row.salesorderState == "已拒绝"){
                            return '无效订单'
                        }
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
                $('#mySalesCommodityOrderDetails').menu('show', {
                    left: e.pageX,//在鼠标点击处显示菜单
                    top: e.pageY
                });

                e.preventDefault();  //阻止浏览器自带的右键菜单弹出
            }
        }
        //设置表格属性
        $("#mySalesCommodity_table").datagrid(param);

        // 查看采购订单详情
        $("#mySalesCommodityOrderDetails_look").click(function () {
            $("#mySalesCommodityOrderDetails_Win").window("open");
            $("#mySalesCommodityOrderDetails_Win").window("refresh");
        })
    })
</script>
</body>
</html>
