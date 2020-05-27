var index;
var operateMode;

layui.use(['layer', 'jquery', 'table'], function() {
    table = layui.table;
    table.render({
        elem: '#noticeListTable',
        url: '../notice/listNotice',
        even: true, //开启隔行背景
        size: 'sm', //小尺寸的表格
        cols: [
            [
                { unresize: true,field: 'noticeTitle',width:'14%',title: '样品编号',align: 'center' },
                { unresize: true,field: 'fileCode', width: '10%', title: '检测类别',align: 'center' },
                { unresize: true,field: 'departmentName', width: '12%', title: '规格尺寸' ,align: 'center'},
                { unresize: true,field: 'transactionName', width: '10%', title: '强度等级',align: 'center' },
                { unresize: true,field: 'draftAuthor', width: '10%', title: '使用地区' ,align: 'center' },
                { unresize: true,field: 'submitter', width: '10%', title: '领样人' ,align: 'center' },
                { unresize: true,field: 'state', width: '12%', title: '领样日期' ,align: 'center' },
                { unresize: true,field: 'submitTime', width: '10%', title: '数据状态' ,align: 'center' },
                { unresize: true,field: 'cuozuo',  width: '12%', align: 'center', title: '操作', toolbar: '#barDemo' }
            ]
        ]

    });
});
function openIndoorTestInfoJcsy(){
    var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitTodoWorkForm",
                arg : ["save"]
            }
        };
    generateModel('indoorTest_info_jcsy','add', "70%", "85%",'检测试验',queryParams, btnOptions);
}
$('.indoorTest_info_left_tree ul li').click(function(){
    $(this).addClass('active').siblings().removeClass('active');
})