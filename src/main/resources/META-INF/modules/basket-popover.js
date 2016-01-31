(function(){
	define(["jquery", "bootstrap/popover"], function($, popover) {
		var showPopover = function(e1, e2, e3, qty) {
			$(e1).popover({
				trigger: 'manual',
				html : true,
		        content: function(){return $(e2).html()}
			}).popover('show');
			$(e3).text(qty);
		};
		return {showPopover: showPopover};
	});
}).call(this);