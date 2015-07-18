;(function($) { 
	/* Cлайдер цен */
	$.fn.priceSlider = function(opts) {
		var $this = this, inputMin = $("input[name=minSl]"), inputMax = $("input[name=maxSl]");
    	if ($this.length > 0) {
    		opts = $.extend({
    			orientation: "horizontal",
    			min: 0,
    			max: 100,
    			values: [0, 100],
    			range: true,
    			stop: function(event, ui) {
    				inputMin.val(ui.values[0]);
    				inputMax.val(ui.values[1]);
    			},
    			slide: function(event, ui){
    				inputMin.val(ui.values[0]);
    				inputMax.val(ui.values[1]);
    			}
    		}, $this.data(), opts);
    		
    		$this.slider(opts);
    
    		inputMin.change(function(){
    			$this.slider('values', 0, $(this).val());
            });
    			
    		inputMax.change(function(){
    			$this.slider('values', 1, $(this).val());
            });
    	
    		$('input', $this).keypress(function(event){
    			var key, keyChar;
    			if(event || (event = window.event)) {
        			if (event.keyCode) key = event.keyCode;
        			else if(event.which) key = event.which;
        	
        			if(key==null || key==0 || key==8 || key==13 || key==9 || key==46 || key==37 || key==39 ) return true;
        			keyChar=String.fromCharCode(key);
        			if(!/\d/.test(keyChar))	return false;
    			}
    		});
    	}
    	
    	return this;
	}
})(jQuery);