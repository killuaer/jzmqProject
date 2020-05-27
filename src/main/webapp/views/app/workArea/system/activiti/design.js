var table;
window.app.design = function() {
	function init() {
		var html = $.templates.design.render();
		window.work_area.empty();
		window.work_area.html(html);
        designTable();
	}
	this.backToList = function() {
		location.hash = "/design";
	}
	$.when($.lazyLoadTemplate("design")).done(function() {
		init();
	});
    
    function designTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#designTable',
                height: $('.data_list').height(),
                url: '../act/getModelList',
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
                        { unresize: true,field: 'name',width:'15%',title: '流程名称'},
                        { unresize: true,field: 'key',width:'15%',title: '流程唯一标识'},
                        { unresize: true,field: 'metaInfo',width:'25%',title: '流程详细'},
                        { unresize: true,field: 'deploymentId',width:'10%',title: '部署id'},
                        { unresize: true,field: 'createTime',width:'15%',title: '创建时间'},
                        { unresize: true,field: 'cuozuo',  width: '10%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
     var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=design.check_design(\''+id+'\')>查看流程</a>';
        return row;
    }
    
    this.queryList  = function() {
        var gcName = $('#gcName_project').val();
        var gcJianduId = $('#gcJianduId_project').val();
        table.reload('designTable', {
          where: { //设定异步数据接口的额外参数，任意设
            gcName: gcName,
            gcJianduId: gcJianduId
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#gcName_project').val('');
        $('#gcJianduId_project').val('');
    }
    
    this.add_design = function() {
        console.log("13");
        
        var name = $("#name").val();
        if(name == ""){
            MSG("请填写流程名称");
            $("#name").focus();
           return;
        }
        var key = $("#key").val();
        if(key == ""){
           MSG("请填写流程标识");
            $("#key").focus();
           return;
        }
        
        $.ajax({
            url:"../model/create?name=" + name + '&key=' + key, 
            type:"get",
            success:function(data){
               if(data.status == true){
                   $("#modelId").val(data.modelId);
                   var queryParams = {};
                   var btnOptions = {};
                   generateModel('design_info','add', "100%", "100%",'流程设计',queryParams, btnOptions);
                  //document.getElementById('content').src = '../views/app/workArea/system/activiti/modeler.html?modelId='+data.modelId;
               }else{
                   MSG(data.msg)
               }
            },
            error:function(){
               MSG("异常");
            }       
        });
       
    }
    
    this.check_design = function(id) {
        var queryParams = {};
        var btnOptions = {};
       $("#modelId").val(id);
       generateModel('design_info','add', "100%", "100%",'流程设计',queryParams, btnOptions);
    }
    
    return this;
};
var design = window.app.design();