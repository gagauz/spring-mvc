var MApp = function () {
    var self = this;

    /**
     * @name {_interface}
     * @param {Object} setup
     * @param {Object} extra <as like beforeSend:function(){}..>
     * @return {Object}
     * @use /-self._interface({type:'get'|'post', url:url|+,(data:{})|undefined}, false|true)-/
     */
    this.ajaxInterface = function (setup, extra) {
        var _self = this;
        /**@type {Object} base*/
        this.base = {
            url: null,
            type: 'post',
            data: null
        };

        /**@set {url} */
        this.base.url = setup.url;

        !!setup.type && setup.type.toLowerCase() === "get"
            ? delete this.base.type
            : this.base.data = setup.data;

        !!extra
            ? $.extend(_self.base, extra)
            : void (0);

        return $.ajax(_self.base);
    };

    /**
     * @name {dispatchEvent}
     * @param {Event} eventName
     * @param {Object|int|String|array|undefined} data
     * @return {Object} self
     */
    this.dispatchEvent = function (eventName, data) {
        $(document).trigger(eventName, data);
        return this;
    };

    this.summ = function(m) {
        var ml = m.length;
        for (var s = 0, k = ml; k; s += parseFloat(m[--k]));
        return s.toFixed(2);
    };

};

var mApp = new MApp();

;(function ($, window, document) {
    /**
	 * Alert Plagin
	 *
	 * Dual licensed under the MIT and GPL licenses:
	 * http://www.opensource.org/licenses/mit-license.php
	 * http://www.gnu.org/licenses/gpl.html
	 *
	 * @author Arthur Enokyan/xazy06@gmail.com
	 *
	 *  -------EXAMPLE-------
	 *
	 *   $.alert('alert text'); // renders simple alert as default[red color]
	 *   $.alert(['alert text1', 'alert text2'], 'success'); // renders success alert with multi string content
	 *   $.alert('alert text', 'info'); //renders simple info alert
	 *   
	 *
	 */
    $.alert = function (alertText, Atype, autohide) {
        if (alertText instanceof Array) {
            for (var i = 0, len = alertText.length, spanMessages = ''; i < len ; ++i) {
                spanMessages += '<span>' + alertText[i] + '</span><br/>';
            }
        } else {
            var spanMessages = alertText;
        }

        Atype === undefined
		? Atype = 'error'
		: Atype = Atype
        ;
        /**
         *   Main template {Object JQuery}
         */
        var $template = ('<div class="alert-block_' + Atype + ' alert alert-block">' +
						 '    <i class="icon icon-alert_big___' + Atype + ' s-mr_20"></i>' +
						 '    <span class="alert-heading">' + spanMessages + '</span>' +
						 '    <a class="close icon s-float_r s-pt_31" data-dismiss="alert" href="javascript:void(0)">' +
						 '        <i class="icon icon-alert_close"></i>' +
						 '    </a>' +
						 '</div>')
		  , appPlace = $('body')
          , closer = $('[data-dismiss="alert"]')
          , aBlock = $('.alert-block')
        ;
        aBlock.remove();
        switch (Atype) {
            //*/ if needed difference
            case 'error': appPlace.append($template);
                break

            case 'info': appPlace.append($template);
                break

            case 'warning': appPlace.append($template);
                break

            case 'success': appPlace.append($template);
                break
                //*/	
            default: appPlace.append($template);
                break
        }
        if (!autohide) {
            setTimeout(function () {
                $('.alert').fadeOut(100);
            }, 2000);
        }
        $(document).on('click', '[data-dismiss="alert"]', function (event) {
            event = event || window.event;
            event.preventDefault ? event.preventDefault() : event.returnValue = false;
            $(this).parent().remove();
        });
    };
})(jQuery, window, document);

/**@{grid system}*/
(function () {
    var b = $("div.s-grid");
    $(document).bind("keydown", function (a) {
        a = a || window.event;
        1 == a.altKey && (1 == a.shiftKey && 1 == a.ctrlKey) && (b.toggle(), b.height($(document).height() - b.offset().top));
    });
})();
