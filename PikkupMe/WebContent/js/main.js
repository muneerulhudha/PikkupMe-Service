/**
 * Created by pratima on 3/26/2016.
 */
jQuery(document).ready(function($) {

    $('.s-icons').hover(function() {
        var icon  = $(this).find('.fa');
        icon.css('color', '#54B49F');
    }, function() {
        var icon  = $(this).find('.fa');
        icon.css('color', '#BDCBCB');
    });
});