/**
 * @name {FeedbackController}
 * @author {Enokyan A/R}
 * @return {Object}
 */
var FeedbackController = function (controller) {
    var self = this;

    this.dataGot = 0;
    this.dataNeed = 0;
    
    this.strings = {
    	requaredFields:"Пожалуйста заполните все поля!",
    	success:"Мы скоро с Вами свяжемся, спасибо!"
    };

    this.init = function () {
        self.dataGot = 0;
    };
};

var controller = new FeedbackController(false);
controller.init();

/**
 * @name {FeedbackModel}
 * @return {undefined}
 */
var FeedbackModel = function ($) {
    var self = this;


    this.name = ko.observable("");
    this.secondName = ko.observable("");
    this.phone = ko.observable("");
    this.mail = ko.observable("");
    this.message = ko.observable("");

    this.yandexCounderBind = function () {
        var date = new Date(),_this = this,capcha,fd = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear() + ' , ' + (date.getHours()) + ':' + (date.getMinutes() + 1);

        this.params = {
            fd: fd,
            captcha: 'request_' + fd,
            yaParams: {}
        };

        this.params.yaParams[_this.params.captcha] = {
            'имя': self.name(),
            'телефон': self.phone(),
            'дата записи': fd,
            'сообщение': self.message()
        }
        
        return yaCounter29767232.reachGoal('send_message', _this.params.yaParams);
    };

    this.submit = function () {
        self.mess = {
            name: self.name(),
            secondName: self.secondName(),
            phone: self.phone(),
            mail: self.mail(),
            sub: "Форма обратной связи",
            mess: self.message()
        };

        /*self.yandexCounderBind();*/

        if (!!self.name().length && !!self.phone().length && !!self.mail().length && !!self.mail().indexOf("@") && self.enable()) {
            /**
             * @if ajax submit
             * askApp.ajaxInterface({ type: "post", url: self.methods.send, data: self.mess }, false).done(self.submitCallback);
             */
        	return true;
        } else {
            $.alert(controller.strings.requaredFields);
            return false;
        }

    };

    this.capcha1 = ko.observable(parseFloat(Math.random() * 10).toFixed(0));
    this.capcha2 = ko.observable(parseFloat(Math.random() * 10).toFixed(0));
    this.capchaTrueResult = ko.observable(+this.capcha1() + +this.capcha2());

    this.capchaInputResult = ko.observable();

    this.enable = function () {
        return self.capchaInputResult() == self.capchaTrueResult();
    };
    
};
var model = new FeedbackModel(jQuery);

ko.applyBindings(model, document.getElementById("feedback"));
window.model = model;

    
