window.app.sidebar = function() {
	function init() {
		var html = $.templates.sidebar.render();
		window.menu_sidebar.html(html);		
		loadUsername();
	}

	$.when(
		$.lazyLoadTemplate("sidebar")
	).done(function() {
		init();
	});
}
window.app.sidebar();

//读取用户信息
function loadUsername() {
//	$.ajax({
//		type : "POST",
//		dataType : "json",
//		url : "../main/account/loginAccountInfo",
//		success : function(result) {
//			$("#userName").html(result.username);
//		},
//		error : function() {
//			MSG("异常");
//		}
//	});
}

//加载侧边栏
var showMenuOne = function(menuid,name) {
	$("#nowMenuId").html(menuid);
    $('.level1').html(name);
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "../user/getRestMenu",
		data: {"parentId": menuid},
		async:false,
		success: function(result) {
			// 显示一级菜单
			var menuOne =
				"<div class=\"layui-nav-item\">" +
				"<div id=\"draw_back\"><img src=\"../img/3color.png\" /><span id=\"menuOne\">" +
				result.name + "</span><i class=\"fa fa-bars\"></i></div>" +
				"</div>";
			$("#left_menu").html(menuOne);
			// 显示一级菜单的子菜单
			if(result.subMenu) {
				var subMenu = result.subMenu;
				for(x in subMenu) {
					var childHtml = "";
					if(subMenu[x].subMenu) {
						var sonsonMenu = subMenu[x].subMenu;
						childHtml = "<dl class=\"layui-nav-child\">";
						for(z in sonsonMenu) {
                            if(sonsonMenu[z].menuUrl == '' || sonsonMenu[z].menuUrl == null || sonsonMenu[z].menuUrl == undefined){
                               sonsonMenu[z].menuUrl = '';
                            }
							childHtml = childHtml + "<dd>" +"<a onclick=showBreadcrumb(\""+subMenu[x].name+","+sonsonMenu[z].name+"\") href=\"#/"+sonsonMenu[z].menuUrl+"\">" +sonsonMenu[z].name + "</a>" +"</dd>";
						}
						childHtml = childHtml + "</dl>";
					}
                    if(subMenu[x].menuUrl == '' || subMenu[x].menuUrl == null || subMenu[x].menuUrl == undefined){
                       subMenu[x].menuUrl = '';
                    }
                    if(subMenu[x].menuUrl != ''){
                       subMenu[x].menuUrl = '/'+subMenu[x].menuUrl;
                    }
					var html = "<div  class=\"layui-nav-item\">" +
					"<a id="+ subMenu[x].id +" onclick=showBreadcrumb(\""+subMenu[x].name+"\") href=\"#" + subMenu[x].menuUrl + "\"><i class=\"fa fa-copy\"></i>&nbsp;&nbsp; " +
					subMenu[x].name +"</a>" +childHtml +"</div>";
					$("#left_menu").append(html);		
				}
			}
		},
		error: function() {
			MSG("异常！");
		}
	});
    layui.use('element', function() {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块  
        element.init();
    })
	sidebarMenuSelect();	//监听菜单选中事件
	sidebarAnimation();		//菜单弹出缩进动效
	loadsidebarFirst();		//点击nav导航时默认加载第一个子单元
    
}

//左侧菜单缩进-弹出
var sidebarAnimation = function() {
	$("#draw_back").click(function() {
		$("#left_menu").css({
			"width": "0px",
			"transition": "width 0.5s"
		});
		$("#work_area").css({
			"margin-left": "30px"
		});

		$("#retract_menu").css({
			"width": "30px",
			"transition": "width 0.2s ease 0.5s"
		});
	});
	$("#retract_menu").click(function() {
		$("#retract_menu").css({
			"width": "0px",
			"transition": "width 0.2s"
		});
		$("#work_area").css({
			"margin-left": "220px"
		});
		$("#left_menu").css({
			"width": "220px",
			"transition": "width 0.5s ease 0.2s"
		});
	});
}

//菜单点击之后保持选中状态
function sidebarMenuSelect() {
	$('.left_menu .layui-nav-item>a').on('mousedown', function() {

	});
}

//点击navbar时默认加载第一个子单元
var loadsidebarFirst = function(){
	$("#navbar li").click(function(){
		var hash;
        if($("#left_menu").children().eq(1).is(':has(dl)')){
            hash = $("#left_menu").children().eq(1).children('dl').children().eq(0).children('a').attr("href");
            $("#left_menu").children().eq(1).children('dl').children().eq(0).children('a').click();
        } else {
            hash = $("#left_menu")[0].childNodes[1].children[0].hash;
            $("#left_menu").children().eq(1).children('a').click();
        }
		location.hash = hash;		
	});
}
//加载面包屑导航
function showBreadcrumb(breadName){
    var breadNames = breadName.split(",");
    var html = '';
    for(var i = 0; i < breadNames.length; i++){
        html += "<i class='fa fa-angle-double-right' aria-hidden='true'></i><a href='#'>"+breadNames[i]+"</a>";
    }
    $('.level2').html(html);
}