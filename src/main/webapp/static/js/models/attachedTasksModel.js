var AttachedTasksController = function($){
	var self = this;
    this.methods = {};
    this.strings = {};
    
    this.getAssignedTichers = function(){
    	self.teachers = window.localStorage.getItem("assigned");
    	return self;
    };
    
    this.init = function(){
    	self.getAssignedTichers();
    };
}, TasksModel, atm, atc;

atc = new AttachedTasksController(jQuery);
atc.init();

AttachedTasksModel = function($){
	var self = this;
    
	this.assignedTeachers = ko.observableArray( atc.teachers ? JSON.parse(atc.teachers) : []);
};

atm = new AttachedTasksModel(jQuery);

ko.applyBindings(atm, document.getElementById("attachedTasksModel"));

tutor.dispatchEvent("busketGot");