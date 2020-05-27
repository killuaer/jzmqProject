var xtree;
var xtree2;
layui.use(['form'], function () {
    var form = layui.form;
    xtree = new layuiXtree({
        elem: 'grantDepart_tree'
        , form: form
        , data: '../dept/xtreeDept?id='+$('#selectRoleId').val()     //(必填) json数据
    });
    xtree2 = new layuiXtree({
        elem: 'grantItem_tree'
        , form: form
        , data: '../testItem/xtreeCategoryAndItem?id='+$('#selectRoleId').val()     //(必填) json数据
        , isopen: false  //加载完毕后的展开状态，默认值：true
    });
});

function submitDataPermissionForm(index) {
    var row = xtree.GetChecked();
    var row2 = xtree2.GetChecked();
    
    var id = $('#selectRoleId').val();
    var arr = new Array();
    var arr2 = new Array();
    if (row.length > 0) {
        for (var i = 0; i < row.length; i++) {
            arr.push(row[i].value);
//            var parentNode = xtree.GetParent(row[i].value);
//            while (parentNode) {
//                arr.push(parentNode.value);
//                parentNode = xtree.GetParent(parentNode.value);
//            }
        }
    }
    if (row2.length > 0) {
        for (var i = 0; i < row2.length; i++) {
            arr2.push(row2[i].value);
//            var parentNode = xtree.GetParent(row[i].value);
//            while (parentNode) {
//                arr.push(parentNode.value);
//                parentNode = xtree.GetParent(parentNode.value);
//            }
        }
    }
    console.log(arr);
    console.log(arr2);
    MSG('待开发');
    return false;
    var ids = arr.join(",");
    $.ajax({
        type: "POST",
        url: "..//",
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
