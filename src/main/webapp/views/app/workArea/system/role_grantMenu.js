var xtree;
layui.use(['form'], function () {
    var form = layui.form;
    xtree = new layuiXtree({
        elem: 'grantMenu_tree'
        , form: form
        , data: '../role/treeMenu?id='+$('#selectRoleId').val()     //(必填) json数据
//             , isopen: false  //加载完毕后的展开状态，默认值：true
//             , ckall: true    //启用全选功能，默认值：false
//             , ckallback: function () { } //全选框状态改变后执行的回调函数
//             , icon: {        //三种图标样式，更改几个都可以，用的是layui的图标
//                 open: "&#xe7a0;"       //节点打开的图标
//                 , close: "&#xe622;"    //节点关闭的图标
//                 , end: "&#xe621;"      //末尾节点的图标
//             }
//             , color: {       //三种图标颜色，独立配色，更改几个都可以
//                 open: "#EE9A00"        //节点图标打开的颜色
//                 , close: "#EEC591"     //节点图标关闭的颜色
//                 , end: "#828282"       //末级节点图标的颜色
//             }
// 		            , click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
// 		            }
    });
});

function submitGrantMenuForm(index) {
    var row = xtree.GetChecked();
    var id = $('#selectRoleId').val();
    var arr = new Array();
    if (row.length > 0) {
        for (var i = 0; i < row.length; i++) {
            arr.push(row[i].value);
            var parentNode = xtree.GetParent(row[i].value);
            while (parentNode) {
                arr.push(parentNode.value);
                parentNode = xtree.GetParent(parentNode.value);
            }
        }
    }
    var ids = arr.join(",");
    $.ajax({
        type: "POST",
        url: "../role/grantMenus",
        data: {
            id: id,
            menuIds: ids
        },
        dataType: "json",
        success: function(data) {
            if (data.status == "success") {
                layer.open({title: '提示', content: '操作成功!'});
                layer.close(index);
            } else {
                MSG(data.msg);
            }
        },
        error: function(msg) {
            layer.open({title: '提示', content: '错误了!'});
        }
    });
}
