window.app = new Object();
window.tmplconfig = {};
window.appConfig = {};
window.header = $("#header");
window.footer = $("#footer");
window.work_area = $("#work_area");
window.top_navbar = $("#top_navbar");
window.menu_sidebar = $("#menu_sidebar");
window.add_modal = $("#add_modal");
window.edit_modal = $("#edit_modal");

$(document).ready(function(){
	$.when(loadConfig_app())
	.done(function() {
		$.route()
	});
	$(window).bind('hashchange',function(){
		$.route();
	});
	overTimeQuitLogin();	
//	响应后退事件
//	var url = window.location.href;
//	if(url.indexOf("#")!=-1){
//		url = url.substring(0,url.indexOf("#"));
//		window.history.pushState({},0,url);
//	}
	
	$.ajaxSetup({ cache: false }); //IE对AJAX地址进行缓存
});

//前端路由导航按顺序加载页面模块
function loadConfig_app(){	
	var deferred = $.Deferred();
	$.get({url:"../js/tmplconfig.json",async:false},function(result,status){
		window.tmplconfig = result;
		$.get({url:"../js/config.json",async:false},function(result1,status1){
			window.appConfig = result1;
			
			//先读取侧边栏，以防刷新时，主菜单刷新后点击菜单时侧边栏未加载出现问题
			var sidebar = window.appConfig[1];
			if(sidebar){
				jQuery.getScript(sidebar.path, function() {
					var navbar = window.appConfig[0];
					if(navbar){
						jQuery.getScript(navbar.path,function(){
							deferred.resolve();
						});
					}
				});
			}
			
			/*var navbar = window.appConfig[0];
			if(navbar){
				jQuery.getScript(navbar.path);
			}
			var sidebar = window.appConfig[1];
			if(sidebar){
				jQuery.getScript(sidebar.path);
			}
			
			/*var systemControl = window.appConfig[2];
			if(systemControl){
				jQuery.getScript(systemControl.path);
			}*/
		});
	});
	return deferred.promise();
}

//全局layui主动效果element重渲染
window.LAYUI_ELEMENT = function(){
	layui.use('element',function(){
		var element = layui.element;
		element.render();
	});
}
//全局layui的form表单重渲染
window.LAYUI_FORM = function(){
	layui.use('form',function(){
		var form = layui.form;		
		form.render();	
	})
};

//全局layui弹出提示
window.MSG = function(str){
	layui.use('layer',function(){
		var layer = layui.layer;
		layer.msg(str);
	})
};

//初始化单选或多选框选中，如window.CHECKEDINPUTLIST($("#sexRadio input"),"male")
window.CHECKEDINPUTLIST = function(inputList,checkedValue){
	if(checkedValue){
		for(var i =0;i<inputList.length;i++){
			if(checkedValue.indexOf($(inputList[i]).val())!=-1){
				$(inputList[i]).prop("checked",true);
			}else{
				$(inputList[i]).prop("checked",false);
			}
		}
	}
}

//获得多选框值，逗号分割
window.GETCHECKINCHECKBOX = function(inputList){
	var checkIds="";
	for(var i =0;i<inputList.length;i++){
		if(inputList[i].checked){
			checkIds +=","+inputList[i].value;
		}
	}
	return checkIds.substring(1);
}

//得到地址栏中参数,如window.GETQUERYPARAM("id","main.html#/userInfo?id=1")
window.GETQUERYPARAM = function(name,hash) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	if(!hash){
		hash = window.location.hash;
	}
	var index = hash.indexOf("?");
	var hash = hash.substr(index + 1);
	var r = hash.match(reg);
	if (r != null) {
		return unescape(decodeURI(r[2]));
	}
	return null;
}

//10min无动作退出登陆
function overTimeQuitLogin(){
	var overTime = 600;	//10min
	var loginTime = overTime;
	$(document).on('keydown mousemove mousedown',function(e){
		loginTime = overTime;	//重置计数；
	});
	//倒计时1s/次
	window.setInterval(function(){
		loginTime--;
		if(loginTime <= 0){
			MSG("登陆超时，请重新登陆！");
			setTimeout(function(){
                logout();
			},800);	
		}
	},1000);
}

//自动填充表单数据
$.fn.autofill = function(data) {
	var settings = {
			findbyname: true,
			restrict: true
		},
		self = this;
	return this.each(function() {
		$.each( data, function(k, v) {
			var selector, elt;
			 if(v && typeof(v) == "object"){
				 var json = v;
				 for(var  key in json){
		            json[k+"."+key] = json[key];   
		            delete json[key]; 
		         }
				 self.autofill(json);
			 }else{
				 if ( settings.findbyname ) { // by name
						selector = '[name="'+k+'"]';
						elt = ( settings.restrict ) ? self.find( selector ) : $( selector );
						if ( elt.length == 1 ) {
							elt.val( ( elt.attr("type") == "checkbox" ) ? [v] : v );
						} else if ( elt.length > 1 ) {
							if(elt.attr("type") == "checkbox"){
								if(v){
									elt.val(v.split(','));
								}
							}else{
								elt.val([v]);
							}
						} else {
							selector = '[name="'+k+'[]"]';
							elt = ( settings.restrict ) ? self.find( selector ) : $( selector );
							elt.each(function(){
								$(this).val(v);
							});
						}

					} else { // by id

						selector = '#'+k;
						elt = ( settings.restrict ) ? self.find( selector ) : $( selector );

						if ( elt.length == 1 ) {
							elt.val( ( elt.attr("type") == "checkbox" ) ? [v] : v );
						} else {
							var radiofound = false;

							// radio
							elt = ( settings.restrict ) ? self.find( 'input:radio[name="'+k+'"]' ) : $( 'input:radio[name="'+k+'"]' );
							elt.each(function(){
								radiofound = true;
								if ( this.value == v ) { this.checked = true; }
							});
							// multi checkbox
							if ( !radiofound ) {
								elt = ( settings.restrict ) ? self.find( 'input:checkbox[name="'+k+'[]"]' ) : $( 'input:checkbox[name="'+k+'[]"]' );
								elt.each(function(){
									$(this).val(v);
								});
							}
						}
					}
			 }
		});
	});
};

//时间戳转时间
var toDateString = function(timestamp){
	if(!timestamp){
		return "-"
	}
	var time = new Date(timestamp );  
	var y = time.getFullYear();//年  
	var m = time.getMonth() + 1;//月  
	var d = time.getDate();//日  
	var h = time.getHours();//时  
	var mm = time.getMinutes();//分  
	var s = time.getSeconds();//秒  
	return (y + "-" + m + "-" + d + " " + h + ":" + mm + ":" + s)  
	return date;
}
function showMoreCondition(name){
    if($('.query_conditionss_show').is(':hidden')){
        $('.query_conditionss_show').show();
        var heightShow = 74 + $('.query_conditions').height();
        $('.data_list').css('height', "calc(100% - "+ heightShow +"px)");
        table.reload(name, {
            height: $('.data_list').height()
        });
    } else {
        $('.query_conditionss_show').hide();
        $('.data_list').css('height', "calc(100% - 146px");
        table.reload(name, {
            height: $('.data_list').height()
        });
    }
}
var winIndexMap = {};
function selectSystemObj(objName, name, width, height, isMulti, queryParams, backCallFunc) {
    if (!queryParams) {
        queryParams = {};
    }
    queryParams["isMulti"] = isMulti ? true : false;
    if (backCallFunc) {
        // queryParams["backCallFunc"] = backCallFunc.name;
        queryParams["backCallFunc"] = backCallFunc.toString().match(/^function\s*([^\s(]+)/)[1];

    }
    var winId = genNonDuplicateID(10);
    queryParams["winId"] = winId;
    layer.load(1, {
        shade: [0.2, '#fff'], 
        content: '加载中...', 
        success: function (layero) {
            layero.find('.layui-layer-content').css({
                'padding-top': '39px',
                'width': '60px'
            });
        }
    });

    if (isMulti) {
//        $.get(url, queryParams, function(str) {
            layer.closeAll('loading'); //关闭加载层
            function getHtml(){
                var html = $.templates[name].render();
                var idName = getRandomString();
                var modelHtml = "<div id=" + idName + ">" + html + "</div>";
//                openModel(modelHtml, width, height,queryParams,btnOptions);
                layer.open({
                    type: 1,
                    title: '选择' + objName,
                    btn: ['确定', '返回'],
                    yes: function(index, layero) {
                        if ($("#selectTable").length>0) {
                            //列表
                            var value = layui.table.checkStatus("selectTable");
                            var data  = value.data;
                            if (data.length >= 1) {
                                if (backCallFunc && typeof backCallFunc == 'function') {
                                    backCallFunc(data);
                                }
                                layer.close(index);
                            } else {
                                layer.open({title: '提示', content: '请选择' + objName});
                            }
                            //iframetreegrid
                        } else if ($("#selectedJcxm").length>0) {
                             jcxmTableIframe.window.getSelectedJcxmFunc();
                            var value = $("#selectedJcxm").val();
                            if (value && value != '') {
                                if (backCallFunc && typeof backCallFunc == 'function') {
                                    backCallFunc(value);
                                }
                                layer.close(index);
                            } else {
                                layer.open({title: '提示', content: '请选择' + objName});
                            }
                        } else if ($("#selectUserTable").length>0) {
                            //列表
                            var value = layui.table.checkStatus("selectUserTable");
                            var data  = value.data;
                            if (data.length >= 1) {
                                if (backCallFunc && typeof backCallFunc == 'function') {
                                    backCallFunc(data);
                                }
                                layer.close(index);
                            } else {
                                layer.open({title: '提示', content: '请选择' + objName});
                            }
                            //iframetreegrid
                        }

                    },

                    border: [1],
                    area: [width, height],
                    content: modelHtml, //注意，如果str是object，那么需要字符拼接。
                    success: function(layero, index){
                        winIndexMap[winId] = index;
                    }
                });
                for (var i = 0; i < window.appConfig.length; i++) {
                    var home = window.appConfig[i];
                    if (home.name == name) {
                        jQuery.getScript(home.path);
                        break;
                    }
                }
            }
            $.when($.lazyLoadTemplate(name)).done(function() {
                getHtml();
            });
            
//        }).error(function(xhr,errorText,errorType){
//            layer.closeAll('loading'); //关闭加载层
//        });
    } else {
//        $.get(url, queryParams, function(str) {
        layer.closeAll('loading'); //关闭加载层
        function getHtml(){
            var html = $.templates[name].render();
            var idName = getRandomString();
            var modelHtml = "<div id=" + idName + ">" + html + "</div>";
            layer.open({
                type: 1,
                title: '选择' + objName,
                border: [1],
                area: [width, height],
                content: modelHtml, //注意，如果str是object，那么需要字符拼接。
                success: function(layero, index){
                    winIndexMap[winId] = index;
                }
            });
            for (var i = 0; i < window.appConfig.length; i++) {
                var home = window.appConfig[i];
                if (home.name == name) {
                    jQuery.getScript(home.path);
                    break;
                }
            }
        }
        $.when($.lazyLoadTemplate(name)).done(function() {
            getHtml();
        });
//        }).error(function(xhr,errorText,errorType){
//            layer.closeAll('loading'); //关闭加载层
//        });
    }
}
var idNameTotal = '';
var dataTotal = '';
function generateModel(name,type, width, height,title,queryParams,btnOptions,url,b,close){
    if(type == "add"){
        function getHtml(){
            var html = $.templates[name].render();
            var idName = getRandomString();
            var modelHtml = "<div id=" + idName + " style='height:100%;'>" + html + "</div>";
            openModel(modelHtml, width, height,title,queryParams,btnOptions,b,close);
            for (var i = 0; i < window.appConfig.length; i++) {
                var home = window.appConfig[i];
                if (home.name == name) {
                    jQuery.getScript(home.path);
                    break;
                }
            }
        }
        $.when($.lazyLoadTemplate(name)).done(function() {
            getHtml();
        });
    } else {
        $.ajax({
            url:url, 
            type:"GET",
            data: queryParams,
            dataType:"json",
            success:function(data){
                function getHtml(data){
                    var html = $.templates[name].render();
                    var idName = getRandomString();
                    var modelHtml = "<div id=" + idName + " style='height:100%;'>" + html + "</div>";
                    openModel(modelHtml, width, height,title,queryParams,btnOptions,b,close);
                    for (var i = 0; i < window.appConfig.length; i++) {
                        var home = window.appConfig[i];
                        if (home.name == name) {
                            jQuery.getScript(home.path);
                            break;
                        }
                    }
                    idNameTotal = idName;
                    dataTotal = data;
                    $("#" + idName + "").autofill(data);
                  window.LAYUI_FORM();
                }
                $.when($.lazyLoadTemplate(name)).done(function() {
                    getHtml(data);
                });
            }
        });
    }
}
function openModel(containerName, width, height,title,queryParams,btnOptions,b,close){
    var btnNames = [], btnIndex = 0;
    var btnFuncs = [];
    if (btnOptions) {
        if (btnOptions.sure) {
            btnNames[btnIndex] = btnOptions.sure.btnName || "确定";
            var argStr = '';
            if (btnOptions.sure.arg) {
                for (i = 0; i < btnOptions.sure.arg.length; i++) {
                    argStr += ',\'' + btnOptions.sure.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.sure.name + '(index' + argStr + ')';				
        }
        if (btnOptions.save) {
            console.log("%%%");
            btnNames[btnIndex] = btnOptions.save.btnName || "保存";
            var argStr = '';
            if (btnOptions.save.arg) {
                for (i = 0; i < btnOptions.save.arg.length; i++) {
                    argStr += ',\'' + btnOptions.save.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.save.name + '(index' + argStr + ')';				
        }
        if (btnOptions.submit) {
            btnNames[btnIndex] = btnOptions.submit.btnName || "提交";
            var argStr = '';
            if (btnOptions.submit.arg) {
                for (i = 0; i < btnOptions.submit.arg.length; i++) {
                    argStr += ',\'' + btnOptions.submit.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.submit.name + '(index' + argStr + ')';		
        }
        if (btnOptions.deliver) {
            btnNames[btnIndex] = btnOptions.deliver.btnName || "下达";
            var argStr = '';
            if (btnOptions.deliver.arg) {
                for (i = 0; i < btnOptions.deliver.arg.length; i++) {
                    argStr += ',\'' + btnOptions.deliver.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.deliver.name + '(index' + argStr + ')';
        }
        if (btnOptions.accept) {
            btnNames[btnIndex] = btnOptions.accept.btnName || "通过";
            var argStr = '';
            if (btnOptions.accept.arg) {
                for (i = 0; i < btnOptions.accept.arg.length; i++) {
                    argStr += ',\'' + btnOptions.accept.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.accept.name + '(index' + argStr + ')';
        }
        if (btnOptions.reject) {
            btnNames[btnIndex] = btnOptions.reject.btnName || "不通过";
            var argStr = '';
            if (btnOptions.reject.arg) {
                for (i = 0; i < btnOptions.reject.arg.length; i++) {
                    argStr += ',\'' + btnOptions.reject.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.reject.name + '(index' + argStr + ')';
        }
        if (btnOptions.rejectOneStep) {
            btnNames[btnIndex] = btnOptions.rejectOneStep.btnName || "退回上一阶段";
            var argStr = '';
            if (btnOptions.rejectOneStep.arg) {
                for (i = 0; i < btnOptions.rejectOneStep.arg.length; i++) {
                    argStr += ',\'' + btnOptions.rejectOneStep.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.rejectOneStep.name + '(index' + argStr + ')';
        }
        if (btnOptions.agree) {
            btnNames[btnIndex] = btnOptions.agree.btnName || "同意";
            var argStr = '';
            if (btnOptions.agree.arg) {
                for (i = 0; i < btnOptions.agree.arg.length; i++) {
                    argStr += ',\'' + btnOptions.agree.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.agree.name + '(index' + argStr + ')';
        }
        if (btnOptions.back) {
            btnNames[btnIndex] = btnOptions.back.btnName || "不通过";
            var argStr = '';
            if (btnOptions.back.arg) {
                for (i = 0; i < btnOptions.back.arg.length; i++) {
                    argStr += ',\'' + btnOptions.back.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.back.name + '(index' + argStr + ')';
        }
        if (btnOptions.moveIn) {
            btnNames[btnIndex] = btnOptions.moveIn.btnName || "移入";
            var argStr = '';
            if (btnOptions.moveIn.arg) {
                for (i = 0; i < btnOptions.moveIn.arg.length; i++) {
                    argStr += ',\'' + btnOptions.moveIn.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.moveIn.name + '(index' + argStr + ')';
        }
        if (btnOptions.moveOut) {
            btnNames[btnIndex] = btnOptions.moveOut.btnName || "移出";
            var argStr = '';
            if (btnOptions.moveOut.arg) {
                for (i = 0; i < btnOptions.moveOut.arg.length; i++) {
                    argStr += ',\'' + btnOptions.moveOut.arg[i] + '\'';
                }
            }
            btnFuncs[btnIndex++] = btnOptions.moveOut.name + '(index' + argStr + ')';
        }

        if(b == null){
            btnNames[btnIndex] = "取消";
        }

        btnFuncs[btnIndex++] = 'closeWin(index)';
    } else {
        btnNames[btnIndex] = "返回";
        btnFuncs[btnIndex++] = 'closeWin(index)';
    }
    var $=layui.$;
    layer.load(1, {
        shade: [0.2, '#fff'], 
        content: '加载中...', 
        success: function (layero) {
            layero.find('.layui-layer-content').css({
                'padding-top': '39px',
                'width': '60px'
            });
        }
    });
    layui.use(['form','layer'], function() {      
        var layer = layui.layer;
        var form = layui.form;
        var index = layer.open({
            type: 1,
            title: title,
            skin: 'modal_skin',
            area: [width, height], // 宽度
            btn: btnNames,
            closeBtn: close,
            btnAlign: 'c',
            content: containerName,
            yes: function(index, layero){
                if (btnIndex >= 1) {
                    return eval(btnFuncs[0]);
                }
            },
            btn2: function(index, layero){
                /*if (btnIndex >= 2) {
                    return eval(btnFuncs[1]);
                }*/
                if (btnIndex >= 2) {
                    eval(btnFuncs[1]);
                    return false;
                }
            },
            btn3: function(index, layero){
                /*if (btnIndex >= 3) {
                    return eval(btnFuncs[2]);
                }*/
                if (btnIndex >= 3) {
                    eval(btnFuncs[2])
                    return false;
                }
            }
        });
        form.render();
    });
    layer.closeAll('loading'); //关闭加载层
}
function callAutofill(){
    debugger;
    $("#" + idNameTotal + "").autofill(dataTotal);
}
function toReset(){
    
}
function loadDepartmentTree(name,url,nodeId,tableName,nodeName) {
    $.get(url, function(data){
        $("#"+name+"").empty();
        layui.use('tree', function(){
          layui.tree({
              elem:"#"+name+"",
              nodes:data,
              click: function(node){
                //console.log(node);
                if (nodeId) {
                    console.log($("#"+nodeId+""));
                    $("#"+nodeId+"").val(node.id);
                }
                if (nodeName) {
                    //console.log($("#"+nodeName+""));
                    $("#"+nodeName+"").val(node.name);
                }
                if(tableName){
                    table.reload(tableName, {where:{pid: node.id}});
                }
              },
          });
        });
    });   
}
function getRandomString() {
    len = 16;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}
function genNonDuplicateID(randomLength){
  return Number(Math.random().toString().substr(3,randomLength) + Date.now()).toString(36)
}
function calculate(ontology,parentName,xmName){
    for(var parentNum = 0; parentNum < parentName.length; parentNum++){
        for(var initial = 0; initial < fsjcData.length; initial++){
            if(fsjcData[initial].templeName == xmName){
                for(var coefficient = 0; coefficient < fsjcData[initial].templeParameters.length; coefficient++){
                    if(fsjcData[initial].templeParameters[coefficient].tableTbody){
                        for(i = 0; i < fsjcData[initial].templeParameters[coefficient].tableTbody.length; i++){
                            for(j = 0; j < fsjcData[initial].templeParameters[coefficient].tableTbody[i][i].length; j++){
                                var tableTbodyDate = fsjcData[initial].templeParameters[coefficient].tableTbody[i][i];
                                if(tableTbodyDate[j].name == parentName[parentNum]){
                                    var newEvent = eval(tableTbodyDate[j].event[0])(tableTbodyDate[j].event[1],tableTbodyDate[j].name);
                                }
                            }
                        }
                    }
                }
            }
        }
//        
        
    }
}
function generateSelect(templeDate){
    var html1 = "<select name='"+templeDate[j].name+"' id='"+templeDate[j].name+"' lay-filter='"+templeDate[j].name+"'>";
    if(templeDate[j].parent){
        var parentChildName = templeDate[j].parent.split(',');
        if(parentChildName.length == 1){
            layui.each(templeDate, function(index, item){
                if(templeDate[j].parent == item.name){
                    var parentDefault = item.default;
                    layui.each(templeDate[j].options, function(index1, item1){
                        if(item1.parentVal.includes(parentDefault) && parentDefault !="" && parentDefault != [] && parentDefault != undefined && parentDefault !=null ){
                            var childObj = item1;
                            if(childObj != [] && childObj != undefined && childObj != "" ){
                                layui.each(childObj.childVal, function(index2, item2){
                                    html1 += "<option value='"+item2+"' ";
                                    if(childObj.default == item2){
                                        html1 += " selected='selected'";
                                    }
                                    html1 += ">"+item2+"</option>";
                                })
                            }
                        }
                    })
                } else {
                    layui.each(templeDate[j].options, function(index1, item1){
                        if(item1.default != ""){
                            layui.each(item1.childVal, function(index2, item2){
                                html1 += "<option value='"+item2+"' ";
                                if(item1.default == item2){
                                    html1 += " selected='selected'";
                                }
                                html1 += ">"+item2+"</option>"
                            })
                        }
                    })
                }
            })
        } else {
            var childArr = [];
            var tt = [];
            var parentChildObj = [];
            layui.each(d, function(index, item){
                layui.each(parentChildName, function(index, item1){
                    if (item.name == item1){
                        tt = childArr.push(item.default);
                    }
                })
            })
            layui.each(templeDate[j].options, function(index, item){
                if(item.parentVal.sort().join() == childArr.sort().join()){
                    var parentChildObj = item;
                    if (parentChildObj !="" && parentChildObj != [] && parentChildObj != undefined && parentChildObj !=null){
                        layui.each(parentChildObj.childVal, function(index1, item1){
                            html1 += "<option value='"+item1+"' ";
                            if(parentChildObj.default == item1){
                                html1 += " selected='selected'";
                            }
                            html1 += ">"+item1+"</option>";
                        })
                    }
                } else {
                    if(item.default != ""){
                        layui.each(item.childVal, function(index2, item2){
                            html1 += "<option value='"+item2+"' ";
                            if(item.default == item2){
                                html1 += " selected='selected'";
                            }
                            html1 += ">"+item2+"</option>";
                        })
                    }
                }
            })
        }
    } else {
        layui.each(templeDate[j].options, function(index, item){
            html1 += "<option value='"+item+"' ";
            if(item == templeDate[j].default){
                html1 += " selected='selected'";
            }
            html1 += ">"+item+"</option>"
        })
    }
    html1 += "</select>";
    return html1;
}
// 层级联动
    function levelsLinkage(parent, child, arr, type,obj) {
        var pArr = parent.split(",");
        pArr.sort();
            for (var i = 0; i < pArr.length; i++) {
                form.on('select('+pArr[i]+')', function (parentData) {
                    var pvArr = pArr.map(function (data) {
                        return $("#"+data+"").val();
                    });
                    for(var j = 0; j < obj.length; j++){
                        if(obj[j].type == "select" ){
                            if(obj[j].parent){
                                var parentName = obj[j].parent.split(',');
                                if(parentName.length == 1){
                                    if(pArr.includes(obj[j].parent)){
                                          $.each(obj[j].options,function(i,n){
                                              if(n.parentVal.includes(parentData.value)){
                                                  var childHtml = n.childVal.map(function (data) {
                                                      var check = n.default == data ? 'selected=\"selected\"' : '';
                                                      if(data == ' '){
                                                          return " ";
                                                      } else {
                                                          return "<option value='" + data + "' " +check+ ">" + data + "</option>";
                                                      }
                                                  });
                                                  $("select[name="+obj[j].name+"]").html(childHtml.join(""));
                                              } 
                                          })
                                    }
                                    form.render();
                                } else {
                                    var parentName = obj[j].parent.split(',');
                                    for(var k = 0; k < parentName.length; k++){
                                        if(pArr.includes(parentName[k])){
                                            var pvArr = parentName.map(function (data) {
                                                return $("#"+data+"").val();
                                            });
                                            $.each(obj[j].options,function(i,n){
                                                if(n.parentVal.sort().join() == pvArr.sort().join()){
                                                      var childHtml = n.childVal.map(function (data) {
                                                      var check = n.default == data ? 'selected=\"selected\"' : '';
                                                      if(data == ' '){
                                                          return " ";
                                                      } else {
                                                          return "<option value='" + data + "' " +check+ ">" + data + "</option>";
                                                      }
                                                  });
                                                  $("select[name="+obj[j].name+"]").html(childHtml.join(""));
                                                }
                                            })
                                        }
                                        form.render();
                                        break;
                                    } 
                                }
                            }
                        }
                        if(obj[j].type == "text" ){
                            if(obj[j].parent){
                                var parentName = obj[j].parent.split(',');
                                if(parentName.length == 1){
                                    if(pArr.includes(obj[j].parent)){
                                          $.each(obj[j].textValue,function(i,n){
                                              if(n.parentVal.includes(parentData.value)){
                                                  $("input[name="+obj[j].name+"]").val(n.childVal);
                                              } 
                                          })
                                    }
                                    form.render();
                                } else {
                                    var parentName = obj[j].parent.split(',');
                                    for(var k = 0; k < parentName.length; k++){
                                        if(pArr.includes(parentName[k])){
                                            var pvArr = parentName.map(function (data) {
                                                return $("#"+data+"").val();
                                            });
                                            $.each(obj[j].options,function(i,n){
                                                if(n.parentVal.sort().join() == pvArr.sort().join()){
                                                    $("input[name="+obj[j].name+"]").val(n.childVal);
                                                }
                                            })
                                        }
                                        form.render();
                                        break;
                                    } 
                                }
                            }
                        }
                    }
                });
            }
    }
function kyEvent(inputValue,outputName){
    var inputValues = inputValue.split(',');
    var a = parseFloat($("#"+inputValues[0]+"").val() == "" ? '0' : $("#"+inputValues[0]+"").val());
    var b = parseFloat($("#"+inputValues[1]+"").val() == "" ? '0' : $("#"+inputValues[1]+"").val());
    var c = parseFloat($("#"+inputValues[2]+"").val() == "" ? '0' : $("#"+inputValues[2]+"").val());
    var d = parseFloat($("#"+inputValues[3]+"").val() == "" ? '0' : $("#"+inputValues[3]+"").val());
    var outputValue = isNaN((c*1000)/(a*b)*d) ? '' : (c*1000)/(a*b)*d;
    outputValue = isFinite(outputValue) ? outputValue : '';
    if(outputValue != ''){
        console.log(outputValue);
        outputValue = evenRound(outputValue,1);
        $("#"+outputName+"").val(outputValue);
    } else {
        $("#"+outputName+"").val('');
    }
    
}
function endkyEvent(inputValue,outputName){
    var inputValues = inputValue.split(',');
    var a = parseFloat($("#"+inputValues[0]+"").val() == "" ? '0' : $("#"+inputValues[0]+"").val());
    var b = parseFloat($("#"+inputValues[1]+"").val() == "" ? '0' : $("#"+inputValues[1]+"").val());
    var c = parseFloat($("#"+inputValues[2]+"").val() == "" ? '0' : $("#"+inputValues[2]+"").val());
    if(a != 0 && b != 0 && c != 0 ){
        var nums = [a,b,c];
        nums = nums.sort(function(a,b){
        return a-b;
        });
        var endkyMax = Math.max.apply(null, nums);
        var endkyMid = nums[parseInt(nums.length/2)];
        var endkyMin = Math.min.apply(null, nums);
        if(Math.abs(endkyMax - endkyMid) > (0.15*endkyMid) && Math.abs(endkyMin - endkyMid) > (0.15*endkyMid)){
            $("#"+outputName+"").val('试验无效');
        } else if(Math.abs(endkyMax - endkyMid) > (0.15*endkyMid) || Math.abs(endkyMin - endkyMid) > (0.15*endkyMid)){
            $("#"+outputName+"").val(evenRound(endkyMid,1));
        } else if(Math.abs(endkyMax - endkyMid) <= (0.15*endkyMid) && Math.abs(endkyMin - endkyMid) <= (0.15*endkyMid)){
            $("#"+outputName+"").val(evenRound(((endkyMax+endkyMid+endkyMin)/3),1));
        }
    }
}
function endkpEvent(inputValue,outputName){
    var inputValues = inputValue.split(',');
    var a = parseFloat($("#"+inputValues[0]+"").val() == "" ? '0' : $("#"+inputValues[0]+"").val());
    if(a != 0){
        var outputValue = evenRound((a/30)*100,1);
        $("#"+outputName+"").val(outputValue);
    }
}
function closeWin(index) {
    layer.close(index);
}
function logout() {
   $.ajax({
        type : "POST",
        url : "../admin/logout",
        data :  {}, 
        dataType : "json",
        success: function (data) {
            window.location.href="../index.html"; 
        },
        error: function (msg) {
            window.location.href="../index.html"; 
        }

    });
}



function evenRound(num, decimalPlaces) {
    var d = decimalPlaces || 0;
    var m = Math.pow(10, d);
    var n = +(d ? num * m : num).toFixed(8); // Avoid rounding errors
    var i = Math.floor(n), f = n - i;
    var e = 1e-8; // Allow for rounding errors in f
    var r = (f > 0.5 - e && f < 0.5 + e) ? ((i % 2 == 0) ? i : i + 1) : Math.round(n);
    if(d){
        console.log("!!!");
        console.log(r / m);
        var y = String(r / m).indexOf(".") + 1;//获取小数点的位置
        var count = String(r / m).length - y;//获取小数点后的个数
        console.log(count);
        console.log(d);
        console.log(count < d);
        if(y > 0) {
            if(count < d){
                return (r / m).toFixed(d);
            } else {
                return r / m;
            }
        } else {
            return (r / m).toFixed(d);
        }
    } else {
        console.log("@@@");
        return r;
    }
//    return d ? r / m : r;
}

function stopRefresh(){
    $('.searchText').on("keydown", function (event) {
    if (event.keyCode == 13) {
        return false
    }
    })
}
function treeActive(){
    $("body").on("mousedown",".layui-tree a",function(){
        if(!$(this).siblings('ul').length){
            $(".layui-tree a cite").css({'color':'#333','background-color':'transparent'});
            $(this).find('cite').css({'color':'#fff','background-color': '#4476A7'});
        }
    });
}

function updateCache(tableId) {
    var gcJson = table.cache[tableId];
    var datas = [];    //如果table删除了数据，拿到的table数据长度不会变，被删除的数据为长度为0的Array
    if (gcJson && gcJson.length > 0) {
        for (var i=0; i<gcJson.length; i++) {
            if (!(gcJson[i] instanceof Array)) {
                datas.push(gcJson[i]);  
            }
        }
    }
    table.reload(tableId,{  
      data : datas  
    });

}