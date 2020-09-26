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
<form id="uptUserInfoWin_form">
    <table id="uptUserInfoWin_table" style="width: 100%;" class="table_form">
        <tr style="display: none">
            <td><label>员工id:</label></td>
            <td><input id="uptUserInfoWin_userId" name="userId" type="text" class="easyui-validatebox"/></td>
        </tr>
        <tr>
            <td><label>员工姓名:</label></td>
            <td><input id="uptUserInfoWin_userName" name="userName" type="text" class="easyui-validatebox"/></td>
        </tr>
        <tr>
            <td><label>员工性别:</label></td>
            <td>
                男：<input name="userSex" checked="checked" type="radio" id="uptUserInfoWin_userSex1"
                         value="男"/><br>
                女：<input name="userSex" type="radio" value="女" id="uptUserInfoWin_userSex2"/>
            </td>
        </tr>
        <tr>
            <td><label>员工年龄:</label></td>
            <td><input id="uptUserInfoWin_userAge" name="userAge" class="easyui-validatebox"
                       data-options="min:18,max:60"/></td>
        </tr>
        <tr>
            <td><label>联系电话:</label></td>
            <td><input id="uptUserInfoWin_userPhone" name="userPhone" class="easyui-validatebox"
                       data-options="validType:'length[11,11]'"/></td>
        </tr>
        <tr>
            <td><label>登录密码:</label></td>
            <td><input id="uptUserInfoWin_userPassword" type="password" name="userPassword" class="easyui-validatebox"/>
            </td>
        </tr>
        <tr>
            <td><label>就职状态:</label></td>
            <td><select id="uptUserInfoWin_userState" name="userState">
                <option>在职</option>
                <option>请假</option>
                <option>离职</option>
            </select></td>
        </tr>
        <tr>
            <td><label>联系地址:</label></td>
            <td>省：<input id="uptUserInfo_sheng" name="shen" type="text" style="width: 150px;"><br>
                市：<input id="uptUserInfo_shi" type="text" name="shi" style="width: 150px;"><br>
                区：<input id="uptUserInfo_qu" type="text" name="qu" style="width: 150px;"></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="uptUserInfoWin_addOk" href="#" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
        </tr>
    </table>
</form>
<script>


    $("#uptUserInfoWin_addOk").click(function () {
        $('#uptUserInfoWin_form').form('submit', {
            url: "uptUser.action",
            onSubmit: function () {
                var userName = $("#uptUserInfoWin_userName").val();
                var userAge = $("#uptUserInfoWin_userAge").val();
                var userPhone = $("#uptUserInfoWin_userPhone").val();
                var userPassword = $("#uptUserInfoWin_userPassword").val();
                if(userName != null && userAge != null && userPassword != null && userPassword != null){
                }else {
                    $.messager.show({
                        title: '我的消息',
                        msg: "修改数据有误，请重新填写",
                        timeout: 3000,
                        showType: 'slide'
                    });
                }
            },
            success: function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#userInfo_uptWin").window("close");
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
    $("#uptUserInfo_sheng").combobox({
        url: 'queryAllProvince.action',
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#uptUserInfo_shi').combobox('clear'); //先清空下拉框数据
            $('#uptUserInfo_qu').combobox('clear');
            $('#uptUserInfo_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
        }
    });
    $("#uptUserInfo_shi").combobox({
        valueField: 'code',
        textField: 'name',
        onSelect: function (recod) {
            $('#uptUserInfo_qu').combobox('clear'); //先清空下拉框数据
            $('#uptUserInfo_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
        }
    });
    $("#uptUserInfo_qu").combobox({
        valueField: 'code',
        textField: 'name'
    });

    // 拿到选中的员工id
    var selrows = $("#userInfo_table").datagrid("getSelected");

    $("#uptUserInfoWin_userId").val(selrows.userId);
    var addrs = "";
    $.get("queryUserById.action", {"userId": selrows.userId}, function (data) {
        // 拿到员工对象，设置他原来的值
        $("#uptUserInfoWin_userName").val(data.userName);
        if (data.userSex == "女") {
            $("#uptUserInfoWin_userSex2").attr("checked", true)
        }
        $("#uptUserInfoWin_userAge").val(data.userAge);
        $("#uptUserInfoWin_userPhone").val(data.userPhone);
        $("#uptUserInfoWin_userPassword").val(data.userPassword);
        var ops = $("#uptUserInfoWin_userState").find("option"); //获取select下拉框的所有值
        for (var j = 1; j < ops.length; j++) {
            if ($(ops[j]).val() == data.userState) {
                $(ops[j]).attr("selected", "selected");
            }
        }
        addrs = data.userAddr.split("/");
        $("#uptUserInfo_sheng").combobox({
            value:addrs[0],
            text:addrs[0]
        })
        $("#uptUserInfo_shi").combobox({
            value:addrs[1],
            text:addrs[1]
        })
        $("#uptUserInfo_qu").combobox({
            value:addrs[2],
            text:addrs[2]
        })
    }, "json");

</script>
</body>
</html>
