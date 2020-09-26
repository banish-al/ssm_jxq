<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/29
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="purchaseorderCaigouWin_div">
    商品名：<input type="text" id="purchaseorderCaigouWin__selCName">
    类型：<select id="purchaseorderCaigouWin__selTName">
    <option value="0">全部</option>
</select>
    <a id="purchaseorderCaigouWin_sel" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">查询</a>
</div>
<table id="purchaseorderCaigouWin_table"></table>
<table id="purchaseorderCaigouWin_table2" style="width: 100%;" class="table_form">
    <tr>
        <td>
            <label>采购商品：</label><br>
            <select multiple='multiple' id="selectCommoditys" style="width: 200px;height: 100px">
            </select>
        </td>
        <td>
            <label>备注：</label><br>
            <textarea id="caigoutext" style='width:210px;height:65px;resize:none;'>
            </textarea>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <a id="purchaseorderCaigouWin_caigouBut" class="easyui-linkbutton"
               data-options="iconCls:'icon-edit'">确认采购</a>
        </td>
    </tr>
</table>


<script>
    // 判断select下的option是否存在
    function jsSelectIsExitItem(id) {
        var isExit = false;
        for (var i = 0; i < $("#selectCommoditys option").size(); i++) {
            if ($("#selectCommoditys option")[i].value == id) {
                isExit = true;
            }
        }
        return isExit;
    }

    $.ajax({
        type: 'post',
        url: 'queryAllCommoditytype.action',
        async: false,//false代表只有在等待ajax执行完毕后才执行window.open('http://www.phpernote.com')语句
        success: function (data) {
            var op = "";
            $(data).each(function (i) {
                op += "<option value='" + data[i].commoditytypeId + "'>" + data[i].commoditytypeName + "</option>";
            })
            $("#purchaseorderCaigouWin__selTName").append(op);
        }
    })


    $("#selectCommoditys").html("");
    $("#caigoutext").val("");

    $(function () {

        var param = {
            url: 'querySupplierCommodity.action',
            columns: [[
                {field: 'ck', title: '复选框', checkbox: true},
                {field: 'supplierCommodityId', title: '供货商商品id', hidden: true},
                {field: 'supplierCommodityName', title: '商品名', width: 60, align: 'center'},
                {field: 'supplierCommodityOnprice', title: '价格', width: 60, align: 'center'},
                {
                    field: 'supplierCommodityImage',
                    title: '图片',
                    width: 60,
                    align: 'center',
                    formatter: function (value) {
                        return "<img src='" + value + "' style='height: 50px;width: 55px'>";
                    }
                },
                {
                    field: 'commoditytype', title: '类型', width: 60, align: 'center', formatter: function (value) {
                        return value.commoditytypeName;
                    }
                },
                {
                    field: 'supplier', title: '供货商', width: 60, align: 'center', formatter: function (value) {
                        return value.supplierName;
                    }
                },
                {
                    field: 'counts', title: '数量', width: 60, align: 'center', formatter: function (value) {
                        var s = "<input class='purchaseorderCaigouWin_count' type='number' value='1' oninput='if(value<1)value=1'/>";
                        return s;
                    }
                },
            ]],
            fitColumns: true,
            toolbar: '#purchaseorderCaigouWin_div',
            striped: true,   //是否显示斑马线效果
            checkOnSelect: false, //点ck才能选中
            pagination: true,  //则在DataGrid控件底部显示分页工具栏。
            rownumbers: true,   //如果为true，则显示一个行号列。
            //singleSelect: true,  //如果为true，则只允许选择一行。
            pageSize: 7,   //在设置分页属性的时候初始化页面大小
            pageList: [7, 10, 15, 20, 30],   //初始化页面大小选择列表。
            onCheck: function (rowIndex, rowData) {
                var commodityId = rowData.supplierCommodityId;
                var commodityName = rowData.supplierCommodityName;
                if (jsSelectIsExitItem(commodityId)) {
                    return;
                }
                var count = "";
                for (var i = 0; i < $(".purchaseorderCaigouWin_count").size(); i++) {
                    count = $(".purchaseorderCaigouWin_count")[rowIndex].value;
                    break;
                }
                var op = "<option value='" + commodityId + "'>" + commodityName + "*" + count + "</option>"
                $("#selectCommoditys").append(op);
            },
            onUncheck: function (rowIndex, rowData) {
                var commodityId = rowData.supplierCommodityId;
                $("#selectCommoditys option[value='" + commodityId + "']").remove();
            },
        };
        $("#purchaseorderCaigouWin_table").datagrid(param);


        $("#purchaseorderCaigouWin_sel").click(function () {
            $("#purchaseorderCaigouWin_table").datagrid("load", {
                "supplierCommodityName": $("#purchaseorderCaigouWin__selCName").val(),
                "tid": $("#purchaseorderCaigouWin__selTName").val()
            });
        })

        // 确认采购
        $("#purchaseorderCaigouWin_caigouBut").click(function () {
            if($("#selectCommoditys option").size() == 0){
                $.messager.alert('消息', '还没选商品---');
                return;
            }
            // 获取所有商品id
            var cids = "";
            var counts = "";
            for (var i = 0; i < $("#selectCommoditys option").size(); i++) {
                cids += $("#selectCommoditys option")[i].value + ",";
            }
            for (var i = 0; i < $("#selectCommoditys option").size(); i++) {
                counts += $("#selectCommoditys option")[i].text.split("*")[1] + ",";
            }
            $.post("caigouOk.action", {
                "commoditys": cids,
                "counts": counts,
                "text": $("#caigoutext").val()
            }, function (data) {
                $.messager.show({
                    title: '我的消息',
                    msg: data,
                    timeout: 3000,
                    showType: 'slide'
                });
                $("#purchaseorder_caigouWin").window("close");
                $("#purchaseorder_table").datagrid('load');
            }, "text")
        })
    })
</script>
</body>
</html>
