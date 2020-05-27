window.app.navbar = function(){
	function init() {
		var header_html = $.templates.header.render();
		var footer_html = $.templates.footer.render();
		var html = $.templates.navbar.render();
		window.top_navbar.html(html);
		window.header.html(header_html);
		window.footer.html(footer_html);
		
		loadMainMenu();
	}
	
	var loadMainMenu = function(){
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "../user/usermenu",
			success : function(result) {
//				for(x in result){
//					$("#navbar").append(
//						"<li onclick=showMenuOne(\""+result[x].id+"\"); id="+ result[x].id +" class=\"layui-nav-item\">" 
//						+ "<a href=\"javascript:void(0);\">"	
//						+ "<img class=\"nav-item-img\" src=\""
//						+ result[x].icon + "\" />"
//						+ "<span class=\"nav-item-span\">" + result[x].name
//						+ "</span>" + "</a>" + "</li>");
//				}
                var html = '';
                for(var i = 0;i < result.length;i++){
                    if(result[i].name == '首页'){                   	 
                        html += "<li id='mainPage' class='layui-nav-item'>";
                        html += "<a href='#/home?appname=mainIndex'>";
                    } else {
                        html += "<li onclick=showMenuOne(\""+result[i].id+"\",\""+result[i].name+"\"); id="+ result[i].id +" class=\"layui-nav-item\">";
                        html += "<a href=\"javascript:void(0);\">";
                    }
                    html += "<span class='nav-item-span'>" +result[i].name+ "</span>";
                    html += "</a>";
                    html += "</li>";
                }
                $("#navbar").append(html);
				keepNavbarSelected();
				
				setTimeout(function(){
					refreshToLastMenu(result);					
				},10);
				
				loadWorkArea();
				
				LAYUI_ELEMENT();
			},
			error : function() {
				MSG("异常！");
			}
		});
	}	
	//保持nav元素选中状态
	var keepNavbarSelected = function(){
		$(".top-navbar .layui-nav-item").on('click',function(){
			$(".top-navbar .layui-nav-item").css({"background-color":"transparent"});
			$(".nav-item-span").css({"color":"#fff"});
			$(this).css({"background-color":"#168bff"});
			$(this).find(".nav-item-span").css("color","#fff");			
		});
	}	
	//刷新后保持菜单选中
	function refreshToLastMenu(result){
        $('#mainPage').click();
        var viewHeight = window.innerHeight;
        var workView = viewHeight - 120;
        location.hash = "#/home?appname=mainIndex";
        $("#menu_sidebar").css("display","none");
        $(".breadcrumb").css("display","none");
        $("#work_area").css({"margin":"100px 0px 0px 0px","height":workView});
//		var hash = window.location.hash;		
//		if(hash == "#/systemControl?appname=mainIndex" || "" || null || !hash){			
//			var viewHeight = window.innerHeight;
//			var workView = viewHeight - 120;
//			$("#menu_sidebar").css("display","none");
//			$("#work_area").css({"margin":"100px 0px 0px 0px","height":workView});
//		}else{
//			menu = GETQUERYPARAM("menu");
//			if(!menu){
//				/*var url = location.hash;
//				var temp = url.indexOf("?");
//				if (temp != -1) {
//					url = url.substring(0, temp);
//				}
//				menu = url;*/
//				menu = hash;
//			}else{
//				menu = "#/"+menu;
//			}
//			if(menu){
//				for(m in result){
//					if(result[m].subMenu){
//						var navMenu = result[m].subMenu;				
//						for(var i=0;i<navMenu.length;i++){
//							var leftMenu = navMenu[i]
//							if(!leftMenu.subMenu){
//								var menuHash = leftMenu.url;
//								if(menu == menuHash){
////									location.hash = "#";	//清除hash否则不会再次触发路由导航;
////									location.hash = hash;	//指向最后一次路由导航
//									$("#" + result[m].id).click();
//								}
//							}else{
//								var navMenuSons = leftMenu.subMenu;
//								for(var j=0;j<navMenuSons.length;j++){
//									if(menu == navMenuSons[j].url){
//										console.log(menu)
////										location.hash = "#";	//清除hash否则不会再次触发路由导航;
////										location.hash = hash;	//指向最后一次路由导航
//										$("#" + result[m].id).click();
//									}
//									//console.log(leftMenu);
//									//debugger;
//								}
//							}
//						}				
//					}else{
//						return false;
//					}
//				}
//			}else{
//				return false;
//			}
//		}			
	}
	//加载工作区
	var loadWorkArea = function(){		
		$("#navbar li").click(function(){
			if(this.id == "mainPage"){
				$("#menu_sidebar").css("display","none");
                $(".breadcrumb").css("display","none");
				$("#work_area").css("margin","100px 0px 0px 0px");
			}else{
//				$("#work_area").empty();
				$("#work_area").css({"margin":"0px 0px 0px 220px"});
                $(".breadcrumb").css("display","block");
				$("#menu_sidebar").css("display","block");
				//console.log($("#navbar li"));
//				$("#left_menu")[0].childNodes[1].click(function(){
//					console.log(666666);
//				});
//				debugger;
				//console.log($("#left_menu"));
				//console.log($("#left_menu")[0].childNodes[1]);
			}				
		});
	}
	
	$.when(
		$.lazyLoadTemplate("navbar"),
		$.lazyLoadTemplate("header"),
		$.lazyLoadTemplate("footer")
	).done(function() {
		init();
	});
}
window.app.navbar();



