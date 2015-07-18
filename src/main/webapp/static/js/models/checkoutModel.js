var CheckoutController = function($){
	var self = this;
    this.methods = {};
    this.strings = {
    	chooseRegion: "Выберите район"
    };
    this.selectors = {
    	regions: "#regions"
    };
       
    this.getAssignedTichers = function(){
    	self.teachers = window.localStorage.getItem("assigned");
    	return self;
    };
    
    /**
     * @name {regionsConverter}
     * @return {Array} [{@id,@text}]
     *  ---map regions ---
     */
    this.regionsConverter = function() {
    	return $.map(JSON.parse(self.regions), function(item) {
    		return { id: item.id, text: item.name };
    	});
    };

    /**
     * @name {initSelect2}
     * @void
     *  ---init select2plugin---
     */
    this.initSelect2 = function() {
    	jQuery(self.selectors.regions).select2({
    		data: self.regionsConverter(),
    		
    		placeholder: self.strings.chooseRegion,
    		maximumSelectionSize: 1
    	});
    };
    
    this.init = function(){
    	self.getAssignedTichers();
    };
}, cm, cc;

cc = new CheckoutController(jQuery);
cc.init();

CheckoutModel = function($){
	var self = this;
	
	this.assignedTeachers = ko.observableArray( cc.teachers ? JSON.parse(cc.teachers) : []);
	
	this.assignedTeachersStringList = ko.observableArray(
		cc.teachers 
			? _.map(JSON.parse(cc.teachers), function(teacher){return teacher.id;})
		    : []
	);
	
	this.selectedRegion = ko.observable();
};

cm = new CheckoutModel(jQuery);

ko.applyBindings(cm, document.getElementById("CheckoutModel"));