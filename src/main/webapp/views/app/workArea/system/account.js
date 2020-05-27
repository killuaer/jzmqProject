var table;
window.app.account = function() {
	function init() {
		var html = $.templates.account.render();
		window.work_area.empty();
		window.work_area.html(html);
        accountTable();
	}
	this.backToList = function() {
		location.hash = "/account";
	}
	$.when($.lazyLoadTemplate("account")).done(function() {
		init();
	});
    
    function accountTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#accountTable',
                height: $('.data_list').height(),
                url: '../user/listAccount',
                even: true, //开启隔行背景
                size: 'sm', //小尺寸的表格
                page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    groups: 1, //只显示 1 个连续页码
                    first: false, //不显示首页
                    last: false, //不显示尾页
                    theme: '#008aec'
                },
                cols: [
                    [
                        { unresize: true,width : '5%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                        { unresize: true,field: 'name',width:'25%',title: '用户姓名',align: 'center'},
                        { unresize: true,field: 'loginName', width: '25%', title: '用户账号',align: 'center' },
                        { unresize: true,field: 'email', width: '25%', title: '用户邮箱' ,align: 'center'},
                        { unresize: true,field: 'phone', width: '20%', title: '联系电话' ,align: 'center' }
//                        ,{ unresize: true,field: 'cuozuo',  width: '10%', align: 'center', title: '操作',templet: function(d){
//                            return operateColumn(d.id)
//                        } }
                    ]
                ]

            });
        });
    }
    
//    var operateColumn = function(id) {
//        var row = '';
//        row += '<a href="javascript:void(0);" class="oc" onclick=account.check_entity(\''+id+'\')>查看</a>';
//        return row;
//    }
    this.queryList  = function() {
        var name = $('#name').val();
        var loginName = $('#loginName').val();
        var departId = $('#departId').val();
        table.reload('accountTable', {
          where: { //设定异步数据接口的额外参数，任意设
            name: name,
            loginName: loginName,
            deptId: departId
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#name').val('');
        $('#loginName').val('');
        $('#departId').val('');
        $('#departName').val('');
    }
//    this.check_entity = function(id) {
//        var queryParams = {
//            type: 'check'
//        };
//        generateModel('structure_account','edit', "70%", "85%",queryParams, null, '../user/getUser?id='+id);
//    }
    this.selectDepart = function() {
        var queryParams = {};
        generateModel('select_depart','add', "70%", "85%", "部门信息", queryParams, null);
    }
    
    function selectDepartBackCall(data) {
        var arr1 = new Array();
        var arr2 = new Array();
        if (data.length >= 1) {
            for (i = 0; i < data.length; i++) {
                arr1.push(data[i].name);
                arr2.push(data[i].id);
            }
        }
        var ids1 = arr1.join(",");
        var ids2 = arr2.join(",");
        $('#roleNames').val(ids1);
        $('#roleIds').val(ids2);
    }
    
    return this;
};
var account = window.app.account();
