<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2020/8/27
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4></h4>
<form id="turnInventory_form">
    <table id="turnInventory_table" style="width: 100%;" class="table_form"></table>
    <input id="turnInventory_turnwid" name="turnwid" style="display: none">
    <b style='color: #8d8d8d'>选择要转到的仓库：</b><select id="turnInventory_select" name="wid"></select>
</form>
<a id="turnInventory_turnOk" class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
<script>
    $(function () {
        // 查询此仓库剩余容量
        var wc = 0;
        $.get("queryWarehouseMaxCount.action", {"wid": $("#queryInventory_warehouseName").val()}, function (data) {
            wc = data;
        },"text");

        $("#turnInventory_turnOk").click(function () {
            $('#turnInventory_form').form('submit', {
                url: "turnInventory.action",
                onSubmit: function () {
                    // 判断需要转到的仓库库存够不够
                    var count = 0;
                    $("input[name='turnCommodityCount']").each(function () {
                        count += parseInt($(this).val());
                    });
                    if(count > wc){
                        $.messager.alert('消息', '此仓库容量不足转库，请尝试其他仓库');
                        return false;
                    }
                },
                success: function (data) {
                    $.messager.show({
                        title: '我的消息',
                        msg: data,
                        timeout: 3000,
                        showType: 'slide'
                    });
                    $("#queryInventory_turnDiv").window("close");
                    $("#queryInventory_table").datagrid('load', {
                        wid: $("#queryInventory_warehouseName").val(),
                        commodityName: $("#queryInventory_selCommodityName").val(),
                    });
                }
            });
            $("#queryInventory_turnDiv").window("close");
        })

        $("#turnInventory_turnwid").val($("#queryInventory_warehouseName").val());

        // 获取下拉框下的所有option除了被转库的
        var ops = "";
        $("#queryInventory_warehouseName option").each(function (i) {
            var id = $(this).val();
            var name = $(this).text();
            if ($("#queryInventory_warehouseName").val() == id) {
            } else {
                ops += "<option value='" + id + "'>" + name + "</option>";
            }
        })
        $("#turnInventory_select").append(ops);

        var selRows = $("#queryInventory_table").datagrid("getSelections");
        var table = "";
        $(selRows).each(function (i) {
            var commodityId = selRows[i].commodityId;
            var commodityName = selRows[i].commodityName;
            var commodityCount = 0;
            var wid = $("#queryInventory_warehouseName").val();
            $.ajax({
                url: "queryCommodityCountByOneCid.action",
                data: {"cid": commodityId, "wid": wid}, type: "get", async: false, success: function (result) {
                    commodityCount = result;
                },
            });
            var tr_commodityId = "<input name=\"turnCommodityId\" value='" + commodityId + "'style='display: none'/>";
            var tr_commodityName = "<tr>\n" +
                "            <td><label>商品名字:</label></td>\n" +
                "            <td><input class=\"easyui-validatebox\"\n" +
                "                       disabled=disabled required=\"required\" value='" + commodityName + "'/></td>\n" +
                "        </tr>";

            var tr_commodityCount = "<tr>\n" +
                "            <td><label>转库数量:</label></td>\n" +
                "            <td><input name=\"turnCommodityCount\" class=\"easyui-numberspinner\"\n" +
                "                       required=\"required\" data-options=\"min:0,max:" + commodityCount + "\" value='0'/>" +
                "                 <b style='color: #8d8d8d;font-size: 12px'>最大数量：" + commodityCount + "</b></td>\n" +
                "        </tr>";
            table += tr_commodityId + tr_commodityName + tr_commodityCount + "<hr style='margin: 20px 0px 20px 0px'>";
        })
        $("#turnInventory_table").append(table);

    })

</script>
</body>
</html>
