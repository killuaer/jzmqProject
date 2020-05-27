var table;
window.app.staff = function() {
	function init() {
		var html = $.templates.staff.render();
		window.work_area.empty();
		window.work_area.html(html);
		staffTable();
	}
	this.backToList = function() {
		location.hash = "/staff";
	}
	$.when($.lazyLoadTemplate("staff")).done(function() {
		init();
	});
    
    function staffTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#staffTable',
//                height: $('.data_list').height(),
                url: '../staff/listStaff',
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
                        { unresize: true,field: 'name',width:'6%',title: '姓名',align: 'center'},
                        { unresize: true,field: 'gender',width:'5%',title: '姓别',align: 'center'},
                        { unresize: true,field: 'nation',width:'5%',title: '民族',align: 'center'},
                        { unresize: true,field: 'nativePlace',width:'5%',title: '籍贯',align: 'center'},
                        { unresize: true,field: 'birthday',width:'8%',title: '出生日期',align: 'center'},
                        { unresize: true,field: 'politicsStatus',width:'7%',title: '政治情况',align: 'center'},
                        { unresize: true,field: 'idcard',width:'13%',title: '身份证号码',align: 'center'},
                        { unresize: true,field: 'finishSchool',width:'7%',title: '毕业院校',align: 'center'},
                        { unresize: true,field: 'major',width:'6.5%',title: '专业',align: 'center'},
                        { unresize: true,field: 'busy', width: '6%', title: '职务',align: 'center' },
                        { unresize: true,field: 'workYears', width: '7%', title: '工作年限' ,align: 'center'},
                        { unresize: true,field: 'jobStatus', width: '7%', title: '在职状态' ,align: 'center' },
                        { unresize: true,field: 'caozuo',  width: '12.5%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=staff.check_entity(\''+id+'\')>查看</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=staff.edit_entity(\''+id+'\')>修改</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=staff.del_entity(\''+id+'\')>删除</a>';
        return row;
    }
    //查询
    this.queryList = function() { 
      	console.log(111);
    	
    	var code = $('#code').val();
    	console.log(code);
        var name = $('#name').val();
        var departId = $('#departId').val();
        var major = $('#major').val();
        var busy = $('#busy').val();
        var starttime = $('#starttime').val();
        var endtime = $('#endtime').val();
        table.reload('staffTable', {
          where: { //设定异步数据接口的额外参数，任意设
        	code: code,
        	name: name,
        	departId: departId,
        	major: major,
        	busy: busy,
        	starttime: starttime,
        	endtime:endtime
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    
    //根据日期查询
    layui.use('laydate', function(){
    	var laydate = layui.laydate;
    	//执行一个laydate实例
    	laydate.render({
            elem: '#starttime'
        });
        laydate.render({
            elem: '#endtime'
        });
	});
    
    //重置
    this.toReset  = function() {
    	$('#code').val('');
    	$('#name').val('');
        $('#departId').val('');
        $('#departName').val('');
        $('#major').val('');
        $('#busy').val('');
        $('#starttime').val('');
        $('#endtime').val('');
    }
    
    this.selectDepart = function() {
        var queryParams = {};
        generateModel('select_depart','add', "70%","", '按部门查询',queryParams, null);
    }

    //查看
    this.check_entity = function(id) {
        var queryParams = {
            type: 'check'
        };
        generateModel('staff_info','edit',"70%","", '人员信息查看',queryParams, null, '../staff/getStaff?id='+id);
    }
    
  //删除
	this.del_entity = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../staff/del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('staffTable');	
						   MSG("删除成功");
					   }else{
						   MSG("已有关联，不能删除")
					   }
					},
					error:function(){
					   MSG("异常");
					}       
				});
			});
		});
	}
	
	//增加
    this.add_entity = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitStaffForm",
                arg : ["save"]
            }
        };
        generateModel('staff_info','add', "70%","", '人员信息登记',queryParams, btnOptions);
    }
    
	//修改
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitStaffForm",
                arg : ["save"]
            }
        };
        console.log(id);
        
        generateModel('staff_info','edit', "70%","", '人员信息修改',queryParams, btnOptions, '../staff/getStaff?id='+id);
    }
    
    //选择人员编号查询
    $('#selectRyCodeBtn').click(function() {
        var queryParams = {};
        selectSystemObj('姓名', 'select_ryCode', '70%', '60%', false, queryParams, selectStaffBackCall);
    });

    function selectStaffBackCall(data) {
        var arr1 = new Array();
        var arr2 = new Array();
        if (data.length >= 1) {
            for (i = 0; i < data.length; i++) {
                arr1.push(data[i].code);
                arr2.push(data[i].id);
            }
        }
        var ids1 = arr1.join(",");
        var ids2 = arr2.join(",");
        $('#code').val(ids1);
        $('#id').val(ids2);
    }
    
    return this;
};
var staff = window.app.staff();
