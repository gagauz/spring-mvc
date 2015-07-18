/**
 * @name {StudentsOrderController}
 * @author {Enokyan A/R}
 * @return {Object}
 */
var StudentsOrderController = function () {
    var self = this;

    this.dataGot = 0;
    this.dataNeed = 1;

    this.methods = {
        orders: "/"
    };

    this.selectors = {};

    this.strings = {};

    this.getOrders = function () {
        tutor.ajaxInterface({ url: self.methods.orders }, false).done(self.getOrdersCallback);
    };

    this.getOrdersCallback = function (response) {
        var list = JSON.parse(response);
        self.orderList = list;

        self.dataGot++;
        tutor.dispatchEvent("dataGot");
    };
    
    /**
     * @name {acceptOrder}
     * @param {object} order ~ map  --> OrdersModel
     * @void
     */
    this.acceptOrder = function(order){
    	
    	
    };

    this.init = function () {
        self.dataGot = 0;
        self.getOrders();
    };
};

var soc = new StudentsOrderController(); soc.init();

/**
 * @Class {Order}
 * @return {Object} Order
 */
var Order = (function () {
    function Order(view, row) {
        var self = this;
        this.view = view;
        this.id = ko.observable(row.id);
        this.subject = ko.observable(row.subject);
        this.date = ko.observable(row.date);
        this.cost = ko.observable(row.cost);
        this.placeType = ko.observable(row.placeType);
        this.address = ko.observable(row.address);
        
        this.show = function() {
            self.view.selectedOrder(this);
        };
    }
    return Order;
})();

/**
 * @Class {OrderModel}
 * @return {Object} OrderModel
 */
var OrderModel = (function () {
    function OrderModel() {
        var rows, tableOptions, _this = this;
        tableOptions = {
            recordWord: 'заявка',
            recordWordPlural: 'заявок',
            sortDir: 'desc',
            sortField: 'name',
            perPage: 15,
            unsortedClass: "fa fa-sort s-green",
            ascSortClass: "fa fa-sort-amount-asc s-green",
            descSortClass: "fa fa-sort-amount-desc s-green"
        };
        rows = soc.orderList.map(function (row) {
            return new Order(_this, row);
        });
        this.table = new DataTable(rows, tableOptions);

        this.selectedOrder = ko.observable(soc.orderList[0]);
        
        this.acceptOrder = function () {
            soc.acceptOrder(_this.selectedTask());
        };
      
        window.model = this;
        ko.applyBindings(this);
    }
    return OrderModel;
})();

jQuery(document).on("dataGot", function () {
    if (soc.dataNeed === soc.dataGot) {
        window.om = new OrderModel();
    }
});
