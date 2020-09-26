<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8"/>
    <title>登录</title>

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

    <%--登录的插件--%>
    <link rel="stylesheet" href="themes/login.css"/>

</head>

<body>
<div class="body">
    <p class="logo">进销存管理系统</p>
    <div class="body_count">
        <div class="login_count">
            <div class="login_count_a">
                <div id="all">
                    <ul id="option">
                        <li class="active login_left">员工登录</li>
                        <li class="login_right">用户登录</li>
                    </ul>
                    <ul id="card">
                        <li class="active">
                            <form id="user_form" method="post">
                                <div class="login_bot_count">
                                    <img src="img/tubiao-07.png"/>
                                    <input type="text" name="userName" id="userName" placeholder="请输入员工账号"/>
                                </div>
                                <div class="login_bot_count">
                                    <img src="img/tubiao-06.png"/>
                                    <input type="password" name="userPassword" id="userPassword" placeholder="请输入密码"/>
                                </div>
                                <input class="login_button" id="user_login_but" value="登录" type="button">
                            </form>
                        </li>

                        <li class="login_account">
                            <form id="other_form" method="post">
                                <div class="login_bot_count">
                                    <img src="img/tubiao-07.png"/>
                                    <input type="text" name="otherName" placeholder="请输入用户账号"/>
                                </div>
                                <div class="login_bot_count">
                                    <img src="img/tubiao-06.png"/>
                                    <input type="password" name="otherPassword" id="otherPassword"
                                           placeholder="请输入密码"/>
                                </div>
                                <input class="login_button" id="other_login_but" value="登录" type="button">
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="js/login.js"></script>
<script>
    $("#user_login_but").click(function () {
        $("#user_form").form('submit', {
            url: "user_login.action",
            success: function (data) {
                if (data == 0) {
                    $("#userPassword").val("");
                    $.messager.show({
                        title: '提示',
                        msg: '登录失败！',
                        timeout: 3000,
                        showType: 'slide'
                    });
                    return;
                }
                location.href = "main.action";
            }
        })
    })

    $("#other_login_but").click(function () {
        $("#other_form").form('submit', {
            url: "other_login.action",
            success: function (data) {
                if (data == 1) {
                    location.href = "supplierMain.action?url=supplierMain";
                    return;
                }
                if(data == 2){
                    location.href = "salesMain.action?url=salesMain";
                    return;
                }
                $("#otherPassword").val("");
                $.messager.show({
                    title: '提示',
                    msg: '登录失败！',
                    timeout: 3000,
                    showType: 'slide'
                });

            }
        })
    })
</script>
</body>

</html>