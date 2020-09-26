<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/23
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form id="addRoleInfo_form">
    <table id="addRoleInfoWin_table" style="width: 100%;" class="table_form">
        <tr>
            <td><label>角色名称:</label></td>
            <td><input id="addRoleInfoWin_roleName" name="roleName"></td>
        </tr>
        <tr>
            <td></td>
            <td><a id="addRoleInfoWin_addOk" class="easyui-linkbutton"
                   data-options="iconCls:'icon-save'" style="text-align: right">提交</a></td>
        </tr>
    </table>
</form>
<script>

    // 确认添加
    $("#addRoleInfoWin_addOk").click(function () {
        var roleName = $("#addRoleInfoWin_roleName").val();
        $('#addRoleInfo_form').form('submit', {
            url: "addRole.action",
            onSubmit: function () {
                if (isNull(roleName)) {
                    $.messager.alert('消息', '角色名不能为空');
                    return false;
                }
            },
            success: function (data) {
                if(data == 0){
                    $.messager.alert('消息', '角色名不能重复');
                }else {
                    $.messager.show({
                        title: '我的消息',
                        msg: data,
                        timeout: 3000,
                        showType: 'slide'
                    });
                    $("#roleInfo_addWin").window("close");
                    $("#roleInfo_user_table").datagrid('load');
                    refresh_roleInfo_role_table();
                }
            }
        });
    })
</script>
</body>
</html>
