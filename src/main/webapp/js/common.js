jQuery.extend({
	lazyLoadTemplate: function (name) {
		var deferred = $.Deferred();
		if ($.templates[name]) {
			deferred.resolve();
		} else {
			var url = window.tmplconfig[name];
			$.get(url, function (result, status) {
				if ("success" === status) {
					$.templates(name, result);
					deferred.resolve();
				} else {
					deferred.reject();
				}
			}, "text");
		}
		return deferred.promise();
	},
	getQueryString: function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var hash = window.location.hash;
		var index = hash.indexOf("?");
		var hash = hash.substr(index + 1);
		var r = hash.match(reg);
		if (r != null) {
			return unescape(decodeURI(r[2]));			
		}
		return null;
	},
	route: function () {
		var anchor = location.hash;
		if (anchor && anchor != "#") {
			window.work_area.empty();
			var url = anchor.substr(2);
			var temp = url.indexOf("?");
			if (temp != -1) {
				url = url.substring(0, temp);
			}
			if (!window.app[url]) {
				for (var i = 0; i < window.appConfig.length; i++) {
					var home = window.appConfig[i];
					if (home.name == url) {
						jQuery.getScript(home.path);
						break;
					}
				}
			} else {
				window.app[url]();
			}
		}
	}
});





