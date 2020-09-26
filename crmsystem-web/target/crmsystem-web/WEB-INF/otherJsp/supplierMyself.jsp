<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/31
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="supplierMyself_div">
    商品名：<input type="text" id="supplierMyself_selCName">
    类型：<select id="supplierMyself_selTName">
    <option value="0">全部</option>
</select>
    <a id="supplierMyself_sel" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">查询</a>
    <a id="supplierMyself_add" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
       style="margin-left: 50px">添加</a>
    <a id="supplierMyself_upt" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
</div>
<table id="supplierMyself_table"></table>
<!-- 添加窗口-->
<div id="supplierMyself_addWin" class="easyui-window" title="添加商品"
     style="width:600px;height:350px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'supplierMain.action?url=addSupplierMyself',closed:true">
</div>
<!-- 修改窗口-->
<div id="supplierMyself_uptWin" class="easyui-window" title="编辑商品"
     style="width:600px;height:350px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'supplierMain.action?url=uptSupplierMyself',closed:true">
</div>
<script>
    $.ajax({
        type: 'post',
        url: 'queryAllCommoditytype.action',
        async: false,//false代表只有在等待ajax执行完毕后才执行window.open('http://www.phpernote.com')语句
        success: function (data) {
            var op = "";
            $(data).each(function (i) {
                op += "<option value='" + data[i].commoditytypeId + "'>" + data[i].commoditytypeName + "</option>";
            })
            $("#supplierMyself_selTName").append(op);
        }
    })
    $(function () {
        var param = {
            url: 'querySupplierCommodityBySid.action',
            columns: [[
                {field: 'supplierCommodityId', title: '供货商商品id', hidden: true},
                {field: 'supplierCommodityName', title: '商品名', width: 60, align: 'center'},
                {field: 'supplierCommodityOnprice', title: '价格', width: 60, align: 'center'},
                {
                    field: 'supplierCommodityImage',
                    title: '图片',
                    width: 60,
                    align: 'center',
                    formatter: function (value) {
                        return "<img src='" + value + "' style='width: 50px;height: 39px'>";
                    }
                },
                {
                    field: 'commoditytype', title: '类型', width: 60, align: 'center', formatter: function (value) {
                        return value.commoditytypeName;
                    }
                },
            ]],
            fitColumns: true,
            toolbar: '#supplierMyself_div',
            striped: true,   //是否显示斑马线效果
            checkOnSelect: false, //点ck才能选中
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 10,   //在设置分页属性的时候初始化页面大小
            pageList: [10, 15, 20, 30],   //初始化页面大小选择列表。
        };
        $("#supplierMyself_table").datagrid(param);


        $("#supplierMyself_sel").click(function () {
            refreshTable();
        })

        function refreshTable(){
            $("#supplierMyself_table").datagrid('load',{
                "supplierCommodityName":$("#supplierMyself_selCName").val(),
                "tid":$("#supplierMyself_selTName").val()
            });
        }

        //修改按钮  绑定事件
        $("#supplierMyself_upt").click(function () {
            //获取所有选中行的记录数组
            var selrows = $("#supplierMyself_table").datagrid("getSelected");
            if (selrows == null) {
                $.messager.alert('消息', '请选择一条记录！', 'info');
                return;
            }
            $("#supplierMyself_uptWin").window("open");
            $('#supplierMyself_uptWin').window('refresh');
        });

        //绑定事件  打开添加窗口
        $("#supplierMyself_add").click(function () {
            $("#supplierMyself_addWin").window("open");
            $('#supplierMyself_adduWin').window('refresh');
            $(':input', '#addSupplierMyself_form').not(':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
            $("#addSupplierMyself_image").attr("src", "");
        });
    });
</script>
</body>
</html>
