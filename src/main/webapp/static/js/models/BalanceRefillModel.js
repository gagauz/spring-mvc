var BalanceRefillModel = function(ammount){
	var self = this;
	
	this.ammount = ko.observable(ammount);
			
}, brm;

ko.bindingHandlers.checkSumm = {
	    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {},
	    update: function(element, valueAccessor) {
	    	jQuery(element).on("keypress", function(event){
	    		event = event || window.event;
	    		if (event.which != 8 && event.which != 0 && (event.which < 48 || event.which > 57)) {
	                return false;
	            }
	    	});
	    }
	};

brm = new BalanceRefillModel(document.location.search.substr(6));
ko.applyBindings(brm, document.getElementById("BalanceRefill"));