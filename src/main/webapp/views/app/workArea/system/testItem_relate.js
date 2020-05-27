layui.use(['layer', 'jquery', 'table'], function() {
    table.render({
        elem: '#unSelectXmTable',
        height: $('#xmtree_table').height() - 47,
        url: '../testItem/listItem',
        where: {
            getCategoryIdIsNull: true,
            noPage: true
        },
        even: true, //开启隔行背景
        size: 'sm', //小尺寸的表格
        cols: [
            [
                { unresize: true,width : '5%',checkbox:true},
                { unresize: true,field: 'xmId', width: '25%', title: '项目ID',align: 'center' },
                { unresize: true,field: 'xmName',width:'43%',title: '项目名称',align: 'center'},
                { unresize: true,field: 'pdstd', width: '25%', title: '项目标准号',align: 'center' }
            ]
        ]

    });

    table.render({
        elem: '#selectedXmTable',
        height: $('#xmtree_table').height() - 47,
        url: '../testItem/listItem',
        where : {
            categoryId: $("#selectCategoryId").val(),
            noPage: true
        },
        even: true, //开启隔行背景
        size: 'sm', //小尺寸的表格
        cols: [
            [
                { unresize: true,width : '5%',checkbox:true},
                { unresize: true,field: 'xmId', width: '25%', title: '项目ID',align: 'center' },
                { unresize: true,field: 'xmName',width:'45%',title: '项目名称',align: 'center'},
                { unresize: true,field: 'pdstd', width: '25%', title: '项目标准号',align: 'center' }
            ]
        ]

    });
});

function submitRelateXmForm(operM) {
    var tableId = 'selectedXmTable';
    if (operM == 'moveIn') {
        tableId = 'unSelectXmTable';
    }
    var checkStatus = table.checkStatus(tableId);
    if (checkStatus.data.length > 0) {
        var xmIdArr = new Array();
        for (var j = 0; j < checkStatus.data.length; j++) {
            xmIdArr.push(checkStatus.data[j].id);
        }
        var categoryId = $("#selectCategoryId").val();
        $.ajax({
            url: "../testItem/relateItem",
            type: "post",
            dataType: "json",
            data: {categoryId:categoryId,xmIds:xmIdArr.join(','),type:operM},
            success: function (data) {
                if (data.status == "success") {
                    if (operM == 'moveIn') {
                        MSG("移入成功");
                    } else {
                        MSG("移出成功");
                    }
                    table.reload('unSelectXmTable');
                    table.reload('selectedXmTable');
                    
                } else {
                    MSG("异常 " + data.msg)
                }
            },
            error: function () {
                MSG("异常");
            }
        });
    } else {
        MSG('请选择检测项目');
    }
}