var $j = jQuery.noConflict();
$j.ajaxSetup( {
	cache : false
});

var HHHTC = {
	flashTC : {
		flashURL : "",
		dataURL : "",
		contentId : "flashContent",
		flashId : "TowerCrane",
		flashName : "TowerCrane",
		freshInterval : 10000,
		handleData : function(data) {
			if (null == data){
				alter('暂无监测数据')
				return;
			}
			try{
				var tc = $j("#" + HHHTC.flashTC.flashId)[0];
				if(tc.traceInfo){
					tc.traceInfo(data);
				}
				if(tc.handleData){
					tc.handleData(data);
				}
			}catch(e){
			}
			var data = HHHTC.flashTC.getData;
			setTimeout(HHHTC.flashTC.getData, HHHTC.flashTC.freshInterval);
		},
		handleDataError : function(obj, msg) {
			try{
				var tc = $j("#" + HHHTC.flashTC.flashId)[0];
				if(tc.traceInfo) tc.traceInfo("获取数据异常：" + msg);
			}
			catch(e){}
			setTimeout(HHHTC.flashTC.getData, HHHTC.flashTC.freshInterval);
		},
		getData : function() {
			$j.ajax( {
				url : HHHTC.flashTC.dataURL,
				success : HHHTC.flashTC.handleData,
				error : HHHTC.flashTC.handleDataError,
				dataType : "txt"
			});
		},
		start : function() {
			$j(function() {
				var swfVersionStr = "10.2.0";
				var xiSwfUrlStr = "";
				var flashvars = {};
				var params = {};
				params.quality = "high";
				params.bgcolor = "#e3e3ed";
				params.allowscriptaccess = "sameDomain";
				params.allowfullscreen = "true";
				var attributes = {};
				attributes.id = HHHTC.flashTC.flashId;
				attributes.name = HHHTC.flashTC.flashName;
				attributes.align = "middle";
				swfobject.embedSWF(HHHTC.flashTC.flashURL, HHHTC.flashTC.contentId,
						"780", "550", swfVersionStr, xiSwfUrlStr, flashvars,
						params, attributes, function(result){ 
								if(result.success == false){
									$j("#"+HHHTC.flashTC.contentId).remove();
									$j('body').append('<object type="application/x-shockwave-flash" id="TowerCrane" name="TowerCrane" align="middle" data="http://192.168.4.27:8080/yczs-web/js/td/flash/TowerCrane.swf" width="100%" height="96%"><param name="quality" value="high"><param name="bgcolor" value="#e3e3ed"><param name="allowscriptaccess" value="sameDomain"><param name="allowfullscreen" value="true"></object>')
								}
								HHHTC.flashTC.getData(); 
						}
				);
				swfobject.createCSS(HHHTC.flashTC.contentId,
						"display:block;text-align:left;");
				//setTimeout(HHHTC.flashTC.getData, HHHTC.flashTC.freshInterval);
			});
		}
	}
};
