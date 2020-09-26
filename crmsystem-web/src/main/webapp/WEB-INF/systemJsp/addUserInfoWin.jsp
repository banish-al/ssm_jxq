<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/20
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加员工</title>
</head>
<body>
<form id="addUserInfoWin_form">
    <table id="addUserInfoWin_table" style="width: 100%;" class="table_form">
        <tr>
            <td><label>员工姓名:</label></td>
            <td><input id="addUserInfoWin_userName" autocapitalize="off" name="userName" class="easyui-validatebox"
                       required="required"/></td>
        </tr>
        <tr>
            <td><label>员工性别:</label></td>
            <td>
                <input name="userSex" type="radio" checked="checked" class="easyui-radiobutton" value="男"
                       label="男"/>
                <input name="userSex" class="easyui-radiobutton" value="女" label="女"/>
            </td>
        </tr>
        <tr>
            <td><label>员工年龄:</label></td>
            <td><input id="addUserInfoWin_userAge" name="userAge" class="easyui-numberspinner"
                       data-options="min:18,max:60" required="required"/></td>
        </tr>
        <tr>
            <td><label>联系电话:</label></td>
            <td><input id="addUserInfoWin_userPhone" name="userPhone" class="easyui-textbox"
                       data-options="validType:'length[11,11]'" required="required"/></td>
        </tr>
        <tr>
            <td><label>登录密码:</label></td>
            <td><input id="addUserInfoWin_userPassword" type="password" name="userPassword" class="easyui-validatebox"
                       required="required"/>
            </td>
        </tr>
        <tr>
            <td><label>确认密码:</label></td>
            <td><input id="addUserInfoWin_userPasswordOk" type="password"
                       class="easyui-validatebox" required="required"/></td>
        </tr>
        <tr>
            <td><label>选择职位:</label></td>
            <td>
                <select name="roleId" id="uptUserInfo_roleName">
                </select>
            </td>
        </tr>
        <tr>
            <td><label>联系地址:</label></td>
            <td>省：<input id="addUserInfo_sheng" name="shen" type="text" style="width: 150px;"><br>
                市：<input id="addUserInfo_shi" type="text" name="shi" style="width: 150px;"><br>
                区：<input id="addUserInfo_qu" type="text" name="qu" style="width: 150px;"></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="addUserInfoWin_addOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
        </tr>
    </table>
</form>
<script>
    $("#addUserInfoWin_addOk").click(function () {
        $('#addUserInfoWin_form').form('submit', {
            url: "addUser.action",
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
                }
                if ($("#addUserInfoWin_userPassword").val() != $("#addUserInfoWin_userPasswordOk").val()) {
                    $.messager.show({
                        title: '我的消息',
                        msg: "密码不一致",
                        timeout: 3000,
                        showType: 'slide'
                    });
                    return false
                }
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#userInfo_addWin").window("close");
                $("#userInfo_table").datagrid('load', {
                    userNumber: $("#userInfo_userNumber").val(),
                    userName: $("#userInfo_userName").val(),
                    userState: $("#userInfo_userState").val(),
                    roleId: $("#userInfo_roleName").val()
                });
            }
        });
    });

    // 级联加载
    $("#addUserInfo_sheng").combobox({
        url: 'queryAllProvince.action',
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#addUserInfo_shi').combobox('clear'); //先清空下拉框数据
            $('#addUserInfo_qu').combobox('clear');
            $('#addUserInfo_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
        }
    });
    $("#addUserInfo_shi").combobox({
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#addUserInfo_qu').combobox('clear'); //先清空下拉框数据
            $('#addUserInfo_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
        }
    });
    $("#addUserInfo_qu").combobox({
        valueField: 'code',
        textField: 'name'
    });

    // 查询所有职位
    $.post("queryAllRole.action", function (data) {
        var op = "";
        $(data).each(function (i) {
            if (data[i].roleId != 1) {
                op += "<option value="+data[i].roleId+">" + data[i].roleName + "</option>";
            }
        });
        $('#uptUserInfo_roleName').append(op);
    }, "json");

</script>
</body>
</html>
