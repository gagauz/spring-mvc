(function($){
    var slider = new Slider('#slider', { min: tutor.min, max: tutor.max}), $sliderMin = $("#priceMin"),$sliderMax = $("#priceMax");
    slider.on("slide", function(slideEvt) {
        $sliderMin.val(slideEvt[0]);
        $sliderMax.val(slideEvt[1]);
    });
})(jQuery);
