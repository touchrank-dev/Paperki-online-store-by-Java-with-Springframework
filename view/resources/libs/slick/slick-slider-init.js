$(document).ready(function(){            
    //    slick slider element
    var slickSlider = $('.main-carousel'),
        slickSliderNav = $('.slick-thumbs-list'),
        singleProdMain = $('.single-product-main-pict'),
        singleProdThumb = $('.single-product-pict-slider-thumbs-list');              

    slickSlider.slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        asNavFor: slickSliderNav,
        autoplay: true,
        autoplaySpeed: 6000,
    });
    slickSliderNav.slick({
        slidesToShow: 9,
        slidesToScroll: 1,
        asNavFor: slickSlider,
        dots: false,
        arrows: false,
        focusOnSelect: true,
        responsive: [
            {
              breakpoint: 1920,
              settings: {
                slidesToShow: 9,
                slidesToScroll: 1,
                asNavFor: slickSlider,
                dots: false,
                arrows: false,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 1530,
              settings: {
                slidesToShow: 5,
                slidesToScroll: 1,
                asNavFor: slickSlider,
                dots: false,
                arrows: false,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 1000,
              settings: {
                slidesToShow: 3,
                slidesToScroll: 1,
                asNavFor: slickSlider,
                dots: false,
                arrows: false,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 480,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 1,
                asNavFor: slickSlider,
                dots: false,
                arrows: false,
                focusOnSelect: true,
              }
            },
          ]
    });            
    
    $('.brands-slider-content').slick({
        slidesToShow: 10,
        dots: false,
        arrows: false,
        focusOnSelect: false,
        infinite: true,
        autoplay: true,
        autoplaySpeed: 5500,
        responsive: [
            {
              breakpoint: 1920,
              settings: {
                slidesToShow: 10,
                dots: false,
                arrows: false,
                focusOnSelect: false,
                infinite: true,
              }
            },
            {
              breakpoint: 1360,
              settings: {
                slidesToShow: 9,
                dots: false,
                arrows: false,
                focusOnSelect: false,
                infinite: true,
              }
            },
            {
              breakpoint: 1200,
              settings: {
                slidesToShow: 7,
                slidesToScroll: 3,
                dots: false,
                arrows: false,
                focusOnSelect: false,
                infinite: true,
              }
            },
            {
              breakpoint: 992,
              settings: {
                slidesToShow: 5,
                slidesToScroll: 3,
                dots: false,
                arrows: false,
                focusOnSelect: false,
                infinite: true,
              }
            },
            {
              breakpoint: 768,
              settings: {
                slidesToShow: 4,
                slidesToScroll: 2,
                dots: false,
                focusOnSelect: false,
                infinite: true,
                arrows: false,
              }
            },
            {
              breakpoint: 480,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
                dots: false,
                arrows: false,
                focusOnSelect: false,
                infinite: true,
              }
            },

          ]
    }); 
    
    var $articles_slider = $('.articles-slider'),              
          settings = {
            slidesToShow: 1,
            slidesToScroll: 1,
            dots: true,
            arrows: false,
            focusOnSelect: false,
            infinite: false,  
          };
      $articles_slider.slick(settings);
      // reslick only if it's not slick()
      $(window).on('resize', function() {
        if ($(window).width() > 991) {
          if ($articles_slider.hasClass('slick-initialized')) {
            $articles_slider.slick('unslick');
          }
          return
        }

        if (!$articles_slider.hasClass('slick-initialized')) {
          return $articles_slider.slick(settings);
        }
      });
    $(window).resize();
    
//    product list page
    $('.products-list-page__products-type').slick({
        slidesToShow: 5,
        slidesToScroll: 1,
        dots: false,
        arrows: true,
        focusOnSelect: true,
        responsive: [
            {
              breakpoint: 1920,
              settings: {
                slidesToShow: 5,
                slidesToScroll: 1,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 1400,
              settings: {
                slidesToShow: 4,
                slidesToScroll: 1,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 1200,
              settings: {
                slidesToShow: 3,
                slidesToScroll: 1,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 992,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 1,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 768,
              settings: {
                slidesToShow: 1,
                slidesToScroll: 1,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            }
          ]
    }); 
    
//    single-product page
    singleProdMain.slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        asNavFor: singleProdThumb
    });
    singleProdThumb.slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        asNavFor: singleProdMain,
        dots: false,
        arrows: true,
        focusOnSelect: true,
        responsive: [
            {
              breakpoint: 1590,
              settings: {
                slidesToShow: 3,
                slidesToScroll: 1,
                asNavFor: singleProdMain,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 1390,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 1,
                asNavFor: singleProdMain,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 1200,
              settings: {
                slidesToShow: 3,
                slidesToScroll: 1,
                asNavFor: singleProdMain,
                dots: false,
                arrows: true,
                focusOnSelect: true,
              }
            },
            {
              breakpoint: 480,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 1,
                asNavFor: singleProdMain,
                dots: false,
                arrows: false,
                focusOnSelect: true,
              }
            },
          ]
    });

    // cart page
    $('.also-recommend-slider').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        dots: false,
        arrows: true,
        focusOnSelect: false,
        variableWidth: true,
        infinite: false,
        responsive: [
            {
                breakpoint: 1040,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1,
                    dots: false,
                    arrows: true,
                    focusOnSelect: false,
                    variableWidth: false,
                }
            },
            {
                breakpoint: 992,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1,
                    dots: false,
                    arrows: true,
                    focusOnSelect: false,
                    variableWidth: false,
                }
            },
            {
                breakpoint: 650,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    dots: false,
                    arrows: false,
                    focusOnSelect: false,
                    variableWidth: false,
                }
            },
        ]
    });

    // cart page added prod
    $('.together-cheaper-slider-product__pict-slider').each(function () {
        var togetherCheap = $(this),
            sliderStatus = togetherCheap.siblings('.pagingInfo');
        togetherCheap.on('init afterChange swipe', function(event, slick){           
            var activeSlide = togetherCheap.find(".slick-current").attr('data-slick-index'),
                slidesNumber = slick.slideCount;  
            if(slidesNumber == 1){
                sliderStatus.hide();
            }
            sliderStatus.text((parseInt(activeSlide) + 1) + ' из ' + slidesNumber);
        });        
        togetherCheap.slick({
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: true,
            infinite: false,
            swipe: false,
            vertical: true,
        });        
    });
    $('.cart-added-popup-together-cheaper-slider').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: true,
        dots: true,
        infinite: false
    });   
    
//    cart empty page
    $('.cart-empty-popup-action-slider').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        arrows: true,
        dots: true,
        infinite: true,
        responsive: [
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1,
                    arrows: true,
                    dots: true,
                    infinite: true,
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    arrows: true,
                    dots: true,
                    infinite: true,
                    adaptiveHeight: true
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    arrows: false,
                    dots: true,
                    infinite: true,
                    adaptiveHeight: true
                }
            }
        ]
    });
    $('.watched-products-slider').slick({
        slidesToShow: 2,
        slidesToScroll: 1,
        arrows: true,
        dots: false,
        infinite: true,
        responsive: [
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    arrows: true,
                    dots: false,
                    infinite: true,
                }
            },
        ]
     });

    //postPage
   $('.blogPostSlider').slick({
     dots: true,
     arrows: false,
   speed: 700,
   });
});
