<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/30
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a id="purchaseorderDetails_del" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" style="display: none">删除</a>
<table id="purchaseorderDetails_table"></table>
<table style="width: 100%;display: none" id="purchaseorder_show" class="table_form">
    <tr>
        <td>
            <label>备注：</label><br>
            <textarea id="purchaseorderDetails_text" style='width:210px;height:65px;resize:none;'>
            </textarea>
        </td>
        <td>
            <a id="purchaseorderDetails_uptOk" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">完成修改</a>
        </td>
    </tr>
</table>
<script>
    var purchaseorderSelRow = $("#purchaseorder_table").datagrid("getSelected");
    if(purchaseorderSelRow.purchaseorderState == "待审核"){
       $("#purchaseorder_show").show();
        $("#purchaseorderDetails_del").show();
    }
    var id = purchaseorderSelRow.purchaseorderId;
    var purchaseorderDetails_param = {
        url: 'queryPurchaseorderDetailsByPid.action?pid=' + id,
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
                        data: {"cid": row.supplierCommodityId, "pid": id},
                        type: "get",
                        async: false,
                        success: function (result) {
                            v = "<input class='purchaseorderDetails_count' type='number' value='" + result + "' oninput='if(value<1)value=1'/>";
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
    $("#purchaseorderDetails_table").datagrid(purchaseorderDetails_param);

    $.get("queryPurchaseorderTextByPid.action", {"pid": id}, function (data) {
        $("#purchaseorderDetails_text").val(data);
    }, "text")

    $("#purchaseorderDetails_del").click(function () {
        var row = $("#purchaseorderDetails_table").datagrid("getSelections");
        if (row.length < 1) {
            $.messager.alert('消息', '请选择一条记录！', 'info');
            return;
        }
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
            if (r){
                var ids = "";
                $(row).each(function (i) {
                    ids += row[i].supplierCommodityId + ",";
                })
                $.post("delPurchaseorderDetailsByCid.action", {"ids": ids}, function (data) {
                    $.messager.show({
                        title: '我的消息',
                        msg: data,
                        timeout: 3000,
                        showType: 'slide'
                    });
                    $("#purchaseorder_table").datagrid("load");
                    $("#PurchaseorderDetails_Win").datagrid("load");
                }, "text")
            }
        });

    })

    $("#purchaseorderDetails_uptOk").click(function () {
        var allData = $("#purchaseorderDetails_table").datagrid("getRows");
        var ids = "";
        var counts = "";
        $(allData).each(function (i) {
            ids += allData[i].supplierCommodityId + ",";
        })
        $($(".purchaseorderDetails_count")).each(function (i) {
            counts += $(this).val() + ",";
        })
        $.post("uptPurchaseorderDetailsByCid.action", {
            "ids": ids,
            "counts": counts,
            "text": $("#purchaseorderDetails_text").val(),
            "pid":id
        }, function (data) {
            $.messager.show({
                title: '我的消息',
                msg: data,
                timeout: 3000,
                showType: 'slide'
            });
           // $("#PurchaseorderDetails_Win").window("close");
            $("#purchaseorder_table").datagrid("load");
            $("#PurchaseorderDetails_Win").datagrid("load");
        }, "text")
    })
</script>
</body>
</html>
