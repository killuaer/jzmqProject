var table;
var form;
window.app.systemSetting = function() {
	function init() {
		var html = $.templates.systemSetting.render();
		window.work_area.empty();
		window.work_area.html(html);
        initForm();
        loadSystemSettingTree();
        clickSystemSetting({name: "委托管理", type: "wt", spread: true});
        treeActive();
	}
	this.backToList = function() {
		location.hash = "/systemSetting";
	}
	$.when($.lazyLoadTemplate("systemSetting")).done(function() {
		init();
	});
    
    function initForm() {
        layui.use(['form'], function() {
            form = layui.form;
            form.on('submit(submitFilter1)',function(data){
                var quaryCode_num = $('#quaryCode_num').val();
                if (quaryCode_num != null && quaryCode_num != '' && parseFloat(quaryCode_num).toString() == "NaN") {
                    layer.open({title: '提示',content: '报告查询码生成格式位数只能为数字类型!'});
                    return false;
                }
				var data = data.field;
                if (data.quaryCode == 'quaryCode_isNumber') {
                    data['quaryCode_isNumber'] = 'y';
                    data['quaryCode_isLetter'] = 'n';
                    data['quaryCode_isMix'] = 'n';
                } else if (data.quaryCode == 'quaryCode_isLetter') {
                    data['quaryCode_isNumber'] = 'n';
                    data['quaryCode_isLetter'] = 'y';
                    data['quaryCode_isMix'] = 'n';
                } else {
                    data['quaryCode_isNumber'] = 'n';
                    data['quaryCode_isLetter'] = 'n';
                    data['quaryCode_isMix'] = 'y';
                }
                delete data.quaryCode;
                layer.load(1);
				$.ajax({
					   url:"../systemSetting/save", 
					   type:"post",
					   dataType:"json",
					   data:data,
					   success:function(data){
						   if(data.status=='success'){
							   MSG("保存成功");
						   }else{
							   MSG("异常 "+data)
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
            form.on('submit(submitFilter2)',function(data){
                var finance_charge_phase_up = $('#finance_charge_phase_up').val();
                if (finance_charge_phase_up != null && finance_charge_phase_up != '' && parseFloat(finance_charge_phase_up).toString() == "NaN") {
                    layer.open({title: '提示',content: '上限只能为数字类型!'});
                    return false;
                }
                var finance_charge_phase_down = $('#finance_charge_phase_down').val();
                if (finance_charge_phase_down != null && finance_charge_phase_down != '' && parseFloat(finance_charge_phase_down).toString() == "NaN") {
                    layer.open({title: '提示',content: '下限只能为数字类型!'});
                    return false;
                }
				var data = data.field;
				layer.load(1);
				$.ajax({
					   url:"../systemSetting/save", 
					   type:"post",
					   dataType:"json",
					   data:data,
					   success:function(data){
						   if(data.status=='success'){
							   MSG("保存成功");
						   }else{
							   MSG("异常 "+data)
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
            form.render();
        });
    }
    
//    var treeData = [
//        {"name":"系统运行参数设置","spread":true, "children":  [
//            	{"name":"委托管理","type":"wt","spread":true},
//                {"name":"检测管理","type":"jc","spread":true},
//                {"name":"报告管理","type":"bg","spread":true},
//                {"name":"财务管理","type":"cw","spread":true}
//            ]
//        }
//    ];
    
    function loadSystemSettingTree() {
        var treeData = [
            {"name":"系统运行参数设置","spread":true, "children":  [
                    {"name":"委托管理","type":"wt","spread":true},
                    {"name":"检测管理","type":"jc","spread":true},
                    {"name":"报告管理","type":"bg","spread":true},
                    {"name":"财务管理","type":"cw","spread":true}
                ]
            }
        ];
        layui.use('tree', function(){
            layui.tree({
                elem:"#tree_systemSetting",
                nodes:treeData,
                click: clickSystemSetting,
            });
        });
    }
    
    function clickSystemSetting(item) {
        //赋值
        $.ajax({
           url:"../systemSetting/getJcXtszSystemSetting", 
           type:"get",
           dataType:"json",
           success:function(data){
               if (item.type == 'wt') {
                   $("#task_bgpz_style option[value='"+data.task_bgpz_style+"']").attr("selected","selected");
                   $("#is_different_sg_rebulid_sn option[value='"+data.is_different_sg_rebulid_sn+"']").attr("selected","selected");
                   $("#is_filter_xmId_byContract option[value='"+data.is_filter_xmId_byContract+"']").attr("selected","selected");
                   $("#is_source_prtnum_open option[value='"+data.is_source_prtnum_open+"']").attr("selected","selected");
                   $("#is_source_ph_num_open option[value='"+data.is_source_ph_num_open+"']").attr("selected","selected");
                   $("#fileter_gc_by_station option[value='"+data.fileter_gc_by_station+"']").attr("selected","selected");
                   $("#wt_discard_apply_only_oneself option[value='"+data.wt_discard_apply_only_oneself+"']").attr("selected","selected");
                   $('#quaryCode_num').val(data.quaryCode_num);
                   if (data.quaryCode_isNumber && data.quaryCode_isNumber == 'y') {
                        $('input[value="quaryCode_isNumber"]').prop("checked",true);
                   }
                   if (data.quaryCode_isLetter && data.quaryCode_isLetter == 'y') {
                        $('input[value="quaryCode_isLetter"]').prop("checked",true);
                   }
                   if (data.quaryCode_isMix && data.quaryCode_isMix == 'y') {
                       $('input[value="quaryCode_isMix"]').prop("checked",true);
                   }
                   if (data.is_show_commission_wtcol && data.is_show_commission_wtcol == 'y') {
                        $('input[name="is_show_commission_wtcol"]').prop("checked",true);
                   }
                   if (data.is_show_commission_wt && data.is_show_commission_wt == 'y') {
                        $('input[name="is_show_commission_wt"]').prop("checked",true);
                   }
                   if (data.is_show_commission_task && data.is_show_commission_task == 'y') {
                        $('input[name="is_show_commission_task"]').prop("checked",true);
                   }
                   if (data.is_show_commission_biaoqian && data.is_show_commission_biaoqian == 'y') {
                        $('input[name="is_show_commission_biaoqian"]').prop("checked",true);
                   }
                   if (data.is_show_commission_bgpz && data.is_show_commission_bgpz == 'y') {
                        $('input[name="is_show_commission_bgpz"]').prop("checked",true);
                   }
                   if (data.is_show_commission_original && data.is_show_commission_original == 'y') {
                        $('input[name="is_show_commission_original"]').prop("checked",true);
                   }
                   if (data.is_show_commission_liuyang && data.is_show_commission_liuyang == 'y') {
                        $('input[name="is_show_commission_liuyang"]').prop("checked",true);
                   }
                   if (data.is_show_commission_jianding && data.is_show_commission_jianding == 'y') {
                        $('input[name="is_show_commission_jianding"]').prop("checked",true);
                   }
                   if (data.is_show_commission_wtcol && data.is_show_commission_wtcol == 'y') {
                        $('input[name="is_show_commission_wtcol"]').prop("checked",true);
                   }
                    $("#rightDiv4").hide();
                    $("#rightDiv3").hide();
                    $("#rightDiv2").hide();
                    $("#rightDiv1").show();
               } else if (item.type == 'jc') {
                   $("#is_report_scan option[value='"+data.is_report_scan+"']").attr("selected","selected");
                   $("#is_original_signature_show option[value='"+data.is_original_signature_show+"']").attr("selected","selected");
                   $("#is_show_pd_std_ex option[value='"+data.is_show_pd_std_ex+"']").attr("selected","selected");
                   $("#is_show_pd_std_sy option[value='"+data.is_show_pd_std_sy+"']").attr("selected","selected");
                    $("#rightDiv4").hide();
                    $("#rightDiv3").hide();
                    $("#rightDiv1").hide();
                    $("#rightDiv2").show();
                } else if (item.type == 'bg') {
                    $('#unit_zz').val(data.unit_zz);
                    $('#ma_code').val(data.ma_code);
                    $('#tc_id').val(data.tc_id);
                    $('#genernal_prtnum_phase').val(data.genernal_prtnum_phase);
                    $('#report_date_source_set').val(data.report_date_source_set);
                    $('#report_success_phase').val(data.report_success_phase);
                    $('#report_declare1').val(data.report_declare1);
                    $('#report_declare2').val(data.report_declare2);
                    $('#report_declare3').val(data.report_declare3);
                    $('#report_declare4').val(data.report_declare4);
                    $('#report_print_operate_sn').val(data.report_print_operate_sn);
                    $('#report_stamper_control').val(data.report_stamper_control);
                    $('#report_print_seal').val(data.report_print_seal);
                    $("#rightDiv4").hide();
                    $("#rightDiv2").hide();
                    $("#rightDiv1").hide();
                    $("#rightDiv3").show();
                } else if (item.type == 'cw') {
                    console.log(data.is_original_signature_show);
                    $('#finance_charge_phase_up').val(data.finance_charge_phase_up);
                    $('#finance_charge_phase_down').val(data.finance_charge_phase_down);
                    $('#is_original_signature_show2').val(data.is_original_signature_show);
                    $("#rightDiv3").hide();
                    $("#rightDiv2").hide();
                    $("#rightDiv1").hide();
                    $("#rightDiv4").show();
                } 
               form.render();
           },
           error:function(){
               MSG("异常");
           }       
        });
    }
	return this;
};
var systemSetting = window.app.systemSetting();