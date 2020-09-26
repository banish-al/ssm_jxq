<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/24
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="addSupplierWin_form">
    <table id="addSupplierWin_table" style="width: 100%;" class="table_form">
        <tr>
            <td><label>供货商名称:</label></td>
            <td><input id="addSupplierWin_supplierName" name="supplierName" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>供货商地址:</label></td>
            <td>省：<input id="addSupplierWin_sheng" type="text" name="shen" style="width: 150px;"><br>
                市：<input id="addSupplierWin_shi" type="text" name="shi" style="width: 150px;"><br>
                区：<input id="addSupplierWin_qu" type="text" name="qu" style="width: 150px;"></td>
        </tr>
        <tr>
            <td><label>供货商邮箱:</label></td>
            <td><input id="addSupplierWin_supplierMailbox" name="supplierMailbox" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>供货登录密码:</label></td>
            <td><input class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="addSupplierWin_addOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
        </tr>
    </table>
</form>
<script>
    $("#addSupplierWin_addOk").click(function () {
        $('#addSupplierWin_form').form('submit', {
            url: "addSupplier.action",
            onSubmit: function () {
            },
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#supplier_addWin").window("close");
                $("#supplier_table").datagrid('load', {
                    supplierName: $("#supplier_supplierName").val(),
                    supplierAddr: $("#supplier_supplierAddr").val(),
                    supplierMailbox: $("#supplier_supplierMailbox").val()
                });
            }
        })
    })

    // 级联加载
    $("#addSupplierWin_sheng").combobox({
        url: 'queryAllProvince.action',
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#addSupplierWin_shi').combobox('clear'); //先清空下拉框数据
            $('#addSupplierWin_qu').combobox('clear');
            $('#addSupplierWin_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
        }
    });
    $("#addSupplierWin_shi").combobox({
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#addSupplierWin_qu').combobox('clear'); //先清空下拉框数据
            $('#addSupplierWin_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
        }
    });
    $("#addSupplierWin_qu").combobox({
        valueField: 'code',
        textField: 'name'
    });
</script>
</body>
</html>
