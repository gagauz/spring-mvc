var Tutor = function($){
    var self = this;

    this.initBodyScroll = function(){
        return $('html').niceScroll({
            zindex: "50002",
            scrollspeed: 50,
            mousescrollstep: 20,
            cursorwidth: 10,
            cursorborder: 0,
            background: "rgba(0,0,0,0.2)",
            cursorcolor: 'rgb(47, 56, 70)',
            cursorborderradius: 0,
            autohidemode: false,
            horizrailenabled: true
        });
    };

    /**
	 * @name {_interface}
	 * @param {Object} setup
	 * @param {Object} extra <as like beforeSend:function(){}..>
	 * @return {Object}
	 * @use /-self._interface({type:'get'|'post',
	 *      url:url|+,(data:{})|undefined}, false|true)-/
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
    
    this.toggler = function(){
    	var _this = this;
    	this.$$selector = "a.js-toggler";

    	$(document).on("click.repetitor", _this.$$selector, function(){
    		$(this)
    		.toggleClass("open")
    		.parent()
    		.toggleClass("js-toggler_t");
    	});
    };
    
    this.toggler_m = function(){
    	var _this = this;
    	this.$$selector = "a.toggler_link_m";

    	$(document).on("click.repetitor", _this.$$selector, function(){
    		$(this)
    		.toggleClass("open")
    		$("div.s-right-fixed").slideToggle(200);
    	});
    };
    
    this.toggler_any = function(){
    	var _this = this;
    	this.$$selector = "a.js_toggler";
    	
    	$(document).on("click.repetitor", _this.$$selector, function(){
    		
    		var $this = $(this),
    			toggleBlocks = $this.data("block"),
    			hideBlocks = $this.data("hide-blocks"),
    			showBlocks = $this.data("show-blocks"),
        		toggleClass = ($this.data("class") || "open");
    		$this.toggleClass(toggleClass)
    		$(toggleBlocks).slideToggle(200);
    		$(hideBlocks).slideUp(200);
    		$(showBlocks).slideDown(200);
    	});
    };
    
    this.cleanBusket = function(){
    	$(document).on("busketGot",function(){return atm.assignedTeachers([]) && window.localStorage.removeItem("assigned");})
    };
    
    this.initMobileMenu = function(){
    	try{
	    	if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
		    	var snapper = new Snap({
			         element: document.getElementById('content'),
			         disable: 'right'
		        }), $menuToggler = $("#open-left");
		    	 
		    	
		    	$menuToggler.on('click', function(){
		    		if( snapper.state().state=="left" ){
		    	        snapper.close();
		    	    } else {
		    	        snapper.open('left');
		    	    }
		    		//$("html").toggleClass("s-overflow_h");
				});
		    	$("body").addClass("s_mob");
		    }
    	}catch(e){};
    };
    
    this.imgError = function(image) {
        $(image).attr("src", "data:image/png;base64,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");
    };
    
    this.tabs = function() {
        +function($) {
            'use strict';

            // TAB CLASS DEFINITION
            // ====================

            var Tab = function(element) {
                this.element = $(element);
            }

            Tab.prototype.show = function() {
                var $this = this.element, $ul = $this.closest('ul:not(.dropdown-menu)'), selector = $this.data('target');

                if (!selector) {
                    selector = $this.attr('href');
                    selector = selector && selector.replace(/.*(?=#[^\s]*$)/, ''); //strip for ie7
                }

                if ($this.parent('li').hasClass('active')) {
                    return;
                }

                var previous = $ul.find('.active:last a')[0];
                var e = $.Event('show.bs.tab', {
                    relatedTarget: previous
                });

                $this.trigger(e);

                if (e.isDefaultPrevented()) {
                    return;
                }

                var $target = $(selector);

                this.activate($this.parent('li'), $ul);
                this.activate($target, $target.parent(), function() {
                    $this.trigger({
                        type: 'shown.bs.tab',
                        relatedTarget: previous
                    });
                });
            }

            Tab.prototype.activate = function(element, container, callback) {
                var $active = container.find('> .active');
                var transition = callback
                    && $.support.transition
                    && $active.hasClass('fade');

                function next() {
                    $active
                        .removeClass('active')
                        .find('> .dropdown-menu > .active')
                        .removeClass('active');

                    element.addClass('active');

                    if (transition) {
                        element[0].offsetWidth; // reflow for transition
                        element.addClass('in');
                    } else {
                        element.removeClass('fade');
                    }

                    if (element.parent('.dropdown-menu')) {
                        element.closest('li.dropdown').addClass('active');
                    }

                    callback && callback();
                }

                transition ?
                    $active
                    .one($.support.transition.end, next)
                    .emulateTransitionEnd(150) :
                    next();

                $active.removeClass('in');
            }


            // TAB PLUGIN DEFINITION
            // =====================

            var old = $.fn.tab;

            $.fn.tab = function(option) {
                return this.each(function() {
                    var $this = $(this);
                    var data = $this.data('bs.tab');

                    if (!data) {
                        $this.data('bs.tab', (data = new Tab(this)));
                    }
                    if (typeof option == 'string') {
                        data[option]();
                    }
                });
            }

            $.fn.tab.Constructor = Tab;


            // TAB NO CONFLICT
            // ===============

            $.fn.tab.noConflict = function() {
                $.fn.tab = old;
                return this;
            }


            // TAB DATA-API
            // ============

            $(document).on('click.bs.tab.data-api', '[data-toggle="tab"], [data-toggle="pill"]', function(e) {
                e.preventDefault();
                $(this).tab('show');
            });

        }(jQuery);

    };
    this.modal = function() {
        +function ($) {
            'use strict';

            // MODAL CLASS DEFINITION
            // ======================

            var Modal = function (element, options) {
                this.options = options
                this.$body = $(document.body)
                this.$element = $(element)
                this.$dialog = this.$element.find('.modal-dialog')
                this.$backdrop = null
                this.isShown = null
                this.originalBodyPad = null
                this.scrollbarWidth = 0
                this.ignoreBackdropClick = false

                if (this.options.remote) {
                    this.$element
                      .find('.modal-content')
                      .load(this.options.remote, $.proxy(function () {
                          this.$element.trigger('loaded.bs.modal')
                      }, this))
                }
            }

            Modal.VERSION = '3.3.4'

            Modal.TRANSITION_DURATION = 300
            Modal.BACKDROP_TRANSITION_DURATION = 150

            Modal.DEFAULTS = {
                backdrop: true,
                keyboard: true,
                show: true
            }

            Modal.prototype.toggle = function (_relatedTarget) {
                return this.isShown ? this.hide() : this.show(_relatedTarget)
            }

            Modal.prototype.show = function (_relatedTarget) {
                var that = this
                var e = $.Event('show.bs.modal', { relatedTarget: _relatedTarget })

                this.$element.trigger(e)

                if (this.isShown || e.isDefaultPrevented()) return

                this.isShown = true

                this.checkScrollbar()
                this.setScrollbar()
                this.$body.addClass('modal-open')

                this.escape()
                this.resize()

                this.$element.on('click.dismiss.bs.modal', '[data-dismiss="modal"]', $.proxy(this.hide, this))

                this.$dialog.on('mousedown.dismiss.bs.modal', function () {
                    that.$element.one('mouseup.dismiss.bs.modal', function (e) {
                        if ($(e.target).is(that.$element)) that.ignoreBackdropClick = true
                    })
                })

                this.backdrop(function () {
                    var transition = $.support.transition && that.$element.hasClass('fade')

                    if (!that.$element.parent().length) {
                        that.$element.appendTo(that.$body) // don't move modals dom position
                    }

                    that.$element
                      .show()
                      .scrollTop(0)

                    that.adjustDialog()

                    if (transition) {
                        that.$element[0].offsetWidth // force reflow
                    }

                    that.$element
                      .addClass('in')
                      .attr('aria-hidden', false)

                    that.enforceFocus()

                    var e = $.Event('shown.bs.modal', { relatedTarget: _relatedTarget })

                    transition ?
                      that.$dialog // wait for modal to slide in
                        .one('bsTransitionEnd', function () {
                            that.$element.trigger('focus').trigger(e)
                        })
                        .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
                      that.$element.trigger('focus').trigger(e)
                })
            }

            Modal.prototype.hide = function (e) {
                if (e) e.preventDefault()

                e = $.Event('hide.bs.modal')

                this.$element.trigger(e)

                if (!this.isShown || e.isDefaultPrevented()) return

                this.isShown = false

                this.escape()
                this.resize()

                $(document).off('focusin.bs.modal')

                this.$element
                  .removeClass('in')
                  .attr('aria-hidden', true)
                  .off('click.dismiss.bs.modal')
                  .off('mouseup.dismiss.bs.modal')

                this.$dialog.off('mousedown.dismiss.bs.modal')

                $.support.transition && this.$element.hasClass('fade') ?
                  this.$element
                    .one('bsTransitionEnd', $.proxy(this.hideModal, this))
                    .emulateTransitionEnd(Modal.TRANSITION_DURATION) :
                  this.hideModal()
            }

            Modal.prototype.enforceFocus = function () {
                $(document)
                  .off('focusin.bs.modal') // guard against infinite focus loop
                  .on('focusin.bs.modal', $.proxy(function (e) {
                      if (this.$element[0] !== e.target && !this.$element.has(e.target).length) {
                          this.$element.trigger('focus')
                      }
                  }, this))
            }

            Modal.prototype.escape = function () {
                if (this.isShown && this.options.keyboard) {
                    this.$element.on('keydown.dismiss.bs.modal', $.proxy(function (e) {
                        e.which == 27 && this.hide()
                    }, this))
                } else if (!this.isShown) {
                    this.$element.off('keydown.dismiss.bs.modal')
                }
            }

            Modal.prototype.resize = function () {
                if (this.isShown) {
                    $(window).on('resize.bs.modal', $.proxy(this.handleUpdate, this))
                } else {
                    $(window).off('resize.bs.modal')
                }
            }

            Modal.prototype.hideModal = function () {
                var that = this
                this.$element.hide()
                this.backdrop(function () {
                    that.$body.removeClass('modal-open')
                    that.resetAdjustments()
                    that.resetScrollbar()
                    that.$element.trigger('hidden.bs.modal')
                })
            }

            Modal.prototype.removeBackdrop = function () {
                this.$backdrop && this.$backdrop.remove()
                this.$backdrop = null
            }

            Modal.prototype.backdrop = function (callback) {
                var that = this
                var animate = this.$element.hasClass('fade') ? 'fade' : ''

                if (this.isShown && this.options.backdrop) {
                    var doAnimate = $.support.transition && animate

                    this.$backdrop = $('<div class="modal-backdrop ' + animate + '" />')
                      .appendTo(this.$body)

                    this.$element.on('click.dismiss.bs.modal', $.proxy(function (e) {
                        if (this.ignoreBackdropClick) {
                            this.ignoreBackdropClick = false
                            return
                        }
                        if (e.target !== e.currentTarget) return
                        this.options.backdrop == 'static'
                          ? this.$element[0].focus()
                          : this.hide()
                    }, this))

                    if (doAnimate) this.$backdrop[0].offsetWidth // force reflow

                    this.$backdrop.addClass('in')

                    if (!callback) return

                    doAnimate ?
                      this.$backdrop
                        .one('bsTransitionEnd', callback)
                        .emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
                      callback()

                } else if (!this.isShown && this.$backdrop) {
                    this.$backdrop.removeClass('in')

                    var callbackRemove = function () {
                        that.removeBackdrop()
                        callback && callback()
                    }
                    $.support.transition && this.$element.hasClass('fade') ?
                      this.$backdrop
                        .one('bsTransitionEnd', callbackRemove)
                        .emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
                      callbackRemove()

                } else if (callback) {
                    callback()
                }
            }

            // these following methods are used to handle overflowing modals

            Modal.prototype.handleUpdate = function () {
                this.adjustDialog()
            }

            Modal.prototype.adjustDialog = function () {
                var modalIsOverflowing = this.$element[0].scrollHeight > document.documentElement.clientHeight

                this.$element.css({
                    paddingLeft: !this.bodyIsOverflowing && modalIsOverflowing ? this.scrollbarWidth : '',
                    paddingRight: this.bodyIsOverflowing && !modalIsOverflowing ? this.scrollbarWidth : ''
                })
            }

            Modal.prototype.resetAdjustments = function () {
                this.$element.css({
                    paddingLeft: '',
                    paddingRight: ''
                })
            }

            Modal.prototype.checkScrollbar = function () {
                var fullWindowWidth = window.innerWidth
                if (!fullWindowWidth) { // workaround for missing window.innerWidth in IE8
                    var documentElementRect = document.documentElement.getBoundingClientRect()
                    fullWindowWidth = documentElementRect.right - Math.abs(documentElementRect.left)
                }
                this.bodyIsOverflowing = document.body.clientWidth < fullWindowWidth
                this.scrollbarWidth = this.measureScrollbar()
            }

            Modal.prototype.setScrollbar = function () {
                var bodyPad = parseInt((this.$body.css('padding-right') || 0), 10)
                this.originalBodyPad = document.body.style.paddingRight || ''
                if (this.bodyIsOverflowing) this.$body.css('padding-right', bodyPad + this.scrollbarWidth)
            }

            Modal.prototype.resetScrollbar = function () {
                this.$body.css('padding-right', this.originalBodyPad)
            }

            Modal.prototype.measureScrollbar = function () { // thx walsh
                var scrollDiv = document.createElement('div')
                scrollDiv.className = 'modal-scrollbar-measure'
                this.$body.append(scrollDiv)
                var scrollbarWidth = scrollDiv.offsetWidth - scrollDiv.clientWidth
                this.$body[0].removeChild(scrollDiv)
                return scrollbarWidth
            }


            // MODAL PLUGIN DEFINITION
            // =======================

            function Plugin(option, _relatedTarget) {
                return this.each(function () {
                    var $this = $(this)
                    var data = $this.data('bs.modal')
                    var options = $.extend({}, Modal.DEFAULTS, $this.data(), typeof option == 'object' && option)

                    if (!data) $this.data('bs.modal', (data = new Modal(this, options)))
                    if (typeof option == 'string') data[option](_relatedTarget)
                    else if (options.show) data.show(_relatedTarget)
                })
            }

            var old = $.fn.modal

            $.fn.modal = Plugin
            $.fn.modal.Constructor = Modal


            // MODAL NO CONFLICT
            // =================

            $.fn.modal.noConflict = function () {
                $.fn.modal = old
                return this
            }


            // MODAL DATA-API
            // ==============

            $(document).on('click.bs.modal.data-api', '[data-toggle="modal"]', function (e) {
                var $this = $(this)
                var href = $this.attr('href')
                var $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))) // strip for ie7
                var option = $target.data('bs.modal') ? 'toggle' : $.extend({ remote: !/#/.test(href) && href }, $target.data(), $this.data())

                if ($this.is('a')) e.preventDefault()

                $target.one('show.bs.modal', function (showEvent) {
                    if (showEvent.isDefaultPrevented()) return // only register focus restorer if modal will actually get shown
                    $target.one('hidden.bs.modal', function () {
                        $this.is(':visible') && $this.trigger('focus')
                    })
                })
                Plugin.call($target, option, this)
            })

        }(jQuery);

    };
    
};

var tutor = new Tutor(jQuery);
tutor.initBodyScroll();
tutor.toggler();
tutor.initMobileMenu();
tutor.toggler_any();


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
    $.alert = function (alertText, Atype) {
        if (alertText instanceof Array) {
            for (var i = 0, len = alertText.length, spanMessages = ''; i < len ; ++i) {
                spanMessages += '<span>' + alertText[i] + '</span><br/>';
            }
        } else {
            spanMessages = alertText;
        }

        Atype === undefined
            ? Atype = 'error'
            : void(0)
        ;
        /**
         *   Main template {Object JQuery}
         */
        var $template = ("<div class=\"alert-block_" + Atype + " alert-block\">\
            \    <i class=\"icon icon-alert_big___" + Atype + " s-mr_20\"></i> \
            \    <span class=\"alert-heading\">" + spanMessages +"</span> \
            \    <a class=\"close icon s-float_r s-pt_31\" data-dismiss=\"alert\" href=\"javascript:void(0)\"> \
            \        <i class=\"icon icon-alert_close\"></i> \
            \    </a> \
            \</div>")
            , appPlace = $('body')
            , closer = $('[data-dismiss="alert"]')
            , aBlock = $('.alert-block')
            ;
        aBlock.remove();
        switch (Atype) {
            //*/ if needed difference
            case 'error': appPlace.append($template);
                break;

            case 'info': appPlace.append($template);
                break;

            case 'warning': appPlace.append($template);
                break;

            case 'success': appPlace.append($template);
                break;
            //*/
            default: appPlace.append($template);
                break
        }
        setTimeout(function () {
            $('div.alert-block').fadeOut(100);
        }, 2000);
        $(document).on('click', '[data-dismiss="alert"]', function (event) {
            event = event || window.event;
            event.preventDefault ? event.preventDefault() : event.returnValue = false;
            $(this).parent().remove();
        });
    };
})(jQuery, window, document);

+function ($) {
	  'use strict';

	  // TOOLTIP PUBLIC CLASS DEFINITION
	  // ===============================

	  var Tooltip = function (element, options) {
	    this.type       =
	    this.options    =
	    this.enabled    =
	    this.timeout    =
	    this.hoverState =
	    this.$element   = null

	    this.init('tooltip', element, options)
	  }

	  Tooltip.VERSION  = '3.3.1'

	  Tooltip.TRANSITION_DURATION = 150

	  Tooltip.DEFAULTS = {
	    animation: true,
	    placement: 'top',
	    selector: false,
	    template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
	    trigger: 'hover focus',
	    title: '',
	    delay: 0,
	    html: false,
	    container: false,
	    viewport: {
	      selector: 'body',
	      padding: 0
	    }
	  }

	  Tooltip.prototype.init = function (type, element, options) {
	    this.enabled   = true
	    this.type      = type
	    this.$element  = $(element)
	    this.options   = this.getOptions(options)
	    this.$viewport = this.options.viewport && $(this.options.viewport.selector || this.options.viewport)

	    var triggers = this.options.trigger.split(' ')

	    for (var i = triggers.length; i--;) {
	      var trigger = triggers[i]

	      if (trigger == 'click') {
	        this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this))
	      } else if (trigger != 'manual') {
	        var eventIn  = trigger == 'hover' ? 'mouseenter' : 'focusin'
	        var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout'

	        this.$element.on(eventIn  + '.' + this.type, this.options.selector, $.proxy(this.enter, this))
	        this.$element.on(eventOut + '.' + this.type, this.options.selector, $.proxy(this.leave, this))
	      }
	    }

	    this.options.selector ?
	      (this._options = $.extend({}, this.options, { trigger: 'manual', selector: '' })) :
	      this.fixTitle()
	  }

	  Tooltip.prototype.getDefaults = function () {
	    return Tooltip.DEFAULTS
	  }

	  Tooltip.prototype.getOptions = function (options) {
	    options = $.extend({}, this.getDefaults(), this.$element.data(), options)

	    if (options.delay && typeof options.delay == 'number') {
	      options.delay = {
	        show: options.delay,
	        hide: options.delay
	      }
	    }

	    return options
	  }

	  Tooltip.prototype.getDelegateOptions = function () {
	    var options  = {}
	    var defaults = this.getDefaults()

	    this._options && $.each(this._options, function (key, value) {
	      if (defaults[key] != value) options[key] = value
	    })

	    return options
	  }

	  Tooltip.prototype.enter = function (obj) {
	    var self = obj instanceof this.constructor ?
	      obj : $(obj.currentTarget).data('bs.' + this.type)

	    if (self && self.$tip && self.$tip.is(':visible')) {
	      self.hoverState = 'in'
	      return
	    }

	    if (!self) {
	      self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
	      $(obj.currentTarget).data('bs.' + this.type, self)
	    }

	    clearTimeout(self.timeout)

	    self.hoverState = 'in'

	    if (!self.options.delay || !self.options.delay.show) return self.show()

	    self.timeout = setTimeout(function () {
	      if (self.hoverState == 'in') self.show()
	    }, self.options.delay.show)
	  }

	  Tooltip.prototype.leave = function (obj) {
	    var self = obj instanceof this.constructor ?
	      obj : $(obj.currentTarget).data('bs.' + this.type)

	    if (!self) {
	      self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
	      $(obj.currentTarget).data('bs.' + this.type, self)
	    }

	    clearTimeout(self.timeout)

	    self.hoverState = 'out'

	    if (!self.options.delay || !self.options.delay.hide) return self.hide()

	    self.timeout = setTimeout(function () {
	      if (self.hoverState == 'out') self.hide()
	    }, self.options.delay.hide)
	  }

	  Tooltip.prototype.show = function () {
	    var e = $.Event('show.bs.' + this.type)

	    if (this.hasContent() && this.enabled) {
	      this.$element.trigger(e)

	      var inDom = $.contains(this.$element[0].ownerDocument.documentElement, this.$element[0])
	      if (e.isDefaultPrevented() || !inDom) return
	      var that = this

	      var $tip = this.tip()

	      var tipId = this.getUID(this.type)

	      this.setContent()
	      $tip.attr('id', tipId)
	      this.$element.attr('aria-describedby', tipId)

	      if (this.options.animation) $tip.addClass('fade')

	      var placement = typeof this.options.placement == 'function' ?
	        this.options.placement.call(this, $tip[0], this.$element[0]) :
	        this.options.placement

	      var autoToken = /\s?auto?\s?/i
	      var autoPlace = autoToken.test(placement)
	      if (autoPlace) placement = placement.replace(autoToken, '') || 'top'

	      $tip
	        .detach()
	        .css({ top: 0, left: 0, display: 'block' })
	        .addClass(placement)
	        .data('bs.' + this.type, this)

	      this.options.container ? $tip.appendTo(this.options.container) : $tip.insertAfter(this.$element)

	      var pos          = this.getPosition()
	      var actualWidth  = $tip[0].offsetWidth
	      var actualHeight = $tip[0].offsetHeight

	      if (autoPlace) {
	        var orgPlacement = placement
	        var $container   = this.options.container ? $(this.options.container) : this.$element.parent()
	        var containerDim = this.getPosition($container)

	        placement = placement == 'bottom' && pos.bottom + actualHeight > containerDim.bottom ? 'top'    :
	                    placement == 'top'    && pos.top    - actualHeight < containerDim.top    ? 'bottom' :
	                    placement == 'right'  && pos.right  + actualWidth  > containerDim.width  ? 'left'   :
	                    placement == 'left'   && pos.left   - actualWidth  < containerDim.left   ? 'right'  :
	                    placement

	        $tip
	          .removeClass(orgPlacement)
	          .addClass(placement)
	      }

	      var calculatedOffset = this.getCalculatedOffset(placement, pos, actualWidth, actualHeight)

	      this.applyPlacement(calculatedOffset, placement)

	      var complete = function () {
	        var prevHoverState = that.hoverState
	        that.$element.trigger('shown.bs.' + that.type)
	        that.hoverState = null

	        if (prevHoverState == 'out') that.leave(that)
	      }

	      $.support.transition && this.$tip.hasClass('fade') ?
	        $tip
	          .one('bsTransitionEnd', complete)
	          .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
	        complete()
	    }
	  }

	  Tooltip.prototype.applyPlacement = function (offset, placement) {
	    var $tip   = this.tip()
	    var width  = $tip[0].offsetWidth
	    var height = $tip[0].offsetHeight

	    // manually read margins because getBoundingClientRect includes difference
	    var marginTop = parseInt($tip.css('margin-top'), 10)
	    var marginLeft = parseInt($tip.css('margin-left'), 10)

	    // we must check for NaN for ie 8/9
	    if (isNaN(marginTop))  marginTop  = 0
	    if (isNaN(marginLeft)) marginLeft = 0

	    offset.top  = offset.top  + marginTop
	    offset.left = offset.left + marginLeft

	    // $.fn.offset doesn't round pixel values
	    // so we use setOffset directly with our own function B-0
	    $.offset.setOffset($tip[0], $.extend({
	      using: function (props) {
	        $tip.css({
	          top: Math.round(props.top),
	          left: Math.round(props.left)
	        })
	      }
	    }, offset), 0)

	    $tip.addClass('in')

	    // check to see if placing tip in new offset caused the tip to resize itself
	    var actualWidth  = $tip[0].offsetWidth
	    var actualHeight = $tip[0].offsetHeight

	    if (placement == 'top' && actualHeight != height) {
	      offset.top = offset.top + height - actualHeight
	    }

	    var delta = this.getViewportAdjustedDelta(placement, offset, actualWidth, actualHeight)

	    if (delta.left) offset.left += delta.left
	    else offset.top += delta.top

	    var isVertical          = /top|bottom/.test(placement)
	    var arrowDelta          = isVertical ? delta.left * 2 - width + actualWidth : delta.top * 2 - height + actualHeight
	    var arrowOffsetPosition = isVertical ? 'offsetWidth' : 'offsetHeight'

	    $tip.offset(offset)
	    this.replaceArrow(arrowDelta, $tip[0][arrowOffsetPosition], isVertical)
	  }

	  Tooltip.prototype.replaceArrow = function (delta, dimension, isHorizontal) {
	    this.arrow()
	      .css(isHorizontal ? 'left' : 'top', 50 * (1 - delta / dimension) + '%')
	      .css(isHorizontal ? 'top' : 'left', '')
	  }

	  Tooltip.prototype.setContent = function () {
	    var $tip  = this.tip()
	    var title = this.getTitle()

	    $tip.find('.tooltip-inner')[this.options.html ? 'html' : 'text'](title)
	    $tip.removeClass('fade in top bottom left right')
	  }

	  Tooltip.prototype.hide = function (callback) {
	    var that = this
	    var $tip = this.tip()
	    var e    = $.Event('hide.bs.' + this.type)

	    function complete() {
	      if (that.hoverState != 'in') $tip.detach()
	      that.$element
	        .removeAttr('aria-describedby')
	        .trigger('hidden.bs.' + that.type)
	      callback && callback()
	    }

	    this.$element.trigger(e)

	    if (e.isDefaultPrevented()) return

	    $tip.removeClass('in')

	    $.support.transition && this.$tip.hasClass('fade') ?
	      $tip
	        .one('bsTransitionEnd', complete)
	        .emulateTransitionEnd(Tooltip.TRANSITION_DURATION) :
	      complete()

	    this.hoverState = null

	    return this
	  }

	  Tooltip.prototype.fixTitle = function () {
	    var $e = this.$element
	    if ($e.attr('title') || typeof ($e.attr('data-original-title')) != 'string') {
	      $e.attr('data-original-title', $e.attr('title') || '').attr('title', '')
	    }
	  }

	  Tooltip.prototype.hasContent = function () {
	    return this.getTitle()
	  }

	  Tooltip.prototype.getPosition = function ($element) {
	    $element   = $element || this.$element

	    var el     = $element[0]
	    var isBody = el.tagName == 'BODY'

	    var elRect    = el.getBoundingClientRect()
	    if (elRect.width == null) {
	      // width and height are missing in IE8, so compute them manually; see https://github.com/twbs/bootstrap/issues/14093
	      elRect = $.extend({}, elRect, { width: elRect.right - elRect.left, height: elRect.bottom - elRect.top })
	    }
	    var elOffset  = isBody ? { top: 0, left: 0 } : $element.offset()
	    var scroll    = { scroll: isBody ? document.documentElement.scrollTop || document.body.scrollTop : $element.scrollTop() }
	    var outerDims = isBody ? { width: $(window).width(), height: $(window).height() } : null

	    return $.extend({}, elRect, scroll, outerDims, elOffset)
	  }

	  Tooltip.prototype.getCalculatedOffset = function (placement, pos, actualWidth, actualHeight) {
	    return placement == 'bottom' ? { top: pos.top + pos.height,   left: pos.left + pos.width / 2 - actualWidth / 2  } :
	           placement == 'top'    ? { top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2  } :
	           placement == 'left'   ? { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth } :
	        /* placement == 'right' */ { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width   }

	  }

	  Tooltip.prototype.getViewportAdjustedDelta = function (placement, pos, actualWidth, actualHeight) {
	    var delta = { top: 0, left: 0 }
	    if (!this.$viewport) return delta

	    var viewportPadding = this.options.viewport && this.options.viewport.padding || 0
	    var viewportDimensions = this.getPosition(this.$viewport)

	    if (/right|left/.test(placement)) {
	      var topEdgeOffset    = pos.top - viewportPadding - viewportDimensions.scroll
	      var bottomEdgeOffset = pos.top + viewportPadding - viewportDimensions.scroll + actualHeight
	      if (topEdgeOffset < viewportDimensions.top) { // top overflow
	        delta.top = viewportDimensions.top - topEdgeOffset
	      } else if (bottomEdgeOffset > viewportDimensions.top + viewportDimensions.height) { // bottom overflow
	        delta.top = viewportDimensions.top + viewportDimensions.height - bottomEdgeOffset
	      }
	    } else {
	      var leftEdgeOffset  = pos.left - viewportPadding
	      var rightEdgeOffset = pos.left + viewportPadding + actualWidth
	      if (leftEdgeOffset < viewportDimensions.left) { // left overflow
	        delta.left = viewportDimensions.left - leftEdgeOffset
	      } else if (rightEdgeOffset > viewportDimensions.width) { // right overflow
	        delta.left = viewportDimensions.left + viewportDimensions.width - rightEdgeOffset
	      }
	    }

	    return delta
	  }

	  Tooltip.prototype.getTitle = function () {
	    var title
	    var $e = this.$element
	    var o  = this.options

	    title = $e.attr('data-original-title')
	      || (typeof o.title == 'function' ? o.title.call($e[0]) :  o.title)

	    return title
	  }

	  Tooltip.prototype.getUID = function (prefix) {
	    do prefix += ~~(Math.random() * 1000000)
	    while (document.getElementById(prefix))
	    return prefix
	  }

	  Tooltip.prototype.tip = function () {
	    return (this.$tip = this.$tip || $(this.options.template))
	  }

	  Tooltip.prototype.arrow = function () {
	    return (this.$arrow = this.$arrow || this.tip().find('.tooltip-arrow'))
	  }

	  Tooltip.prototype.enable = function () {
	    this.enabled = true
	  }

	  Tooltip.prototype.disable = function () {
	    this.enabled = false
	  }

	  Tooltip.prototype.toggleEnabled = function () {
	    this.enabled = !this.enabled
	  }

	  Tooltip.prototype.toggle = function (e) {
	    var self = this
	    if (e) {
	      self = $(e.currentTarget).data('bs.' + this.type)
	      if (!self) {
	        self = new this.constructor(e.currentTarget, this.getDelegateOptions())
	        $(e.currentTarget).data('bs.' + this.type, self)
	      }
	    }

	    self.tip().hasClass('in') ? self.leave(self) : self.enter(self)
	  }

	  Tooltip.prototype.destroy = function () {
	    var that = this
	    clearTimeout(this.timeout)
	    this.hide(function () {
	      that.$element.off('.' + that.type).removeData('bs.' + that.type)
	    })
	  }


	  // TOOLTIP PLUGIN DEFINITION
	  // =========================

	  function Plugin(option) {
	    return this.each(function () {
	      var $this    = $(this)
	      var data     = $this.data('bs.tooltip')
	      var options  = typeof option == 'object' && option
	      var selector = options && options.selector

	      if (!data && option == 'destroy') return
	      if (selector) {
	        if (!data) $this.data('bs.tooltip', (data = {}))
	        if (!data[selector]) data[selector] = new Tooltip(this, options)
	      } else {
	        if (!data) $this.data('bs.tooltip', (data = new Tooltip(this, options)))
	      }
	      if (typeof option == 'string') data[option]()
	    })
	  }

	  var old = $.fn.tooltip

	  $.fn.tooltip             = Plugin
	  $.fn.tooltip.Constructor = Tooltip


	  // TOOLTIP NO CONFLICT
	  // ===================

	  $.fn.tooltip.noConflict = function () {
	    $.fn.tooltip = old
	    return this
	  }

	}(jQuery);
	
	+function ($) {
		  'use strict';

		  // POPOVER PUBLIC CLASS DEFINITION
		  // ===============================

		  var Popover = function (element, options) {
		    this.init('popover', element, options)
		  }

		  if (!$.fn.tooltip) throw new Error('Popover requires tooltip.js')

		  Popover.VERSION  = '3.3.4'

		  Popover.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
		    placement: 'right',
		    trigger: 'click',
		    content: '',
		    template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
		  })


		  // NOTE: POPOVER EXTENDS tooltip.js
		  // ================================

		  Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype)

		  Popover.prototype.constructor = Popover

		  Popover.prototype.getDefaults = function () {
		    return Popover.DEFAULTS
		  }

		  Popover.prototype.setContent = function () {
		    var $tip    = this.tip()
		    var title   = this.getTitle()
		    var content = this.getContent()

		    $tip.find('.popover-title')[this.options.html ? 'html' : 'text'](title)
		    $tip.find('.popover-content').children().detach().end()[ // we use append for html objects to maintain js events
		      this.options.html ? (typeof content == 'string' ? 'html' : 'append') : 'text'
		    ](content)

		    $tip.removeClass('fade top bottom left right in')

		    // IE8 doesn't accept hiding via the `:empty` pseudo selector, we have to do
		    // this manually by checking the contents.
		    if (!$tip.find('.popover-title').html()) $tip.find('.popover-title').hide()
		  }

		  Popover.prototype.hasContent = function () {
		    return this.getTitle() || this.getContent()
		  }

		  Popover.prototype.getContent = function () {
		    var $e = this.$element
		    var o  = this.options

		    return $e.attr('data-content')
		      || (typeof o.content == 'function' ?
		            o.content.call($e[0]) :
		            o.content)
		  }

		  Popover.prototype.arrow = function () {
		    return (this.$arrow = this.$arrow || this.tip().find('.arrow'))
		  }


		  // POPOVER PLUGIN DEFINITION
		  // =========================

		  function Plugin(option) {
		    return this.each(function () {
		      var $this   = $(this)
		      var data    = $this.data('bs.popover')
		      var options = typeof option == 'object' && option

		      if (!data && /destroy|hide/.test(option)) return
		      if (!data) $this.data('bs.popover', (data = new Popover(this, options)))
		      if (typeof option == 'string') data[option]()
		    })
		  }

		  var old = $.fn.popover

		  $.fn.popover             = Plugin
		  $.fn.popover.Constructor = Popover


		  // POPOVER NO CONFLICT
		  // ===================

		  $.fn.popover.noConflict = function () {
		    $.fn.popover = old
		    return this
		  }

		}(jQuery);

		/* ========================================================================
		 * Bootstrap: scrollspy.js v3.3.4
		 * http://getbootstrap.com/javascript/#scrollspy
		 * ========================================================================
		 * Copyright 2011-2015 Twitter, Inc.
		 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
		 * ======================================================================== */


		+function ($) {
		  'use strict';

		  // SCROLLSPY CLASS DEFINITION
		  // ==========================

		  function ScrollSpy(element, options) {
		    this.$body          = $(document.body)
		    this.$scrollElement = $(element).is(document.body) ? $(window) : $(element)
		    this.options        = $.extend({}, ScrollSpy.DEFAULTS, options)
		    this.selector       = (this.options.target || '') + ' .nav li > a'
		    this.offsets        = []
		    this.targets        = []
		    this.activeTarget   = null
		    this.scrollHeight   = 0

		    this.$scrollElement.on('scroll.bs.scrollspy', $.proxy(this.process, this))
		    this.refresh()
		    this.process()
		  }

		  ScrollSpy.VERSION  = '3.3.4'

		  ScrollSpy.DEFAULTS = {
		    offset: 10
		  }

		  ScrollSpy.prototype.getScrollHeight = function () {
		    return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight)
		  }

		  ScrollSpy.prototype.refresh = function () {
		    var that          = this
		    var offsetMethod  = 'offset'
		    var offsetBase    = 0

		    this.offsets      = []
		    this.targets      = []
		    this.scrollHeight = this.getScrollHeight()

		    if (!$.isWindow(this.$scrollElement[0])) {
		      offsetMethod = 'position'
		      offsetBase   = this.$scrollElement.scrollTop()
		    }

		    this.$body
		      .find(this.selector)
		      .map(function () {
		        var $el   = $(this)
		        var href  = $el.data('target') || $el.attr('href')
		        var $href = /^#./.test(href) && $(href)

		        return ($href
		          && $href.length
		          && $href.is(':visible')
		          && [[$href[offsetMethod]().top + offsetBase, href]]) || null
		      })
		      .sort(function (a, b) { return a[0] - b[0] })
		      .each(function () {
		        that.offsets.push(this[0])
		        that.targets.push(this[1])
		      })
		  }

		  ScrollSpy.prototype.process = function () {
		    var scrollTop    = this.$scrollElement.scrollTop() + this.options.offset
		    var scrollHeight = this.getScrollHeight()
		    var maxScroll    = this.options.offset + scrollHeight - this.$scrollElement.height()
		    var offsets      = this.offsets
		    var targets      = this.targets
		    var activeTarget = this.activeTarget
		    var i

		    if (this.scrollHeight != scrollHeight) {
		      this.refresh()
		    }

		    if (scrollTop >= maxScroll) {
		      return activeTarget != (i = targets[targets.length - 1]) && this.activate(i)
		    }

		    if (activeTarget && scrollTop < offsets[0]) {
		      this.activeTarget = null
		      return this.clear()
		    }

		    for (i = offsets.length; i--;) {
		      activeTarget != targets[i]
		        && scrollTop >= offsets[i]
		        && (offsets[i + 1] === undefined || scrollTop < offsets[i + 1])
		        && this.activate(targets[i])
		    }
		  }

		  ScrollSpy.prototype.activate = function (target) {
		    this.activeTarget = target

		    this.clear()

		    var selector = this.selector +
		      '[data-target="' + target + '"],' +
		      this.selector + '[href="' + target + '"]'

		    var active = $(selector)
		      .parents('li')
		      .addClass('active')

		    if (active.parent('.dropdown-menu').length) {
		      active = active
		        .closest('li.dropdown')
		        .addClass('active')
		    }

		    active.trigger('activate.bs.scrollspy')
		  }

		  ScrollSpy.prototype.clear = function () {
		    $(this.selector)
		      .parentsUntil(this.options.target, '.active')
		      .removeClass('active')
		  }


		  // SCROLLSPY PLUGIN DEFINITION
		  // ===========================

		  function Plugin(option) {
		    return this.each(function () {
		      var $this   = $(this)
		      var data    = $this.data('bs.scrollspy')
		      var options = typeof option == 'object' && option

		      if (!data) $this.data('bs.scrollspy', (data = new ScrollSpy(this, options)))
		      if (typeof option == 'string') data[option]()
		    })
		  }

		  var old = $.fn.scrollspy

		  $.fn.scrollspy             = Plugin
		  $.fn.scrollspy.Constructor = ScrollSpy


		  // SCROLLSPY NO CONFLICT
		  // =====================

		  $.fn.scrollspy.noConflict = function () {
		    $.fn.scrollspy = old
		    return this
		  }


		  // SCROLLSPY DATA-API
		  // ==================

		  $(window).on('load.bs.scrollspy.data-api', function () {
		    $('[data-spy="scroll"]').each(function () {
		      var $spy = $(this)
		      Plugin.call($spy, $spy.data())
		    })
		  })

		}(jQuery);
		
		/* ========================================================================
		 * Bootstrap: dropdown.js v3.3.4
		 * http://getbootstrap.com/javascript/#dropdowns
		 * ========================================================================
		 * Copyright 2011-2015 Twitter, Inc.
		 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
		 * ======================================================================== */


		+function ($) {
		  'use strict';

		  // DROPDOWN CLASS DEFINITION
		  // =========================

		  var backdrop = '.dropdown-backdrop'
		  var toggle   = '[data-toggle="dropdown"]'
		  var Dropdown = function (element) {
		    $(element).on('click.bs.dropdown', this.toggle)
		  }

		  Dropdown.VERSION = '3.3.4'

		  Dropdown.prototype.toggle = function (e) {
		    var $this = $(this)

		    if ($this.is('.disabled, :disabled')) return

		    var $parent  = getParent($this)
		    var isActive = $parent.hasClass('open')

		    clearMenus()

		    if (!isActive) {
		      if ('ontouchstart' in document.documentElement && !$parent.closest('.navbar-nav').length) {
		        // if mobile we use a backdrop because click events don't delegate
		        $('<div class="dropdown-backdrop"/>').insertAfter($(this)).on('click', clearMenus)
		      }

		      var relatedTarget = { relatedTarget: this }
		      $parent.trigger(e = $.Event('show.bs.dropdown', relatedTarget))

		      if (e.isDefaultPrevented()) return

		      $this
		        .trigger('focus')
		        .attr('aria-expanded', 'true')

		      $parent
		        .toggleClass('open')
		        .trigger('shown.bs.dropdown', relatedTarget)
		    }

		    return false
		  }

		  Dropdown.prototype.keydown = function (e) {
		    if (!/(38|40|27|32)/.test(e.which) || /input|textarea/i.test(e.target.tagName)) return

		    var $this = $(this)

		    e.preventDefault()
		    e.stopPropagation()

		    if ($this.is('.disabled, :disabled')) return

		    var $parent  = getParent($this)
		    var isActive = $parent.hasClass('open')

		    if ((!isActive && e.which != 27) || (isActive && e.which == 27)) {
		      if (e.which == 27) $parent.find(toggle).trigger('focus')
		      return $this.trigger('click')
		    }

		    var desc = ' li:not(.disabled):visible a'
		    var $items = $parent.find('[role="menu"]' + desc + ', [role="listbox"]' + desc)

		    if (!$items.length) return

		    var index = $items.index(e.target)

		    if (e.which == 38 && index > 0)                 index--                        // up
		    if (e.which == 40 && index < $items.length - 1) index++                        // down
		    if (!~index)                                      index = 0

		    $items.eq(index).trigger('focus')
		  }

		  function clearMenus(e) {
		    if (e && e.which === 3) return
		    $(backdrop).remove()
		    $(toggle).each(function () {
		      var $this         = $(this)
		      var $parent       = getParent($this)
		      var relatedTarget = { relatedTarget: this }

		      if (!$parent.hasClass('open')) return

		      $parent.trigger(e = $.Event('hide.bs.dropdown', relatedTarget))

		      if (e.isDefaultPrevented()) return

		      $this.attr('aria-expanded', 'false')
		      $parent.removeClass('open').trigger('hidden.bs.dropdown', relatedTarget)
		    })
		  }

		  function getParent($this) {
		    var selector = $this.attr('data-target')

		    if (!selector) {
		      selector = $this.attr('href')
		      selector = selector && /#[A-Za-z]/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
		    }

		    var $parent = selector && $(selector)

		    return $parent && $parent.length ? $parent : $this.parent()
		  }


		  // DROPDOWN PLUGIN DEFINITION
		  // ==========================

		  function Plugin(option) {
		    return this.each(function () {
		      var $this = $(this)
		      var data  = $this.data('bs.dropdown')

		      if (!data) $this.data('bs.dropdown', (data = new Dropdown(this)))
		      if (typeof option == 'string') data[option].call($this)
		    })
		  }

		  var old = $.fn.dropdown

		  $.fn.dropdown             = Plugin
		  $.fn.dropdown.Constructor = Dropdown


		  // DROPDOWN NO CONFLICT
		  // ====================

		  $.fn.dropdown.noConflict = function () {
		    $.fn.dropdown = old
		    return this
		  }


		  // APPLY TO STANDARD DROPDOWN ELEMENTS
		  // ===================================

		  $(document)
		    .on('click.bs.dropdown.data-api', clearMenus)
		    .on('click.bs.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() })
		    .on('click.bs.dropdown.data-api', toggle, Dropdown.prototype.toggle)
		    .on('keydown.bs.dropdown.data-api', toggle, Dropdown.prototype.keydown)
		    .on('keydown.bs.dropdown.data-api', '[role="menu"]', Dropdown.prototype.keydown)
		    .on('keydown.bs.dropdown.data-api', '[role="listbox"]', Dropdown.prototype.keydown)

		}(jQuery);

	window.pushParamsToUrl = function(json) {
		console.log(json);
    	var a = document.createElement('a'), q=[];
    	a.href = window.location.href;
    	for (var i in json) {
    		var v = json[i];
    		if (v) {
        		if (typeof(v)=='array') {
    	        	v = v.join(',');
    	        }
    		}
    		q.push(i + "=" + encodeURIComponent(v));
    	}
    	a.search = q.join('&');
        var pathname = (a.pathname.charAt(0) == '/' ) ? a.pathname : "/" + a.pathname;
        window.history.pushState('', '', pathname + a.search);
	}
	
	
	if (typeof(Tapestry) != 'undefined') {
    	Tapestry.ElementEffect.highlight = function(element){ 
			jQuery(element).css('opacity', 0).animate({'opacity': 1}, 500); 
		}
	}
	
