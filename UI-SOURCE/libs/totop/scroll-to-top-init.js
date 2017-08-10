$(function() {
    // bottom scrollTop
    $.fn.scrollBottom = function() {
        return $(document).height() - this.scrollTop() - this.height();
    };
    var $elem = $('body, html'),
        $arrowsContainer = $('.scroll-page-nav'),
        $showNav = $('#wrapp').offset().top;
    $(window).scroll(function() {
        if ($(this).scrollTop() > $showNav ) {
            $arrowsContainer.addClass('is-visible');                        
        } else{
            $arrowsContainer.removeClass('is-visible');
        }
    });
    $(window).scroll(function() {
        if ($(window).scrollBottom() < 360) {
            $('.nav_down').hide();
        } else {
            $('.nav_down').show();
        }
    });
    $(window).bind('scrollstart', function(){
        $arrowsContainer.css({visibility: "visible"}).animate({opacity: 1}, 200);
    });
    $(window).bind('scrollstop', function(){                    
        setTimeout(function() {
            $arrowsContainer.css({visibility: "hidden"}).animate({opacity: 0}, 200);
        }, 5000);
    });
    $('.nav_down').click(
        function (e) {
            $elem.animate({scrollTop: $elem.height()}, 800);
        }
    );
    $('.nav_up').click(
        function (e) {
            $elem.animate({scrollTop: '0px'}, 800);
        }
    );
});