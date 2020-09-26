<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/9/3
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" style="width:600px;height:400px;">
    <!-- 左边 -->
    <div data-options="region:'west',title:'选择商品'" style="width:45%">
        <table id="salesCommodity_table" style="width: 100%;"></table>
    </div>

    <!-- 中间 -->
    <div data-options="region:'east',title:'我的商品'" style="width:55%;">
        <div class="easyui-layout" data-options="fit:true" style="width:600px;height:400px;">
            <%--上面--%>
            <div data-options="region:'north'" style="height: 80%;">
                <table class="easyui-datagrid" id="salesCommodity_salesTable" style="width: 100%;">
                    <thead>
                    <tr>
                        <th data-options="field:'salesCommdityId', hidden: true">商品id</th>
                        <th data-options="field:'salesCommdityName',width:120, align: 'center'">商品名</th>
                        <th data-options="field:'salesCommodityOnprice',width:120, align: 'center'">价格</th>
                        <th data-options="field:'salesCommodityImage',width:120, align: 'center'">图片</th>
                        <th data-options="field:'salesCommdityCount',width:120, align: 'center'">数量</th>
                        <th data-options="field:'salesCommdityMoney',width:120, align: 'center'">单价</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <%--下面--%>
            <div data-options="region:'south',title:'结账'" style="height:20%;align-items: center;">
                <div style="line-height: 50px;float: left;width: 100px">总价:<b id="zongjia">0</b></div>
                <div style="line-height: 50px;float: left;margin-left: 150px">备注:</div>
                <div style="float:left;">
                    <textarea id="salesorderText" style='width:170px;height:60px;resize:none;'></textarea>
                </div>
                <div align="right" style="margin-top: 15px">
                    <a id="salesOk" class="easyui-linkbutton" data-options="iconCls:'icon-add'">提交订单</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="salesMenu" class="easyui-menu" style="width: 80px; display: none;">
    <div id="delSalesCommodity" data-options="iconCls:'icon-edit'">删除此商品</div>
</div>
<script>

    $(function () {
        var param = {
            url: 'queryCommoditySales.action',
            columns: [[
                {field: 'commodityId', title: '商品id', hidden: true},
                {field: 'commodityName', title: '商品名', width: 60, align: 'center'},
                {field: 'commodityOnprice', title: '价格', width: 60, align: 'center'},
                {
                    field: 'commodityImage',
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
                {
                    field: 'kucun', title: '库存', width: 60, align: 'center', formatter: function (value, row) {
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
            ]],
            fitColumns: true,
            toolbar: '#supplierMyself_div',
            striped: true,   //是否显示斑马线效果
            checkOnSelect: false, //点ck才能选中
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 8,   //在设置分页属性的时候初始化页面大小
            pageList: [8, 14, 18],   //初始化页面大小选择列表。
            onSelect: function (rowIndex, rowData) {
                //判断使用已有这条数据
                var allData = $("#salesCommodity_salesTable").datagrid("getRows");
                var flag = true;
                $(allData).each(function (i) {
                    if (allData[i].salesCommdityId == rowData.commodityId) {
                        flag = false;
                    }
                })
                if (flag) {
                    // 添加数据到我的商品表格
                    $('#salesCommodity_salesTable').datagrid('appendRow', {
                        salesCommdityId: rowData.commodityId,
                        salesCommdityName: rowData.commodityName,
                        salesCommodityOnprice: rowData.commodityOnprice,
                        salesCommodityImage: "<img style='width: 50px;height: 45px' src=" + rowData.commodityImage + ">",
                        salesCommdityCount: "<input class='salesPrice' id='salesPrice" + rowData.commodityId + "' oninput='number(this)' " +
                            "type='number' value='1'/>",
                        salesCommdityMoney: "<lable id='moneyLable" + rowData.commodityId + "'>" + rowData.commodityOnprice + "</lable>"
                    });
                    //根据我的商品表格显示总价
                    var salesData = $("#salesCommodity_salesTable").datagrid("getRows");
                    var countPrice = 0;
                    $(salesData).each(function (i) {
                        countPrice += salesData[i].salesCommodityOnprice * $("#salesPrice" + salesData[i].salesCommdityId + "").val();
                    })
                    $("#zongjia").text(countPrice.toFixed(1));
                }
            }
        };
        //设置表格属性
        $("#salesCommodity_table").datagrid(param);
        $("#salesCommodity_salesTable").datagrid({
            rownumbers: true,   //如果为true，则显示一个行号列。
            singleSelect: true,  //如果为true，则只允许选择一行。
            //右键点击
            onRowContextMenu: function (e, rowIndex, rowData) {
                e.preventDefault(); //阻止浏览器捕获右键事件
                $(this).datagrid("clearSelections"); //取消所有选中项
                $(this).datagrid("selectRow", rowIndex); //根据索引选中该行
                $('#salesMenu').menu('show', {
                    left: e.pageX,//在鼠标点击处显示菜单
                    top: e.pageY
                });
                e.preventDefault();  //阻止浏览器自带的右键菜单弹出
            }
        });

    })

    //修改事件
    function number(ob) {
        var va = $(ob).val();
        //最小val为1
        if (va < 1) {
            $(ob).val(1);
        }
        //设置单价
        var selectRow = $("#salesCommodity_salesTable").datagrid("getSelected");
        //判断库存是否足够
        $.ajax({
            url: "queryCommodityCountByCid.action",
            data: {"cid": selectRow.salesCommdityId}, type: "get", async: false, success: function (result) {
                if (va > result) {
                    $(ob).val(result);
                }
            },
        });
        if (selectRow != null) {
            var id = selectRow.salesCommdityId;
            var i = $(ob).val();
            var j = selectRow.salesCommodityOnprice;
            $("#moneyLable" + id).text((i * j).toFixed(1));
        }
        //设置商品总价
        var salesData = $("#salesCommodity_salesTable").datagrid("getRows");
        var countPrice = 0;
        $(salesData).each(function (i) {
            countPrice += salesData[i].salesCommodityOnprice * $("#salesPrice" + salesData[i].salesCommdityId + "").val();
        })
        $("#zongjia").text(countPrice.toFixed(1));
    }

    // 移除右键商品
    $("#delSalesCommodity").click(function () {
        var row = $("#salesCommodity_salesTable").datagrid("getSelected");
        var index = $("#salesCommodity_salesTable").datagrid('getRowIndex', row);
        $("#salesCommodity_salesTable").datagrid('deleteRow', index);
        //设置商品总价
        var salesData = $("#salesCommodity_salesTable").datagrid("getRows");
        var countPrice = 0;
        $(salesData).each(function (i) {
            countPrice += salesData[i].salesCommodityOnprice * $("#salesPrice" + salesData[i].salesCommdityId + "").val();
        })
        $("#zongjia").text(countPrice.toFixed(1));
    })

    // 确认购买。生成销售订单
    $("#salesOk").click(function () {
        var row = $("#salesCommodity_salesTable").datagrid("getRows");
        if (row.length == 0) {
            $.messager.alert('消息', '请选择商品！', 'info');
            return;
        }
        $.messager.confirm('确认', '确定提交？', function (r) {
            if (r) {
                var commoditys = "";
                var counts = "";
                $(row).each(function (i) {
                    commoditys += row[i].salesCommdityId + ",";
                })
                $(".salesPrice").each(function (i) {
                    counts += $(this).val() + ",";
                })
                $.post("addsalesorder.action", {
                    "commoditys": commoditys,
                    "counts": counts,
                    "text": $("#salesorderText").val()
                }, function (data) {
                    $.messager.show({
                        title: '我的消息',
                        msg: data,
                        timeout: 3000,
                        showType: 'slide'
                    });
                    //提交采购清除页面
                    $(row).each(function (i) {
                        var index = $("#salesCommodity_salesTable").datagrid('getRowIndex', row[i]);
                        $("#salesCommodity_salesTable").datagrid('deleteRow', index);
                    })
                    $("#salesorderText").val("");
                    $("#zongjia").val(0);
                }, "text")
            }
        })
    })
</script>
</body>
</html>
