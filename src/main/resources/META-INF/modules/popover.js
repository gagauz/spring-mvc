(function(){
	define(["jquery", "bootstrap/popover"], function($, popover) {
		var showPopover = function(on, element) {
			$(on).popover({
				trigger: 'manual',
				html : true,
		        content: function(){return $(element).html()}
			}).popover('show');
		};
		return {showPopover: showPopover};
	});
}).call(this);