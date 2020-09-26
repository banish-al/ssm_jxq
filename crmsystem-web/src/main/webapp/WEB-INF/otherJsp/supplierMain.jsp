<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/31
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>供货商管理</title>
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
        body{
            margin: 0;
        }
        #main_h1{
            font-size: 40px;
            font-family: "楷体";
            font-weight: 900;
            color: #2984A4;
        }
        input,select{
            text-align: center;
            width: 110px;
            border-radius:5px;
            border-color: #a6a6a6;
        }
        .table_form td{
            text-align: left;
            width: 180px;
            margin-left: 20px;
            height: 47px;
            /*border: 1px solid gray;*/
        }
        .table_form input{
            width: 180px;
        }
        .table_form label{
            margin-left: 50px;
        }
        img{
            width: 40px;
            height: 35px;
        }
    </style>
</head>
<body>
<div id="supplier_main" class="easyui-layout" data-options="fit:true" style="width:600px;height:400px;">
    <!-- 头部 -->
    <div data-options="region:'north'" style="height: 110px;text-align: center">
        <h1 id="main_h1">进 销 存 管 理 系 统</h1>
    </div>

    <!-- 左边 -->
    <div data-options="region:'west',title:'菜单管理'" style="width:200px;">
        <ul id="supplier_tree" class="easyui-tree">
            <li>
                <span>商品信息</span>
            </li>
        </ul>
    </div>

    <!-- 中间 -->
    <div data-options="region:'center'">
        <div id="supplier_tt" class="easyui-tabs" data-options="fit:true" style="width:500px;height:250px;">
            <div title="首页" style="padding:20px;display:none;">

            </div>
        </div>
    </div>
</div>
<script>
    $("#supplier_tree").tree({
        onClick: function(){
            //判断是否打开了
            var res = $("#supplier_tt").tabs('exists', "商品信息");
            if (res) {
                //打开  选中已打开
                $("#supplier_tt").tabs('select', "商品信息");
            } else {
                //未打开  添加选项卡
                $("#supplier_tt").tabs('add', {title: "商品信息", closable: true, href: "supplierMain.action?url=supplierMyself"});
            }
        }
    })
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
