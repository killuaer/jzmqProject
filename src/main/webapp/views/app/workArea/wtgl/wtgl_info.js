var index;
var operateMode;
var form;

var table;
window.app.wtgl_info = function() {
	function init() {
		jcxmGrouTable();
	}
	this.backToList = function() {
		location.hash = "/wtgl_info";
	}
	$.when($.lazyLoadTemplate("wtgl_info")).done(function() {
		init();
	});
    
    function jcxmGrouTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#jcxmGrouTable',
                height: $('.data_list').height(),
                url: '../wtgl/listJcxmGrou',
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
                        { unresize: true,field: 'jddy',width:'15%',title: '鉴定单元',align: 'center'},
                        { unresize: true,field: 'mqlx', width: '15%', title: '幕墙类型',align: 'center' },
                        { unresize: true,field: 'jdnum', width: '15%', title: '位置数量' ,align: 'center'},
                        { unresize: true,field: 'mqType', width: '15%', title: '鉴定类型' ,align: 'center' },
                        { unresize: true,field: 'jdzdy', width: '15%', title: '子单元' ,align: 'center' }
                        ,{ unresize: true,field: 'cuozuo',  width: '18%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=wtgl_info.check_entity(\''+id+'\')>查看</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=wtgl_info.edit_entity(\''+id+'\')>修改</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=wtgl_info.del_entity(\''+id+'\')>删除</a>';
        return row;
    }
    
    this.add_jcxmgrou = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitjcxmGrouForm",
                arg : ["save"]
            }
        };
        generateModel('wtgl_jcxmgrou','add', "70%", "50%","项目信息",queryParams, btnOptions);
    }
    
    this.queryList  = function() {
        var name = $('#name').val();
        var loginName = $('#loginName').val();
        var departId = $('#departId').val();
        table.reload('jcxmGrouTable', {
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

    this.selectDepart = function() {
        var queryParams = {};
        generateModel('select_project','add', "70%", "85%", "工程信息", queryParams, null);
    }      
    
    return this;
};
var wtgl_info = window.app.wtgl_info();

layui.use(['form'], function() {
    form = layui.form;
    form.on('submit(wtgl_infoFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../wtgl/jcXmCoreSave", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('jcxmCoreTable')
                   }else{
                       MSG("异常 "+data.msg)
                   }
                   layer.closeAll('loading');
               },
               error:function(){
                   MSG("异常");
                   layer.closeAll('loading');
               }       
        });
        return false;
    });
});

//日期控件
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
		elem: '#syDate'
	});
	laydate.render({
        elem: '#sjDate'
    });
	laydate.render({
        elem: '#jgDate'
    });
	laydate.render({
        elem: '#qdDate'
    });
    $('.dateType').each(function(){
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd'
        });
    });
});

function submitwtglForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#wtgl_infoBtn").click();
}