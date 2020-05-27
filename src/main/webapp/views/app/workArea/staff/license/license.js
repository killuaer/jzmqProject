var table;
window.app.license = function() {
	function init() {
		var html = $.templates.license.render();
		window.work_area.empty();
		window.work_area.html(html);
		licenseTable();
	}
	this.backToList = function() {
		location.hash = "/license";
	}
	$.when($.lazyLoadTemplate("license")).done(function() {
		init();
	});
    
    function licenseTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#licenseTable',
                height: $('.data_list').height(),
                url: '../license/listLicense',
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
                        { unresize: true,field: 'staffName',width:'6%',title: '姓名',align: 'center'},
                        { unresize: true,field: 'staffGender',width:'5%',title: '姓别',align: 'center'},
                        { unresize: true,field: 'type',width:'8%',title: '上岗证类别',align: 'center'},
                        { unresize: true,field: 'code',width:'7%',title: '上岗证号',align: 'center'},
                        { unresize: true,field: 'validTerm',width:'8%',title: '有效期限',align: 'center'},
                        { unresize: true,field: 'name',width:'9%',title: '上岗证名称',align: 'center'},
                        { unresize: true,field: 'staffAge',width:'7%',title: '年龄',align: 'center'},
                        { unresize: true,field: 'staffIdCard',width:'11%',title: '身份证号',align: 'center'},
                        { unresize: true,field: 'staffTitle',width:'6.5%',title: '职称',align: 'center'},
                        { unresize: true,field: 'staffEdu', width: '7%', title: '最高学历',align: 'center' },
                        { unresize: true,field: 'staffMajor', width: '7%', title: '专业' ,align: 'center'},
                        { unresize: true,field: 'caozuo',  width: '14.5%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=license.check_entity(\''+id+'\')>查看</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=license.edit_entity(\''+id+'\')>修改</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=license.del_entity(\''+id+'\')>删除</a>';
        return row;
    }
    //查询
    this.queryList  = function() {
    	var licenceHolder = $('#licenceHolder').val();
        var code = $('#code').val();
        var type = $('#type').val();
        var name = $('#name').val();
        var title = $('#title').val();
        var starttime = $('#starttime').val();
        var endtime = $('#endtime').val();
        table.reload('licenseTable', {
          where: { //设定异步数据接口的额外参数，任意设
        	licenceHolder: licenceHolder,
        	code: code,
        	type: type,
        	name: name,
        	title: title,
        	starttime: starttime,
        	endtime: endtime
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
    	$('#licenceHolder').val('');
    	$('#code').val('');
        $('#type').val('');
        $('#name').val('');
        $('#title').val('');
        $('#starttime').val('');
        $('#endtime').val('');
    }
    
    this.selectDepart = function() {
        var queryParams = {};
        generateModel('select_depart','add',"70%","", '按部门查询',queryParams, null);
    }
    
    //查看
    this.check_entity = function(id) {
        var queryParams = {
            type: 'check'
        };
        generateModel('license_info','edit', "70%","", '上岗证信息查看',queryParams, null, '../license/getLicense?id='+id);
    }
    
  //删除
	this.del_entity = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../license/del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('licenseTable');	
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
                name : "submitLicenseForm",
                arg : ["save"]
            }
        };
        generateModel('license_staff','add', "70%", "", '上岗证信息登记',queryParams, btnOptions);
    }
    
	//修改
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitLicenseForm",
                arg : ["save"]
            }
        };
        console.log(id);
        
        generateModel('license_info','edit', "70%", "", '上岗证信息修改',queryParams, btnOptions, '../license/getLicense?id='+id);
    }
    
    return this;
};
var license = window.app.license();
