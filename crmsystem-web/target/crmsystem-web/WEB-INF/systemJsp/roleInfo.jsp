<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/22
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
</head>
<body>
<div id="roleInfo_main" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',title:'用户选择'" style="width:30%;">
        <!--  显示所有角色-->
        <table id="roleInfo_user_table"></table>
    </div>

    <div id="roleInfo_div" data-options="region:'center',title:'角色选择'" style="padding:5px;">
        <table id="roleInfo_role_table"></table>
        <div id="roleInfo_but">
            <a id="roleInfo_butOk" class="easyui-linkbutton" data-options="iconCls:' icon-201208041220'">授权</a>
            <a id="roleInfo_addRole" class="easyui-linkbutton" data-options="iconCls:' icon-201208041220'">添加角色</a>
        </div>
    </div>
    <!-- 添加窗口-->
    <div id="roleInfo_addWin" class="easyui-window" title="添加角色" style="width:500px;height:160px;margin: auto;text-align: center"
         data-options="iconCls:'icon-add',modal:true,href:'systemIndex.action?url=addRoleInfo',closed:true">
    </div>
</div>

<script>
    var param = {
        url: 'queryLikeUser.action',
        columns: [[
            {field: 'ck', title: '复选框', checkbox: true, width: 100},
            {field: 'userId', title: 'id', hidden: true},
            {field: 'userNumber', title: '编号', width: 60, align: 'center'},
            {field: 'userName', title: '姓名', width: 60, align: 'center'}
        ]],
        fitColumns: true,
        toolbar: '#tbuser',
        striped: true,   //是否显示斑马线效果
        pagination: true,  //则在DataGrid控件底部显示分页工具栏。
        rownumbers: true,   //如果为true，则显示一个行号列。
        singleSelect: true,  //如果为true，则只允许选择一行。
        pageSize: 10,   //在设置分页属性的时候初始化页面大小
        pageList: [10, 15, 20],   //初始化页面大小选择列表。
        onClickRow: function (index, data) {
            // 点击前先清空复选框的选中
            $(".roleBox").each(function (i) {
                $(this).prop("checked", false);
            })

            // 如果员工已有角色就选中
            $(data.roles).each(function (i) {
                var roleId = data.roles[i].roleId;
                $(".roleBox").each(function (i) {
                    if ($(this).val() == roleId) {
                        $(this).prop("checked", true);
                    }
                })
            })
        }
    };
    $("#roleInfo_user_table").datagrid(param);

    refresh_roleInfo_role_table();

    // 刷新角色选项
    function refresh_roleInfo_role_table(){
        // 先清空表
        $("#roleInfo_role_table").html("");
        // 再刷新表
        $.post("queryAllRole.action", function (data) {
            var ckbox = "";
            $(data).each(function (i) {
                disabled ="";
                if(data[i].roleName == "总经理"){
                    disabled = "disabled='disabled'";
                }
                ckbox += "<tr><td>" + data[i].roleName + "</td>" +
                    "<td>" + "<input type='checkbox' class='roleBox' "+disabled+" value='" + data[i].roleId + "' name='roleId'>" + "</td></tr>";
            })
            $("#roleInfo_role_table").append(ckbox);
        })
    }



    // 授权
    $("#roleInfo_butOk").click(function () {
        // 拿到员工id
        var selrows = $("#roleInfo_user_table").datagrid("getSelected");
        var userId = selrows.userId;

        // 拿到角色id集合
        var roleIds = "";
        $(".roleBox").each(function (i) {
            if ($(this).prop("checked") == true) {
                roleIds += $(this).val();
            }
        })
        //执行 确认授权
        $.post("addUserRole.action", {"userId": userId, "roleIds": roleIds}, function (data) {
            $.messager.show({
                title: '我的消息',
                msg: data,
                timeout: 3000,
                showType: 'slide'
            });
            // 刷新表格
            $("#roleInfo_user_table").datagrid("reload");
            refresh_roleInfo_role_table();
        }, "text");
    })

    // 跳到添加角色页面
    $("#roleInfo_addRole").click(function () {
        $("#roleInfo_addWin").window("open");
    })
</script>
</body>
</html>
