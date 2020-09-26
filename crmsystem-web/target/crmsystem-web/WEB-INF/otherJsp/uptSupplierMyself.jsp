<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/31
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="uptSupplierMyself_form" enctype="multipart/form-data" method="post">
    <table class="table_form" width="100%">
        <tr style="display: none">
            <td><label>员工id:</label></td>
            <td><input id="uptSupplierMyself_id" name="supplierCommodityId" type="text" class="easyui-validatebox"/>
            </td>
        </tr>
        <tr>
            <td><label>商品名:</label></td>
            <td><input id="uptSupplierMyself_name" name="supplierCommodityName" type="text" class="easyui-validatebox"/>
            </td>
        </tr>
        <tr>
            <td><label>商品价格:</label></td>
            <td><input id="uptSupplierMyself_price" name="supplierCommodityOnprice" type="text"
                       class="easyui-validatebox"/>
            </td>
        </tr>
        <tr>
            <td><label>图片:</label></td>
            <td>
                <img id="uptSupplierMyself_image" src="" style="width: 80px;height: 70px;">
                <input id="uptSupplierMyself_checkedImage" type="file" name="img">
            </td>
        </tr>
        <tr>
            <td><label>类型:</label></td>
            <td>
                <input type="text" id="commoditytypeId" name="commoditytypeId" style="display: none">
                <select id="uptSupplierMyself_type"></select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a id="uptSupplierMyself_addOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a>
            </td>
        </tr>
    </table>
</form>
<script>
    $("#uptSupplierMyself_checkedImage").change(function () {
        $("#uptSupplierMyself_image").attr("src", URL.createObjectURL($(this)[0].files[0]));
    });
    $.ajax({
        type: 'post',
        url: 'queryAllCommoditytype.action',
        async: false,//false代表只有在等待ajax执行完毕后才执行window.open('http://www.phpernote.com')语句
        success: function (data) {
            var op = "";
            $(data).each(function (i) {
                op += "<option value='" + data[i].commoditytypeId + "'>" + data[i].commoditytypeName + "</option>";
            })
            $("#uptSupplierMyself_type").append(op);
        }
    })

    $("#uptSupplierMyself_type").change(function () {
        $("#commoditytypeId").val($("#uptSupplierMyself_type").val());
    })

    $(function () {
        var selrows = $("#supplierMyself_table").datagrid("getSelected");
        $("#uptSupplierMyself_id").val(selrows.supplierCommodityId);
        $("#uptSupplierMyself_name").val(selrows.supplierCommodityName);
        $("#uptSupplierMyself_price").val(selrows.supplierCommodityOnprice);
        $("#uptSupplierMyself_image").attr("src", selrows.supplierCommodityImage);
        $("#commoditytypeId").val(selrows.commoditytype.commoditytypeId);
        var ops = $("#uptSupplierMyself_type").find("option"); //获取select下拉框的所有值
        for (var j = 0; j < ops.length; j++) {
            if ($(ops[j]).val() == selrows.commoditytype.commoditytypeId) {
                $(ops[j]).attr("selected", "selected");
            }
        }

        $("#uptSupplierMyself_addOk").click(function () {
            var param ={
                url:"uptSupplierCommodity.action",
                dataType:"text",
                success:function (data) {
                    $.messager.show({
                        title: '我的消息',
                        msg: data,
                        timeout: 3000,
                        showType: 'slide'
                    });
                    $("#supplierMyself_uptWin").window("close");
                    $("#supplierMyself_table").datagrid('load');
                }
            };
            $("#uptSupplierMyself_form").ajaxSubmit(param);
        })
    })
</script>
</body>
</html>
