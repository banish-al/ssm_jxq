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
<form id="addSupplierMyself_form" enctype="multipart/form-data" method="post">
    <table class="table_form" width="100%">
        <tr>
            <td><label>商品名:</label></td>
            <td><input id="addSupplierMyself_name" name="supplierCommodityName" type="text"/>
            </td>
        </tr>
        <tr>
            <td><label>商品价格:</label></td>
            <td><input id="supplierCommodityOnprice" name="supplierCommodityOnprice" type='number' step="0.1" value='1.0' oninput='if(value<1)value=1'/></td>
        </tr>
        <tr>
            <td><label>图片:</label></td>
            <td>
                <img id="addSupplierMyself_image" src="" style="width: 80px;height: 70px;">
                <input id="addSupplierMyself_checkedImage" type="file" name="img">
            </td>
        </tr>
        <tr>
            <td><label>类型:</label></td>
            <td>
                <input type="text" id="add_commoditytypeId" name="commoditytypeId" style="display: none">
                <select id="addSupplierMyself_type">
                    <option value="请选择">请选择</option>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a id="addSupplierMyself_addOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a>
            </td>
        </tr>
    </table>
</form>
<script>
    $("addSupplierMyself_form input").val("");

    $("#addSupplierMyself_checkedImage").change(function () {
        $("#addSupplierMyself_image").attr("src", URL.createObjectURL($(this)[0].files[0]));
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
            $("#addSupplierMyself_type").append(op);
        }
    })
    $("#addSupplierMyself_type").change(function () {
        if ($("#addSupplierMyself_type").val() == "请选择") {
            return;
        }
        $("#add_commoditytypeId").val($("#addSupplierMyself_type").val());
    })
    $("#addSupplierMyself_addOk").click(function () {
        if ($("#addSupplierMyself_type").val() == "请选择") {
            $.messager.alert('消息', '请选择商品类型');
            return;
        }
        if ($("#addSupplierMyself_name").val() == "") {
            $.messager.alert('消息', '输入有空');
            return;
        }
        var fileFlag = false;
        $("#addSupplierMyself_checkedImage").each(function () {
            if ($(this).val() == "") {
                fileFlag = true;
            }
        });
        if($("#supplierCommodityOnprice").val() == ""){
            $.messager.alert('消息', '请指定价格');
            return;
        }
        if(fileFlag){
            $.messager.alert('消息', '选择图片');
            return;
        }
        var param = {
            url: "addSupplierCommodity.action",
            dataType: "text",
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#supplierMyself_addWin").window("close");
                $("#supplierMyself_table").datagrid('load');
            }
        };
        $("#addSupplierMyself_form").ajaxSubmit(param);
    })
</script>
</body>
</html>
