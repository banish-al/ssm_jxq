<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/4
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="addConnection_form">
    <table id="addConnection_table" style="width: 100%;" class="table_form">
        <tr>
            <td><label>客户姓名:</label></td>
            <td><input name="connectionName" class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>客户电话:</label></td>
            <td><input name="connectionPhone" class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>登录密码:</label></td>
            <td><input class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>供货商地址:</label></td>
            <td>省：<input id="addConnectionWin_sheng" type="text" name="shen" style="width: 150px;"><br>
                市：<input id="addConnectionWin_shi" type="text" name="shi" style="width: 150px;"><br>
                区：<input id="addConnectionWin_qu" type="text" name="qu" style="width: 150px;"></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="connection_addOkBut" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a></td>
        </tr>
    </table>
</form>
<script>
    // 级联加载
    $("#addConnectionWin_sheng").combobox({
        url: 'queryAllProvince.action',
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#addConnectionWin_shi').combobox('clear'); //先清空下拉框数据
            $('#addConnectionWin_qu').combobox('clear');
            $('#addConnectionWin_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
        }
    });
    $("#addConnectionWin_shi").combobox({
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#addConnectionWin_qu').combobox('clear'); //先清空下拉框数据
            $('#addConnectionWin_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
        }
    });
    $("#addConnectionWin_qu").combobox({
        valueField: 'code',
        textField: 'name'
    });
    $("#connection_addOkBut").click(function () {
        $('#addConnection_form').form('submit', {
            url: "addConnetion.action",
            onSubmit: function () {
            },
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#connection_addWin").window("close");
                $("#connection_table").datagrid('load', {
                    connectionName: $("#connection_selName").val(),
                    connectionPhone: $("#connection_selPhone").val()
                });
            }
        })
    })
</script>
</body>
</html>
