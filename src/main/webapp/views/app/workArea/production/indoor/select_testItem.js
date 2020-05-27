layui.use([ 'form','util' ], function() {
    layui.form;
});
var test_item_id = "";
function loadJcxmTree(xmName) {
    $.get("../testItem/treeCategoryAndItem", function(data) {
        $("#tree_jzqy").empty();
        layui.use('tree', function() {
            layui.tree({
                elem : "#tree_jzqy",
                spread : false,
                nodes : data,
                click : clickJcxm,
            });
        });
    });
}
loadJcxmTree("");
treeActive();
function clickJcxm(item) {
    //alert(tables+'/'+xmname);
    console.log(item);
    test_item_id = item.name;
    $('#test_item_id').val(test_item_id);
}