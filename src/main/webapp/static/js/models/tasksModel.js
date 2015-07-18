var TasksController = function($){
	var self = this;
    this.methods = {};
   
    this.strings = {
    	duplicateTeacher : "Вы уже добавили этого репетитора!"
    };
    
    this.pushAction = function(teacher){
    	_.where(atm.assignedTeachers(), { id: teacher.id }).length === 0 
    		? (atm.assignedTeachers.push(teacher), self.fillLocalStorage(atm.assignedTeachers()))
    		: $.alert(self.strings.duplicateTeacher);
    };
        
    this.fillLocalStorage = function(teachers){
    	return window.localStorage.setItem("assigned", JSON.stringify(teachers));
    };
    
    this.init = function(){
    	window.console ? console.log(this, +new Date):void(0)
    };
}, TasksModel, tm, tc;

tc = new TasksController(jQuery);tc.init();

TasksModel = function($){
	var self = this;
	
	this.selectedTeachers = ko.observableArray();
};

ko.bindingHandlers.addTeacher = {
    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
    	console.log(valueAccessor());
       jQuery(element).on("click", function(){tc.pushAction(valueAccessor())});
    },
    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {}
};

tm = new TasksModel(jQuery);

ko.applyBindings(tm, document.getElementById("TasksModel"));