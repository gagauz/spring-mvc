var SettingsModel = function($){
	var self = this;
    this.showOnSearch = ko.observable(document.getElementById("showOnSearchYes").checked.toString());
    this.showOnMap    = ko.observable(document.getElementById("showOnMapYes").checked.toString());
    this.needStudents = ko.observable(document.getElementById("needStudentsYes").checked.toString());
    
};

var sm = new SettingsModel(jQuery);

ko.applyBindings(sm, document.getElementById("settingsModel"));