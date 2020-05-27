var dictTemp = {};

var getDict = function(dict) {
	if(dictTemp[dict]){
		return dictTemp[dict];
	}else{
		$.ajax({
			type : "GET",
			url : "../main/dictonary/find?category="+dict,
			async : false, 
			success : function(result) {
				if(result && result.length !=0){
					dictTemp[dict] = {
						labelField : "name",
						valueField : "code",
						data:result
					};
				}else{
					MSG('获取数据字典失败 '+dict);
				}
			},
			error : function(data) {
				MSG('获取数据字典失败 '+dict);
			}
		});
		return dictTemp[dict];
	}
}

/**
 * 转义字典工具
 */
window.toDict = function(dict, value) {
	var data = getDict(dict);
	var _value = "";
	if (data && dict && value && data["labelField"] && data["valueField"]) {

		var labelField = data["labelField"];
		var valueField = data["valueField"];

		var list = data["data"];

		// 分割方式，默认,
		var spaceMode = data["spaceMode"];
		if (!spaceMode) {
			spaceMode = ",";
		}

		if ($.isNumeric(value)) {
			analysis(value);
		} else if ($.type(value) == "string") {
			// value 多个,分割，循环处理
			$.each(value.split(','), function(i, e) {
				analysis(e);
			});
		}

		function analysis(value) {
			$.each(list, function(index, elem) {
				if (elem[valueField] == value) {
					if (_value) {
						_value += spaceMode;
					}
					if (elem[labelField]) {
						var css = elem["css"];// 样式处理
						var style = elem["style"];
						if (css || style) {
							_value += "<span class=\"" + css + "\" style=\""
									+ style + "\">" + elem[labelField]
									+ "</span>";
						} else {
							_value += elem[labelField];
						}
					}
					return false;
				}
			});
		}

		var otherValue = data["otherValue"];
		if (!_value && otherValue) {
			_value = otherValue;
		}

	}
	return _value;
};

/**
 * 渲染全部数据字典配置
 */
window.renderDictAll = function(form) {
	var thisForm = this;
	if (form) {
		form.find(".toDict").each(function() {
			var _this = $(this);
			if (_this.is('input')) {// 单选或者多选
				var type = _this.attr("type").toLowerCase();
				var dict = _this.attr("dict");
				if (dict && (type == "checkbox" || type == "radio")) {
					thisForm.loadDictData(_this, false, null, type);
				}else{
					console.log("未识别类型:"+type)
				}
			} else if (_this.is('select')) {
				thisForm.loadDictData(_this,false,null,"select");
			}
		});
	} else {
		$(".toDict").each(function() {
			var _this = $(this);
			if (_this.is('input')) {// 单选或者多选
				var type = _this.attr("type").toLowerCase();
				var dict = _this.attr("dict");
				if (dict && (type == "checkbox" || type == "radio")) {
					thisForm.loadDictData(_this, false, null, type);
				}else{
					console.log("未识别类型:"+type)
				}
			} else if (_this.is('select')) {
				thisForm.loadDictData(_this,false,null,"select");
			}
		});
	}
}

/**
 * 加载数据
 */
window.loadDictData = function(_this, b, value, type) {
	var thisForm = this;
	var addNull = _this.attr("addNull");// 是否显示空值，1 显示
	var isLoad = _this.attr("isLoad");// 是否自动加载，1 是
	if (type == "select") {
		_this.empty();// 清空
		if (addNull == "1") {
			_this.append("<option></option>");
		}
	}
	var dict = _this.attr("dict");
	if (!dict) {
		return false;
	}
	var dictObj = getDict(dict);
	if (!dictObj) {
		return false;
	}
	var labelField = dictObj["labelField"];
	var valueField = dictObj["valueField"];
	var method = dictObj["method"];
	var list = dictObj["data"];
	thisForm.dictDataRender(_this, labelField, valueField, list, type);
};

window.dictDataRender = function(_this, labelField, valueField, list, type) {
	var thisForm = this;
	if (type == "select") {
		thisForm.selectDataRender(_this, labelField, valueField, list);
	} else if (type == "checkbox") {
		thisForm.checkboxDataRender(_this, labelField, valueField, list);
	} else if (type == "radio") {
		thisForm.radioDataRender(_this, labelField, valueField, list);
	} else{
		console.log("未识别的类型:"+type);
	}
}

/**
 * 复选框数据渲染
 */
window.checkboxDataRender = function(_this, labelField, valueField, list) {
	var thisForm = this;
	var name = _this.attr("name");
	var laySkin = _this.attr("lay-skin");
	$(list)
			.each(
					function(i, v) {
						var checkbox = "<input type=\"checkbox\" name=\""
								+ name + "\" lay-skin=\"" + laySkin
								+ "\" title=\"" + v[labelField] + "\" value=\""
								+ v[valueField] + "\">";
						_this.parent().append(checkbox);
					});
	_this.remove();
}

/**
 * 单选框数据渲染
 */
window.radioDataRender = function(_this, labelField, valueField, list) {
	var thisForm = this;
	var name = _this.attr("name");
	$(list).each(
			function(i, v) {
				var checkbox = "<input type=\"radio\" name=\"" + name
						+ "\" title=\"" + v[labelField] + "\" value=\""
						+ v[valueField] + "\">";
				_this.parent().append(checkbox);
			});
	_this.remove();
}

/**
 * select数据渲染
 */
window.selectDataRender = function(_this, labelField, valueField, list) {
	var thisForm = this;
	$(list).each(
			function(i, v) {
				var option = "<option value=\"" + v[valueField] + "\">"
						+ v[labelField] + "</option>";
				_this.append(option);
			});
};
