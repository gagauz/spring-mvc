(function(){
	define(["jquery", "bootstrap/popover"], function($, popover) {
		var showPopover = function(content) {
			if (!$(content).is(':visible')) {
				$(content).css({opacity:0,position:'absolute'});
			}
			$(content).popover({
				trigger: 'manual',
				html : true,
		        content: function(){console.log($(this)[0]);return $(this).html()}
			}).popover('show');
			$(content).css({opacity:1});
		};
		return {showPopover: showPopover};
	});
}).call(this);