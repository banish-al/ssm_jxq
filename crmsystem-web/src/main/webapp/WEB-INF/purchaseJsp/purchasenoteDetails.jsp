<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/1
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>采购单详情</title>
</head>
<body>
<table style="width: 100%;" class="table_form">
    <tr>
        <td>采购订单申请人：<b id="purchasenoteDetails_sqr"></b></td>
        <td>采购订单生成日期：<b id="purchasenoteDetails_rq"></b></td>
    </tr>
    <tr>
        <td>采购订单编号：<b id="purchasenoteDetails_bh"></b></td>
        <td>订单总价：<b id="purchasenoteDetails_zj"></b></td>
    </tr>
</table>
<table id="purchasenoteDetails_table"></table>
<script>
    $(function () {
        var purchasenoteSelRow = $("#purchasenote_table").datagrid("getSelected");

        $("#purchasenoteDetails_sqr").text(purchasenoteSelRow.purchaseorder.users.userName);
        $("#purchasenoteDetails_rq").text(purchasenoteSelRow.purchaseorder.purchaseorderCreatedate);
        $("#purchasenoteDetails_bh").text(purchasenoteSelRow.purchaseorder.purchaseorderNumber);
        $("#purchasenoteDetails_zj").text(purchasenoteSelRow.purchaseorder.purchaseorderTotalprice);
        var purchaseorderId = purchasenoteSelRow.purchaseorder.purchaseorderId;//得到采购订单id
        var purchasenoteDetails_param = {
            url: 'queryPurchaseorderDetailsByPid.action?pid=' + purchaseorderId,
            columns: [[
                {field: 'supplierCommodityId', title: '商品id', hidden: true},
                {field: 'supplierCommodityName', title: '商品名', width: 50, align: 'center'},
                {field: 'supplierCommodityOnprice', title: '商品价格', width: 50, align: 'center'},
                {
                    field: 'supplierCommodityImage',
                    title: '商品图片',
                    width: 50,
                    align: 'center',
                    formatter: function (value) {
                        return "<img src='" + value + "'>";
                    }
                },
                {
                    field: 'commodityQuantity',
                    title: '采购数量',
                    width: 50,
                    align: 'center',
                    formatter: function (value, row) {
                        // 根据商品id和订单id差商品数量
                        var v = "";
                        $.ajax({
                            url: "queryCountByCidOrPid.action",
                            data: {"cid": row.supplierCommodityId, "pid": purchaseorderId},
                            type: "get",
                            async: false,
                            success: function (result) {
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
            pageList: [5, 10, 15, 20, 30],   //初始化页面大小选择列表。
        };
        $("#purchasenoteDetails_table").datagrid(purchasenoteDetails_param);
    })
</script>
</body>
</html>
