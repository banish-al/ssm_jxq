<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.zilong.vo.systemVo.Users" %>
<%@ page import="com.zilong.vo.systemVo.Role" %>
<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/18
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <!-- 3个js  2个css -->
    <!-- jquery -->
    <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
    <!-- easyui -->
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <!-- 语言包 -->
    <script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
    <!-- 主体样式 -->
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <!-- 图标样式 -->
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <!-- 导入扩展图标-->
    <link rel="stylesheet" type="text/css" href="themes/IconExtension.css">

    <%-- form --%>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <style>
        body {
            margin: 0;
        }

        #main_h1 {
            font-size: 40px;
            font-family: "楷体";
            font-weight: 900;
            color: #2984A4;
        }

        input, select {
            text-align: center;
            width: 110px;
            border-radius: 5px;
            border-color: #a6a6a6;
        }

        .table_form td {
            text-align: left;
            width: 180px;
            margin-left: 20px;
            height: 47px;
            /*border: 1px solid gray;*/
        }

        .table_form input {
            width: 180px;
        }

        .table_form label {
            margin-left: 50px;
        }

        img {
            width: 55px;
            height: 50px;
        }
    </style>
</head>
<body>
<div id="main" class="easyui-layout" data-options="fit:true" style="width:600px;height:400px;">
    <!-- 头部 -->
    <div data-options="region:'north'" style="height: 110px;text-align: center">
        <h1 id="main_h1">进 销 存 管 理 系 统</h1>
    </div>

    <!-- 左边 -->
    <div data-options="region:'west',title:'菜单管理'" style="width:200px;">
        <ul id="menutree"></ul>
    </div>

    <!-- 中间 -->
    <div data-options="region:'center'">
        <div id="tt" class="easyui-tabs" data-options="fit:true" style="width:500px;height:250px;">
            <div title="首页" style="padding:20px;display:none;">
                <table id="commodityWin_table" style="width: 500px;" class="table_form">
                    <tr>
                        <td>欢迎登陆：<span id="myName"></span></td>
                    </tr>
                    <tr>
                        <td>我的职位：<span id="myRole"></span></td>
                    </tr>
                    <tr>
                        <td>我的编号：<span id="myNumber"></span></td>
                    </tr>
                    <tr>
                        <td>我的状态：<span id="myState"></span></td>
                    </tr>
                </table>
                <a id="upt_myself" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑个人资料</a>
                <div id="myself_div" class="easyui-window" title="修改员工"
                     style="width: 460px;height: 460px;background-color: linen"
                     data-options="iconCls:'icon-add',modal:true,closed:true">
                    <form id="myself_form">
                        <table class="table_form">
                            <tr>
                                <td style="display: none"><input value="${user.userId}" name="userId"></td>
                                <td><label>员工姓名:</label></td>
                                <td><input id="myself_userName" autocapitalize="off" name="userName" type="text"
                                           class="easyui-validatebox"/></td>
                            </tr>
                            <tr>
                                <td><label>员工性别:</label></td>
                                <td>
                                    男：<input name="userSex" checked="checked" type="radio" id="myself_userSex1"
                                             value="男" style="width: 20px;"/>
                                    女：<input name="userSex" style="width: 20px;" type="radio" value="女"
                                             id="myself_userSex2"/>
                                </td>
                            </tr>
                            <tr>
                                <td><label>员工年龄:</label></td>
                                <td><input id="myself_userAge" name="userAge" class="easyui-validatebox"
                                           data-options="min:18,max:60"/></td>
                            </tr>
                            <tr>
                                <td><label>联系电话:</label></td>
                                <td><input id="myself_userPhone" name="userPhone" class="easyui-validatebox"
                                           data-options="validType:'length[11,11]'"/></td>
                            </tr>
                            <tr>
                                <td><label>登录密码:</label></td>
                                <td><input id="myself_userPassword" type="password" name="userPassword"
                                           class="easyui-validatebox"/>
                                </td>
                            </tr>
                            <tr>
                                <td><label>联系地址:</label></td>
                                <td>省：<input id="myself_sheng" name="shen" type="text" style="width: 120px;"><br>
                                    市：<input id="myself_shi" type="text" name="shi" style="width: 120px;"><br>
                                    区：<input id="myself_qu" type="text" name="qu" style="width: 120px;"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><a id="myself_uptOk" href="#" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-save'" style="text-align: center">提交</a></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function init() { element.setAttribute("AutoComplete", "off"); }
    $(function () {
        $("#upt_myself").click(function () {
            $("#myself_div").window("open");
        })
        var treeparam = {
            url: "menu.action",
            onClick: function (node) {
                //alert(node.text);  // 在用户点击的时候提示
                //alert(node.attributes.url);
                var url = node.url;
                //根节点  不进行任何操作
                if (url == null) {
                    return;
                }
                //判断是否打开了
                var res = $("#tt").tabs('exists', node.text);
                if (res) {
                    //打开  选中已打开
                    $("#tt").tabs('select', node.text);
                } else {
                    //未打开  添加选项卡
                    $("#tt").tabs('add', {title: node.text, closable: true, href: url});
                }
            }
        };
        $("#menutree").tree(treeparam);
        // 级联加载
        $("#myself_sheng").combobox({
            url: 'queryAllProvince.action',
            valueField: 'code',
            textField: 'name',
            onSelect: function (recod) {
                $('#myself_shi').combobox('clear'); //先清空下拉框数据
                $('#myself_qu').combobox('clear');
                $('#myself_shi').combobox('reload', 'queryCityByPid.action?provincecode=' + recod.code);
            }
        });
        $("#myself_shi").combobox({
            valueField: 'code',
            textField: 'name',
            onSelect: function (recod) {
                $('#myself_qu').combobox('clear'); //先清空下拉框数据
                $('#myself_qu').combobox('reload', 'queryAreaByCid.action?citycode=' + recod.code);
            }
        });
        $("#myself_qu").combobox({
            valueField: 'code',
            textField: 'name'
        });
        $.get("queryUserById.action", {"userId": ${user.userId}}, function (data) {
            $("#myName").text(data.userName);
            var ro = "";
            $(data.roles).each(function (i) {
                ro += ","+data.roles[i].roleName;
            })
            $("#myRole").text(ro.substring(1));
            $("#myNumber").text(data.userNumber);
            $("#myState").text(data.userState);
            // 拿到员工对象，设置他原来的值
            $("#myself_userName").val(data.userName);
            if (data.userSex == "女") {
                $("#myself_userSex2").attr("checked", true)
            }
            $("#myself_userAge").val(data.userAge);
            $("#myself_userPhone").val(data.userPhone);
            $("#myself_userPassword").val(data.userPassword);
            addrs = data.userAddr.split("/");
            $("#myself_sheng").combobox({
                value:addrs[0],
                text:addrs[0]
            })
            $("#myself_shi").combobox({
                value:addrs[1],
                text:addrs[1]
            })
            $("#myself_qu").combobox({
                value:addrs[2],
                text:addrs[2]
            })
        }, "json");

        $("#myself_uptOk").click(function () {
            $('#myself_form').form('submit', {
                url: "uptUser.action",
                onSubmit: function () {
                    var userName = $("#myself_userName").val();
                    var userAge = $("#myself_userAge").val();
                    var userPhone = $("#myself_userPhone").val();
                    var userPassword = $("#myself_userPassword").val();
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
                    location.reload();
                }
            });
        })
    });

    // 判断字符串是否为空的方法
    function isNull(str) {
        if (str == "" || str == null || str == undefined) {
            return true;
        }
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        return re.test(str);
    }

</script>
</body>
</html>
