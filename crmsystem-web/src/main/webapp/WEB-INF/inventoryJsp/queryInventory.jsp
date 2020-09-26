<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/26
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="queryInventory_slediv">
    商品名：<input type="text" id="queryInventory_selCommodityName">
    <select id="queryInventory_warehouseName">
    </select>
    <a id="queryInventory_selBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <a id="queryInventory_turnBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">转库</a><br>
</div>
<script>
    $.get("queryAllWarehouse.action",function (data) {
        var op = "";
        $(data).each(function (i) {
            op += "<option value='"+data[i].warehouseId+"'>"+data[i].warehouseName+"</option>"
        })
        $("#queryInventory_warehouseName").append(op);
    },"json")
</script>
<table id="queryInventory_table"></table>
<!-- 转库窗口-->
<div id="queryInventory_turnDiv" class="easyui-window" title="转库商品"
     style="width:740px;height:460px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'inventoryIndex.action?url=turnInventory',closed:true">
</div>
<script>
    $(function () {
        var param = {
            url: 'queryInventoryByCommodity.action',
            columns: [[
                {field: 'commodityId', title: '商品id', hidden: true},
                {field: 'commodityName', title: '商品名', width: 40, align: 'center'},
                {field: 'commodityCoding', title: '商品编号', width: 60, align: 'center'},
                {
                    field: 'commodityImage', title: '商品图片', width: 40, align: 'center', formatter: function (value) {
                        return "<img src='" + value + "' >";
                    }
                },
                {
                    field: 'commoditys',
                    title: '商品数量',
                    width: 30,
                    align: 'center',
                    formatter: function (value, row) {
                        var v = 0;
                        var wid = $("#queryInventory_warehouseName").val();
                        $.ajax({
                            url: "queryCommodityCountByOneCid.action",
                            data: {"cid": row.commodityId,"wid":wid}, type: "get", async: false, success: function (result) {
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
            //singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15, 20, 30]   //初始化页面大小选择列表。
        };
        $("#queryInventory_table").datagrid(param);
        // 点击查询
        $("#queryInventory_selBut").click(function () {
            $("#queryInventory_table").datagrid('load', {
                wid: $("#queryInventory_warehouseName").val(),
                commodityName: $("#queryInventory_selCommodityName").val(),
            });
        })
        // 改变select查询
        $("#queryInventory_warehouseName").change(function () {
            $("#queryInventory_table").datagrid('load', {
                wid: $("#queryInventory_warehouseName").val(),
                commodityName: $("#queryInventory_selCommodityName").val(),
            });
        })

        $("#queryInventory_turnBut").click(function () {
            var selrows = $("#queryInventory_table").datagrid("getSelections");
            if (selrows.length == 0) {
                $.messager.alert('消息', '请至少选择一条商品！', 'info');
                return;
            }
            $("#queryInventory_turnDiv").window("open");
            $('#queryInventory_turnDiv').window('refresh');
        })
    });

</script>
</body>
</html>
