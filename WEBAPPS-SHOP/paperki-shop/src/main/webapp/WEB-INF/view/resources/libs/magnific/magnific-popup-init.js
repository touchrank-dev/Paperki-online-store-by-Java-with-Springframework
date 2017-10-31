$(document).ready(function() {
    $('.add-delivery-address-button').magnificPopup({
        type:'inline',
        removalDelay: 400,
        midClick: true
    });

    $('.update-profile-popup').magnificPopup({
        type:'inline',
        removalDelay: 400,
        midClick: true
    });

    $('#open-add-enterprise-popup').magnificPopup({
        type:'inline',
        removalDelay: 400,
        midClick: true
    });

    $('.open-reg-popup').magnificPopup({
        type:'inline',
        removalDelay: 400,
        midClick: true
    });	

    $('.open-password-recovery-popup').magnificPopup({
        type:'inline',
        removalDelay: 400,
        midClick: true // Allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source in href.
        // other options
    });	 
    $('.open-morph').click(function(e){
        $.magnificPopup.close();
        e.preventDefault();
    });
    //    single product popup
    $('.user-gallery__photos__list').each(function() { // the containers for all your galleries
        $(this).magnificPopup({
            delegate: 'a', // the selector for gallery item
            type: 'image',
            gallery: {
              enabled:true,
              tCounter: '<span class="mfp-counter">%curr% с %total%</span>' // markup of counter
            }
        });
    });
    $('.write-review').magnificPopup({
        type:'inline',
        midClick: true // Allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source in href.
        // other options
    });
    $('.saw-other-price').magnificPopup({
        type:'inline',
        midClick: true // Allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source in href.
        // other options
    });
    $('.send-description__btn').magnificPopup({
        type:'inline',
        midClick: true // Allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source in href.
        // other options
    });
    $('.find-mistake__btn').magnificPopup({
        type:'inline',
        midClick: true // Allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source in href.
        // other options
    });

    // cart page
    $('.btn-ea-c').magnificPopup({
        type:'inline',
        midClick: true,
        callbacks: {
            open: function () {
                $(window).trigger('resize');
            }
        }
    });
    $('.cart-popup-reg__link').magnificPopup({
        type:'inline',
        midClick: true // Allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source in href.
        // other options
    });

    // profile page
    $('.user-gallery__videos__list').each(function() { // the containers for all your galleries
        $(this).magnificPopup({
            delegate: 'a', // the selector for gallery item
            type: 'iframe',
            gallery: {
                enabled:true,
                tCounter: '<span class="mfp-counter">%curr% с %total%</span>' // markup of counter
            }
        });
    });

    // search popup
    $( ".search-inp" ).keyup(function( e ) {
        $.magnificPopup.open({
            items: {
                src: '#search-popup'
            },
            type: 'inline',
            preloader: false,
            focus: '#search-popup-input'
        });
        var descValue = $('.search-inp').val(),
            popupValue = $('.search-popup-input'),
            searchPopHead = $('.search-popup-content-top').height(),
            searchPopBody = $('.drop-search-catalog');
        searchPopBody.css({ 'margin-top': (searchPopHead) + 'px'});
        popupValue.val(descValue);
    });
    $( ".search-popup-input" ).keyup(function( e ) {
        var descValue = $('.search-inp'),
            popupValue = $(this).val(),
            searchPopHead = $('.search-popup-content-top').height(),
            searchPopBody = $('.drop-search-catalog');
        searchPopBody.css({ 'margin-top': (searchPopHead) + 'px'});
        descValue.val(popupValue);
    });
    $(window).resize(function(){
        if ($(window).width() > 992) {
            var searchPopup = ($(window).height() - 70);
            $('.search-popup-content').height(searchPopup);
        }
        else {
            var searchPopup = ($(window).height());
            $('.search-popup-content').height(searchPopup);
        }
    });

    // feedback popup
    $('.feedback-page-write-feedback-btn').magnificPopup({
        type:'inline',
        midClick: true
    });

});