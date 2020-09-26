<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/22
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>权限管理</title>
</head>
<body>
<div id="menuIfo_main" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',title:'用户选择'" style="width:40%;">
        <!--  显示所有角色-->
        <table id="menuIfo_role_table"></table>
    </div>
    <div id="menuIfo_div" data-options="region:'center',title:'角色选择'" style="padding:5px;">
        <div id="menuInfo_but" <%--align="center"--%>>
            <a id="menuInfo_butOk" class="easyui-linkbutton" data-options="iconCls:' icon-201208041220'">授权</a>
        </div>
        <div>
            <ul id="menuInfo_treeMenu"></ul>
        </div>
    </div>
</div>
<script>
    var param = {
        url: 'queryAllRole.action',
        columns: [[
            {field: 'roleId', title: 'id', hidden: true},
            {field: 'roleName', title: '角色名称', width: 300, align: 'center'},
        ]],
        fitColumns: true,
        striped: true,   //是否显示斑马线效果
        pagination: true,  //则在DataGrid控件底部显示分页工具栏。
        rownumbers: true,   //如果为true，则显示一个行号列。
        singleSelect: true,  //如果为true，则只允许选择一行。
        pageSize: 10,   //在设置分页属性的时候初始化页面大小
        pageList: [10, 15, 20],   //初始化页面大小选择列表。
        onClickRow: function (rowIndex, rowData) {
            //将选中的角色id  传递给  tree控件绑定的地址
            roleId = rowData.roleId;
            //重新加载树控件
            $('#menuInfo_treeMenu').tree('reload');

        }
    };
    $("#menuIfo_role_table").datagrid(param);

    var roleId = 0;
    //权限页面 显示所有的菜单信息
    var menuparam = {
        url: 'querymenus.action',
        checkbox: true,
        queryParams: {"roleId": roleId},  //额外传递参数   post方式
        onBeforeLoad: function (node, param) {  //提交之前触发  参数  param包含这次请求的所有的参数
            param.roleId = roleId;
            return true;
        },
        onBeforeCheck:function () {
            if(roleId == 1){
                return false;
            }
        }
    };
    $('#menuInfo_treeMenu').tree(menuparam);

    $("#menuInfo_butOk").click(function () {
        //获取角色id
        var selrow = $('#menuIfo_role_table').datagrid('getSelected');
        if(selrow ==null){
            alert("请选择一个角色！")
            return;
        }
        var rid = selrow.roleId;
        //获取菜单id
        var menuId = $('#menuInfo_treeMenu').tree('getChecked', ['checked','indeterminate']);
        var menuIds = "";
        if(menuId != null){
            for (var i =0;i<menuId.length;i++){
                menuIds+= menuId[i].id+",";
            }
        }

        $.post("addMenuByRid.action",{"roleId":rid,"menuIds":menuIds},function (data) {
            $.messager.show({
                title:'消息',
                msg:data,
                timeout:5000,
                showType:'slide'
            });
        },"text")
    })
</script>
</body>
</html>
