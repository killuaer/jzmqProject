var slideVerify_ts='';
//layui相关组件支持
layui.use(['element','carousel','form','layer'],function(){
	var carousel = layui.carousel,
	element = layui.element,
	form = layui.form,
	layer = layui.layer;
	
	var result;
	var param;
	var paramName = "result";
	var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
	var	hash = window.location.href;
	var index = hash.indexOf("?");
	var hash = hash.substr(index + 1);
	var r = hash.match(reg);
	if (r != null) {
		result =  unescape(decodeURI(r[2]));
	}
	if(result){
		layer.msg(result);
	}
});

// DES加密
function encryptByDES(message) {
    //声明一个私钥
    var key = "12345678";
    //把私钥转化成16进制的字符床
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode : CryptoJS.mode.ECB,
        padding : CryptoJS.pad.Pkcs7
    });
    //加密出来是一个16进制的字符串
    return encrypted.ciphertext.toString();
}

function changeImg(obj) {
	$(obj).attr('src', 'captcha-image.jpg?' + Math.floor(Math.random() * 100));
}
//加密登录
function doglogin() {
	var dogkey = GetDogKey("1");
	if (dogkey) {
		$("#keyId").val(dogkey);
		$("#submitCtrlWithoutVerify").click();
	}
}
//普通登录
function login() {
	$("#keyId").val("");
    //将加密后的用户名和密码传递到后台
    var username=$("#p_username").val();    
    var password=$("#p_password").val();
    if(isEmpty(username)){
    	layer.msg("用户账号不能为空");
    	return "";
    }
    if(isEmpty(password)){
    	layer.msg("用户密码不能为空");
    	return "";
    }
    if(!slideVerify_ts){				
		layer.msg("请拖动滑块进行验证!");
		return "";
	}
    //加密操作
    document.getElementById("username").value = encryptByDES($("#p_username").val());
    document.getElementById("password").value = encryptByDES($("#p_password").val());
    var username1 = $("#username").val();
    var password1 = $("#password").val();
    $.ajax({
        type : "POST",
        url : "admin/login",
        data :  {username:$("#username").val(),password:$("#password").val()}, 
        dataType : "json",
        success: function (data) {
            if (data.status == "success") {
				window.location.href="views/main.html"; 
			} else {
				layer.msg("账号/密码错误");
			}
        },
        error: function (msg) {
            alert(msg.responseText);
        }

    });
//	$("#submitCtrl").click();
}

function GetDogKey(needMessage) {
	var dogkey = "";
	try {
		//检测驱动
		ePass.GetLibVersion();
		try {
			//打开驱动
			ePass.OpenDevice(1, "");
			dogkey = ePass.GetStrProperty(7, 0, 0);
			//关闭驱动
			ePass.CloseDevice();
			return dogkey;
		} catch (e) {
			if (needMessage) {
				alert("请插入软件锁");
			}
			return dogkey;
		}
	} catch (e) {
		if (needMessage) {
			alert("请安装软件锁驱动");
		}
		return dogkey;
	}
}

//回车登录
$('body').on('keypress',function(event){
	if(event.keyCode == 13){
		login();
	}
});
function back(){
	layer.msg("请联系统管理员重置密码!");
}

$(function(){

	var SlideVerifyPlug = window.slideVerifyPlug;
	
	var slideVerify2 = new SlideVerifyPlug('#verify-wrap2',{
	wrapWidth:'330',
	initText:'请按住滑块往右拖动！',
	sucessText:'验证通过！',	
	getSucessState:function(res){
		//当验证完成的时候 会 返回 res 值 true，只留了这个应该够用了 
		//console.log(res);
		slideVerify_ts= slideVerify2.slideFinishState;
	}
	});
	
})
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}