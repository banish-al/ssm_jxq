<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/4
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑客户</title>
</head>
<body>
<form id="uptConnection_form">
    <table id="uptConnection_table" style="width: 100%;" class="table_form">
        <tr style="display: none">
            <td><label>客户id</label></td>
            <td><input id="connection_uptId" name="connectionId" class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>客户姓名:</label></td>
            <td><input id="connection_uptName" name="connectionName" class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>客户电话:</label></td>
            <td><input id="connection_uptPhone" name="connectionPhone" class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>供货商地址:</label></td>
            <td>省：<input id="uptConnectionWin_sheng" type="text" name="shen" style="width: 150px;"><br>
                市：<input id="uptConnectionWin_shi" type="text" name="shi" style="width: 150px;"><br>
                区：<input id="uptConnectionWin_qu" type="text" name="qu" style="width: 150px;"></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="connection_uptOkBut" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a></td>
        </tr>
    </table>
</form>
<script>
    $(function () {
        var connectionRow = $("#connection_table").datagrid("getSelected");
        $("#connection_uptId").val(connectionRow.connectionId);
        $("#connection_uptName").val(connectionRow.connectionName);
        $("#connection_uptPhone").val(connectionRow.connectionPhone);
        var addr = (connectionRow.connectionAddr);
        var addrs = addr.split("/");
        $("#uptConnectionWin_sheng").val(addrs[0]);
        $("#uptConnectionWin_shi").val(addrs[1]);
        $("#uptConnectionWin_qu").val(addrs[2]);

        // 级联加载
        $("#uptConnectionWin_sheng").combobox({
            url: 'queryAllProvince.action',
            valueField: 'code',
            textField: 'name',
            onSelect: function (recod) {
                $('#uptConnectionWin_shi').combobox('clear'); //先清空下拉框数据
                $('#uptConnectionWin_qu').combobox('clear');
                $('#uptConnectionWin_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
            }
        });
        $("#uptConnectionWin_shi").combobox({
            valueField: 'code',
            textField: 'name',
            onSelect: function (recod) {
                $('#uptConnectionWin_qu').combobox('clear'); //先清空下拉框数据
                $('#uptConnectionWin_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
            }
        });
        $("#uptConnectionWin_qu").combobox({
            valueField: 'code',
            textField: 'name'
        });

        $("#connection_uptOkBut").click(function () {
            $('#uptConnection_form').form('submit', {
                url: "uptConnection.action",
                onSubmit: function () {
                    if (isNull($("#connection_uptName").val()) || isNull($("#connection_uptPhone").val())
                        || isNull($("#uptConnectionWin_sheng").val())) {
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
                    $("#connection_uptWin").window("close");
                    $("#connection_table").datagrid('load', {
                        connectionName: $("#connection_selName").val(),
                        connectionPhone: $("#connection_selPhone").val()
                    });
                }
            });
        })
    })
</script>
</body>
</html>
