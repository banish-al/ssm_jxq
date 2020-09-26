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
<form id="uptSupplierWin_form">
    <table id="uptSupplierWin_table" style="width: 100%;" class="table_form">
        <tr style="display: none">
            <td><label>供货商id:</label></td>
            <td><input id="uptSupplierWin_supplierId" name="supplierId"
                       class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>供货商名称:</label></td>
            <td><input id="uptSupplierWin_supplierName" name="supplierName" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>供货商地址:</label></td>
            <td>省：<input id="uptSupplierWin_sheng" type="text" name="shen" style="width: 150px;"><br>
                市：<input id="uptSupplierWin_shi" type="text" name="shi" style="width: 150px;"><br>
                区：<input id="uptSupplierWin_qu" type="text" name="qu" style="width: 150px;"></td>
        </tr>
        <tr>
            <td><label>供货商邮箱:</label></td>
            <td><input id="uptSupplierWin_supplierMailbox" name="supplierMailbox" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="uptSupplierWin_uptOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
        </tr>
    </table>
</form>
<script>
    $("#uptSupplierWin_uptOk").click(function () {
        $('#uptSupplierWin_form').form('submit', {
            url: "uptSupplier.action",
            onSubmit: function () {
                if (isNull($("#uptSupplierWin_supplierName").val()) || isNull($("#uptSupplierWin_supplierName").val())
                    || isNull($("#uptSupplierWin_sheng").val()) || isNull($("#uptSupplierWin_supplierMailbox").val())) {
                    $.messager.alert('消息', '输入有误不能为空');
                    return false;
                }
            },
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#supplier_uptWin").window("close");
                $("#supplier_table").datagrid('load', {
                    supplierName: $("#supplier_supplierName").val(),
                    supplierAddr: $("#supplier_supplierAddr").val(),
                    supplierMailbox: $("#supplier_supplierMailbox").val()
                });
            }
        });
    })

    // 拿到选中的供应商
    var supplierSelRows = $("#supplier_table").datagrid("getSelected");
    $("#uptSupplierWin_supplierId").val(supplierSelRows.supplierId);
    $("#uptSupplierWin_supplierName").val(supplierSelRows.supplierName);
    $("#uptSupplierWin_supplierMailbox").val(supplierSelRows.supplierMailbox);
    var addr = (supplierSelRows.supplierAddr);
    var addrs = addr.split("/");
    $("#uptSupplierWin_sheng").val(addrs[0]);
    $("#uptSupplierWin_shi").val(addrs[1]);
    $("#uptSupplierWin_qu").val(addrs[2]);

    // 级联加载
    $("#uptSupplierWin_sheng").combobox({
        url: 'queryAllProvince.action',
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#uptSupplierWin_shi').combobox('clear'); //先清空下拉框数据
            $('#uptSupplierWin_qu').combobox('clear');
            $('#uptSupplierWin_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
        }
    });
    $("#uptSupplierWin_shi").combobox({
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#uptSupplierWin_qu').combobox('clear'); //先清空下拉框数据
            $('#uptSupplierWin_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
        }
    });
    $("#uptSupplierWin_qu").combobox({
        valueField: 'code',
        textField: 'name'
    });
</script>
</body>
</html>
