<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/4
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table id="salesorderDetails_table"></table>
<script>
    $(function () {
        var row = $("#salesorder_table").datagrid("getSelected");
        var id = row.salesorderId;
        var purchaseorderDetails_param = {
            url: 'queryCommodityBySid.action?sid=' + id,
            columns: [[
                {field: 'commodityId', title: '商品id', hidden: true},
                {field: 'commodityName', title: '商品名', width: 50, align: 'center'},
                {field: 'commodityOnprice', title: '商品价格', width: 50, align: 'center'},
                {
                    field: 'commodityImage',
                    title: '商品图片',
                    width: 50,
                    align: 'center',
                    formatter: function (value) {
                        return "<img src='" + value + "'>";
                    }
                },
                {
                    field: 'count',
                    title: '采购数量',
                    width: 50,
                    align: 'center',
                    formatter: function (value, row) {
                        // 根据商品id和订单id差商品数量
                        var v = "";
                        $.ajax({
                            url: "queryCountByCidOrSid.action",
                            data: {"cid": row.commodityId, "sid": id},
                            type: "get",
                            async: false,
                            success: function (result) {
                                v =  result ;
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
            pageList: [5, 10, 15, 20, 30],   //初始化页面大小选择列表。
        };
        $("#salesorderDetails_table").datagrid(purchaseorderDetails_param);
    })
</script>
</body>
</html>
