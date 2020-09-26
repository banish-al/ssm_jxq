<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/25
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="commodityWin_form" enctype="multipart/form-data" method="post">
    <table id="commodityWin_table" style="width: 100%;" class="table_form">
        <tr style="display: none">
            <td><label>商品id:</label></td>
            <td><input id="commodityWin_commodityId" name="commodityId"
                       class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>商品名称:</label></td>
            <td><input id="commodityWin_commodityName" name="commodityName" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>商品图片:</label></td>
            <td>
                <img id="uptCommodityWin_img" src="img/tubiao-07.png" style="width: 80px;height: 70px;">
                <input id="uptCommodityWin_checkedImg" type="file" name="img">
            </td>
        </tr>
        <tr>
            <td><label>出售价格:</label></td>
            <td><input id="commodityWin_commodityOnprice" name="commodityOnprice" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="commodityrWin_uptOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
        </tr>
    </table>
</form>

<script>
    $("#uptCommodityWin_checkedImg").change(function () {
        $("#uptCommodityWin_img").attr("src", URL.createObjectURL($(this)[0].files[0]));
    });
    // 拿到选中的供应商
    var supplierSelRows = $("#commodity_table").datagrid("getSelected");
    $("#commodityWin_commodityId").val(supplierSelRows.commodityId);
    $("#commodityWin_commodityName").val(supplierSelRows.commodityName);
    $("#uptCommodityWin_img").attr("src", supplierSelRows.commodityImage);
    $("#commodityWin_commodityOnprice").val(supplierSelRows.commodityOnprice);

    $("#commodityrWin_uptOk").click(function () {
        var param ={
            url:"uptCommodity.action",
            dataType:"text",
            success:function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#commodity_uptWin").window("close");
                load_commodity();
            }
        };
        $("#commodityWin_form").ajaxSubmit(param);
    })
</script>
</body>
</html>
