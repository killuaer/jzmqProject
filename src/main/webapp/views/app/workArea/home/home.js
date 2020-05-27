var table;
window.app.home = function() {
	function init() {
		var html = $.templates.home.render();
		window.work_area.empty();
		window.work_area.html(html);
		home();
	}
	$.when($.lazyLoadTemplate("home")).done(function() {
		init();
	});

	function home() {
		
	}
	return this;
};
var home = window.app.home();
