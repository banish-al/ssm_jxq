<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/19
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
</head>
<body>
<div id="userInfo_seldiv">
编号：<input type="text" name="userNumber" id="userInfo_userNumber">
姓名：<input type="text" name="userName" id="userInfo_userName">
状态：<select name="userState" id="userInfo_userState">
    <option>全部</option>
    <option>在职</option>
    <option>请假</option>
    <option>离职</option>
</select>
职位：<select name="roleName" id="userInfo_roleName">
    <option value="0">全部</option>
</select>
<a id="userInfo_selBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
<br>
    <a id="userInfo_add" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
    <a id="userInfo_upt" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
</div>
<table id="userInfo_table"></table>
<!-- 添加窗口-->
<div id="userInfo_addWin" class="easyui-window" title="添加员工"
     style="width:600px;height:550px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'addUserInfoWin.action',closed:true">
</div>
<!-- 修改窗口-->
<div id="userInfo_uptWin" class="easyui-window" title="修改员工"
     style="width:600px;height:500px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'uptUserInfoWin.action',closed:true">
</div>
<script>
    $(function () {
        var param = {
            url: 'queryLikeUser.action',
            columns: [[
                {field: 'ck', title: '复选框', checkbox: true, width: 100},
                {field: 'userId', title: 'id', hidden: true},
                {field: 'userNumber', title: '编号', width: 60, align: 'center'},
                {field: 'userName', title: '姓名', width: 60, align: 'center'},
                {field: 'userSex', title: '性别', width: 60, align: 'center'},
                {field: 'userAge', title: '年龄', width: 60, align: 'center'},
                {field: 'userPhone', title: '联系电话', width: 80, align: 'center',},
                {field: 'userPassword', title: '密码', width: 100, align: 'center', hidden: true},
                {field: 'userAddr', title: '联系地址', align: 'center'},
                {field: 'userState', title: '状态', width: 60, align: 'center'},
                {
                    field: 'roles', title: '职位', align: 'center', formatter: function (value) {
                        var op = "";
                        $(value).each(function (i) {
                            var roleName = value[i].roleName;
                            op += "/" + roleName;
                        });
                        return op.substring(1);
                    }
                },
                /*{field: 'f1', title: '操作', width: 100, align: 'center', formatter: function () {return "<button>修改</button><button>删除</button>";}}*/
            ]],
            fitColumns: true,
            toolbar: '#userInfo_seldiv',
            striped: true,   //是否显示斑马线效果
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15, 20, 30]   //初始化页面大小选择列表。
        };
        $("#userInfo_table").datagrid(param);

        $("#userInfo_selBut").click(function () {
            //调用datagrid load方法 传递要查询的数据给后端
            //刷新绑定地址  额外传递参数
            $("#userInfo_table").datagrid('load', {
                userNumber: $("#userInfo_userNumber").val(),
                userName: $("#userInfo_userName").val(),
                userState: $("#userInfo_userState").val(),
                roleId: $("#userInfo_roleName").val()
            });
        });

        // 查询所有职位
        $.post("queryAllRole.action", function (data) {
            var op = "";
            $(data).each(function (i) {
                op += "<option value=" + data[i].roleId + ">" + data[i].roleName + "</option>";
            });
            $('#userInfo_roleName').append(op);
        }, "json");

        //添加按钮  绑定事件  打开添加窗口
        $("#userInfo_add").click(function () {
            $("#userInfo_addWin").window("open");
        });

        //修改按钮  绑定事件  打开添加窗口
        $("#userInfo_upt").click(function () {
            //获取所有选中行的记录数组
            var selrows = $("#userInfo_table").datagrid("getSelected");
            if (selrows == null) {
                $.messager.alert('消息', '请选择一条记录！', 'info');
                return;
            }
            $("#userInfo_uptWin").window("open");
            $('#userInfo_uptWin').window('refresh');
        });
    })
</script>
</body>
</html>
