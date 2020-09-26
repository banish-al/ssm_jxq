<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/25
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="commodity_slediv">
    商品名：<input type="text" id="commodity_selCommodityName">
    销售日期：<select id="commodity_selcommodityOndate">
    <option>全部</option>
    <option>单月</option>
    <option>半年内</option>
    <option>一年内</option>
</select>
    状态：<select id="commodity_selCommodityState">
    <option>上架</option>
    <option>下架</option>
</select>
    <a id="commodity_selBut" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a><br>
    <a id="commodity_upButt" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
    <a id="commodity_stateInBut" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">下架商品</a>
    <a id="commodity_stateOnBut" style="display: none" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">上架商品</a>
</div>
<!-- 修改窗口-->
<div id="commodity_uptWin" class="easyui-window" title="修改商品信息"
     style="width:600px;height:270px;margin: auto;text-align: center"
     data-options="iconCls:'icon-add',modal:true,href:'dataIndex.action?url=uptCommodityWin',closed:true">
</div>
<table id="commodity_table"></table>
<script>
    $(function () {
        var param = {
            url: 'queryLikeCommodity.action',
            columns: [[

                {field: 'commodityId', title: '商品id', hidden: true},
                {field: 'commodityName', title: '商品名', width: 40, align: 'center'},
                {field: 'commodityCoding', title: '商品编号', width: 60, align: 'center'},
                {
                    field: 'commodityImage', title: '商品图片', width: 40, align: 'center', formatter: function (value) {
                        return "<img src='" + value + "'>";
                    }
                },
                {field: 'commodityInprice', title: '进货价格', width: 30, align: 'center'},
                {field: 'commodityOnprice', title: '出售价格', width: 30, align: 'center'},
                {field: 'commodityIndate', title: '进货日期', width: 40, align: 'center'},
                {
                    field: 'commodityOndate', title: '最近销售日期', width: 40, align: 'center', formatter: function (value) {
                        if (isNull(value)) {
                            return "暂无销售记录";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'warehouses',
                    title: '商品数量',
                    width: 30,
                    align: 'center',
                    formatter: function (value, row, rowIndex) {
                        var v = 0;
                        $.ajax({
                            url: "queryCommodityCountByCid.action",
                            data: {"cid": row.commodityId}, type: "get", async: false, success: function (result) {
                                v = result;
                            },
                        });
                        return v;
                    }
                },
                {field: 'commodityState', title: '状态', width: 30, align: 'center'},
                {
                    field: 'commoditytype', title: '商品类型', width: 40, align: 'center', formatter: function (value) {
                        return value.commoditytypeName;
                    }
                },
                {
                    field: 'supplier', title: '供应商', width: 40, align: 'center', formatter: function (value) {
                        return value.supplierName;
                    }
                },
            ]],
            fitColumns: true,
            toolbar: '#commodity_slediv',
            striped: true,   //是否显示斑马线效果
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            //singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 5,   //在设置分页属性的时候初始化页面大小
            pageList: [5, 10, 15, 20, 30]   //初始化页面大小选择列表。
        };
        $("#commodity_table").datagrid(param);

        $("#commodity_selBut").click(function () {
            load_commodity();
        });

        $("#commodity_upButt").click(function () {
            //获取所有选中行的记录数组
            //var selrows = $("#commodity_table").datagrid("getSelected");
            var selrows = $("#commodity_table").datagrid("getSelections");
            if (selrows.length != 1) {
                $.messager.alert('消息', '请选择一条记录！', 'info');
                return;
            }
            $("#commodity_uptWin").window("open");
            $('#commodity_uptWin').window('refresh');
        });
    });

    $("#commodity_stateInBut").click(function () {
        var selrows = $("#commodity_table").datagrid("getSelections");
        if (selrows.length == 0) {
            $.messager.alert('消息', '请至少选择一条记录！', 'info');
            return;
        }
        $.messager.confirm('确认对话框', '确认下架吗？', function (r) {
            if (r) {
                var ids = "";
                for (var i = 0; i < selrows.length; i++) {
                    ids += selrows[i].commodityId + ",";
                }
                $.get("inCommodity.action", {"arr": ids}, function (data) {
                    //弹出结果消息
                    $.messager.show({
                        title: '消息',
                        msg: data,
                        timeout: 5000,
                        showType: 'slide'
                    });
                    //刷新表格数据
                    load_commodity()
                }, "text");
            }
        })
    })
    $("#commodity_stateOnBut").click(function () {
        var selrows = $("#commodity_table").datagrid("getSelections");
        if (selrows.length == 0) {
            $.messager.alert('消息', '请至少选择一条记录！', 'info');
            return;
        }
        $.messager.confirm('确认对话框', '确认上架吗？', function (r) {
            if (r) {
                var ids = "";
                for (var i = 0; i < selrows.length; i++) {
                    ids += selrows[i].commodityId + ",";
                }
                $.get("onCommodity.action", {"arr": ids}, function (data) {
                    //弹出结果消息
                    $.messager.show({
                        title: '消息',
                        msg: data,
                        timeout: 5000,
                        showType: 'slide'
                    });
                    //刷新表格数据
                    load_commodity()
                }, "text");
            }
        })
    })

    function load_commodity() {
        $("#commodity_table").datagrid('load', {
            commodityName: $("#commodity_selCommodityName").val(),
            commodityOndateText: $("#commodity_selcommodityOndate").val(),
            commodityState: $("#commodity_selCommodityState").val()
        });
        if ($("#commodity_selCommodityState").val() == "下架") {
            $("#commodity_stateOnBut").show();
            $("#commodity_stateInBut").hide();
        }
        if ($("#commodity_selCommodityState").val() == "上架") {
            $("#commodity_stateInBut").show();
            $("#commodity_stateOnBut").hide();
        }
    }
</script>
</body>
</html>
