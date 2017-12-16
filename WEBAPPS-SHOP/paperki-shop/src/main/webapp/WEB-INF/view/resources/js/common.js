$(function() {
//    avatar menu    
    $ (".login-user-button").click(function(e){        
        $(this).toggleClass('js-active');        
        $('.login-user-menu').toggleClass('is-visible');
        $('#wrapp').toggleClass('js-active');
        $('.footer').toggleClass('js-active');
        $('header').toggleClass('js-active');
        $('.dropdown.cart').toggleClass('js-active');
        e.preventDefault();
    });
    //product hover
    /*$ (".al-ph").mouseover(function(e){
        $(this).closest('.product-content').siblings('.block').addClass('active');
        e.preventDefault();
    });
    $ (".thumbnail").mouseleave(function(e){
        $(this).children('.block').removeClass('active');
        e.preventDefault();
    });*/
    $("li.add-btn").mouseover(function(e){
        $(this).children("div.add-area").css('display', 'block');
        e.preventDefault();
    });
    $(".add-area").mouseleave(function(e){
        $(this).css('display', 'none');
        e.preventDefault();
    });

    //recommend goods tabs
    $ (".box-bl").click(function(){
        $('.box-bl').removeClass('active');
        $(this).addClass('active');
        $('.recom-text').removeClass('active');
        $(this).find('.recom-text').addClass('active');
    });
    //Catalog
    $ ("#catalog-button").click(function(e){
        $('.products-catalog').addClass('js-active');        
        $('.catalog_overlay').addClass('js-active');         
        $('.scroll-page-nav').addClass('js-active');
        e.preventDefault();
    });    
    $ (".menu__level[data-menu='main'] .menu__link").click(function(e){
        $(this).parent().addClass('js-active');
        $(this).parent().siblings().removeClass('js-active');
        $('.menu').addClass('js-next');
        e.preventDefault();
    });
    $ (".menu__breadcrumbs a").click(function(e){
        $('.menu').removeClass('js-next');
        e.preventDefault();
    });    
    $(".catalog_overlay").click(function(e){
        $(this).removeClass('js-active');
        $('.scroll-page-nav').removeClass('js-active');
        $('.products-catalog').removeClass('js-active');
    });    
//    close button catalog
    $ (".action--close").click(function(e){
        $('.products-catalog').removeClass('js-active');
        $('.catalog_overlay').removeClass('js-active');
        $('.scroll-page-nav').removeClass('js-active');
        e.preventDefault();
    });
//    esc button catalog
    $(document).keyup(function(e) {
        if (e.keyCode == 27) { 
            $('.products-catalog').removeClass('js-active');            
            $(".catalog_overlay").removeClass('js-active');
            $('.scroll-page-nav').removeClass('js-active');
        }
    });
    $(window).resize(function(){
        if (document.documentElement.clientWidth < 992) {
            var catalogMenuH = ($(window).height() - 141);
            $('.menu').height(catalogMenuH);
        };
    });
    $(window).resize();
    //catalog   

    // products list page
//    filter button open/close
    $ (".mobile-filter-button").click(function(e){
        $(this).addClass('is-hidden');
        $('.products-list-page__filter').addClass('js-active');
        $.scrollTo($("header"), 800, {
            offset: 0
        });
        e.preventDefault();
    });
    $(document).mouseup(function (e) {
        var container = $(".products-list-page__filter");
        if (container.has(e.target).length === 0){
            container.removeClass('js-active');
            $(".mobile-filter-button").removeClass('is-hidden');
        }
    });
    // close mobile filter
    $ (".filter-mobile-action__close").click(function(e){
        $('.products-list-page__filter').removeClass('js-active');
        $(".mobile-filter-button").removeClass('is-hidden');
        e.preventDefault();
    });
    //number input
    $ (".inc").click(function(){
        $(this).parent().find(".inde").val(function(i, val) {
            return +val + 1;
        });
    });
    $ (".dec").click(function(){
        $(this).parent().find(".inde").val(function(i, val) {
            return +val - 1;
        });
    });
    $ (".dec").click(function(){
        if ($('.inde').val() < 0 ) {
            $('.inde').val(0);
        }
    });
    $ (".form-control.search-inp").focusin(function(){
        $(".input-group.hgt-in").addClass("orange");
    });
    $ (".form-control.search-inp").focusout(function(){
        $(".input-group.hgt-in").removeClass("orange");
    });
    $ (".search-popup-input").focusin(function(){
        $(".input-group.hgt-in").addClass("orange");
    });
    $ (".search-popup-input").focusout(function(){
        $(".input-group.hgt-in").removeClass("orange");
    });
    $(".phone").mask("+375(99) 999-99-99");
    $("#datepicker").datepicker({
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель',
            'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь',
            'Октябрь', 'Ноябрь', 'Декабрь'
        ],
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        firstDay: 1,
        dateFormat: "yy-mm-dd"
    });  
//    show/hide password
    $ (".show-password").click(function(e){
        $(this).toggleClass('js-show');
        ShowHidePassword('registration-input-password');      
    });
    function ShowHidePassword(id) {
        element = $('#' + id);
        element.replaceWith(element.clone().attr('type', (element.attr('type') == 'password') ? 'text' : 'password'));
    }
//    single product page
	$(".single-product-tabs").lightTabs();
    //    tabs nav2    
    $(".tabs-top-nav li").click(function(e){        
        var self   = $(this),
        index  = self.index();        
        self.addClass('js-active');
        self.siblings().removeClass('js-active');        
        $('.single-product-tabs__titles').children().eq(index).addClass('active');
        $('.single-product-tabs__titles').children().eq(index).siblings().removeClass('active');
        $('.single-product-tabs-content').children().eq(index).show();
        $('.single-product-tabs-content').children().eq(index).siblings().hide();
        e.preventDefault();
    });
    $(".tabs-top-nav__link").click(function() {
        $.scrollTo($(".single-product-tabs"), 800, {
            offset: -155
        });
    });
    $(".single-product-tabs__titles li").click(function(e){        
        var self   = $(this),
        index  = self.index();             
        $('.tabs-top-nav').children().eq(index).addClass('js-active');
        $('.tabs-top-nav').children().eq(index).siblings().removeClass('js-active');
        e.preventDefault();
    }); 
    $('.tooltip-btn').click(function(e){
        $(this).siblings('.tooltip-info').addClass('is-visible');
        e.preventDefault();
    });
    $('.tooltip-close').click(function(e){
        $(this).parent('.tooltip-info').removeClass('is-visible');
        e.preventDefault();
    });    
    $('.show-more-photos').click(function(e){
        $(this).addClass('is-hidden');
        $(this).siblings('.user-gallery__photos__list').addClass('js-active');
        e.preventDefault();
    });
    //    with product buy    
    $(".buy-with-product-wrap").lightTabs();
//    single product page end

    // cart page
    $('.cart-popup-del-prod').click(function(e){
        $('.cart-popup-del-prod').removeClass('js-active');
        $(this).addClass('js-active');
        $('.cart-popup-del-prod-confirm').removeClass('is-visible');
        $(this).siblings('.cart-popup-del-prod-confirm').addClass('is-visible');
        e.preventDefault();
    });
    $('.cart-popup-del__cancel').click(function(e){
        $('.cart-popup-del-prod').removeClass('js-active');
        $('.cart-popup-del-prod-confirm').removeClass('is-visible');
        $('.product-add-popup-confirm').removeClass('is-visible');
        e.preventDefault();
    });
    // cart page end
    
//    profile page
    $(".profile-page-content").lightTabs();
    $(".my-orders-content").lightTabs();
    $(".my-buying-list-content").lightTabs();
    $(".my-reviews-list").lightTabs();
    $(".my-prod-photo-list").lightTabs();
    $(".my-video-reviews-list").lightTabs();
    $('.show-more-videos').click(function(e){
        $(this).addClass('is-hidden');
        $(this).siblings('.user-gallery__videos__list').addClass('js-active');
        e.preventDefault();
    });
    // prifle tabs nav 2
    $(".my-profile-links li").click(function(e){
        var self   = $(this),
            index  = self.index();
        console.log(index);
        self.addClass('js-active');
        self.siblings().removeClass('js-active');
        $('.profile-tabs-titles').children().eq(index).addClass('active');
        $('.profile-tabs-titles').children().eq(index).siblings().removeClass('active');
        $('.profile-tabs-content').children().eq(index).show();
        $('.profile-tabs-content').children().eq(index).siblings().hide();
        e.preventDefault();
    });
    $(".back-to-personal-profile").click(function(e){
        $('.profile-tabs-titles >li:first-child').addClass('active');
        $('.profile-tabs-titles >li:first-child').siblings().removeClass('active');
        $('.profile-tabs-content >div:first-child').show();
        $('.profile-tabs-content >div:first-child').siblings().hide();
        e.preventDefault();
    });
    $('.notification-turn-on-succ__close').click(function (e) {
        $(this).parent().addClass('is-hidden');
        $('.notification-turn-off').addClass('js-active');
        e.preventDefault();
    });
    $('.notification-turn-off__close').click(function (e) {
        $(this).parent().removeClass('js-active');
        $('.notification-turn-on-succ__close').parent().removeClass('is-hidden');
        e.preventDefault();
    });

    // contacts page
    $(".contacts-tabs").lightTabs();

    // feedback page
    $('.feedback-tabs').lightTabs();
});

function openTab(tabName) {
    var i;
    var x = document.getElementsByClassName("tab");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";  
    }
    document.getElementById(tabName).style.display = "block";  
}

$(document).ready(function() {
    
//Header nav wrap letters
    var letterWrap = $('.nav-mainMenu li a');        
    letterWrap.each(function (index) {
        var characters = $(this).text().split("");
        $this = $(this);
        $this.empty();
        $.each(characters, function (i, el) {
            $this.append("<span>" + el + "</span>");
        });
    });    
//    product catalog stick to top
    var objToStick = $(".products-catalog"); //Получаем нужный объект
    var topOfObjToStick = $(objToStick).offset().top; //Получаем начальное расположение нашего блока
    $(window).scroll(function () {
        var windowScroll = $(window).scrollTop(); //Получаем величину, показывающую на сколько прокручено окно
        if (windowScroll > topOfObjToStick) { // Если прокрутили больше, чем расстояние до блока, то приклеиваем его
            $(objToStick).addClass("topWindow");
            $('.catalog-search').addClass("stickToTop");
            $('header').addClass('is-fixed');
            $('.fixed-basket').show(100);
        } else {
            $(objToStick).removeClass("topWindow");
            $('.catalog-search').removeClass("stickToTop");
            $('header').removeClass('is-fixed');
            $('.fixed-basket').hide(100);
        };
    });

    // total price fixed top
    // var objToStick1 = $(".orderingPage"); //Получаем нужный объект
    // var topOfObjToStick1 = $(objToStick1).offset().top; //Получаем начальное расположение нашего блока
    // $(window).scroll(function () {
    //     var windowScroll = $(window).scrollTop(); //Получаем величину, показывающую на сколько прокручено окно
    //     if (windowScroll > topOfObjToStick1) { // Если прокрутили больше, чем расстояние до блока, то приклеиваем его
    //         $('.orderingSidebar').addClass('orderfix');
    //     } else {
    //         $('.orderingSidebar').removeClass('orderfix');
    //     }
    // });
    if (document.documentElement.clientWidth > 1200) {
        $('.orderBlock-right').theiaStickySidebar({
            // setting
            additionalMarginTop: 55
        });
    }

//mainPagefooter#accordionButtons
  var collapseLi=$('#accordion #collapseOne .panel-body ul li'),
      halfСollapseLiHeight,
      accordionControls=$('#accordion h4.panel-title a'),
      accordionControlButtons=$('#accordion h4.panel-title a span'),
      accordionControlButtonHeight,
      accordionUls=$('#accordion .panel-collapse.collapse'),
      heightsStorage={};
   if(collapseLi[0].offsetHeight!=0){ 
     halfСollapseLiHeight=(+(collapseLi[0].offsetHeight)+(+collapseLi.css('margin-bottom').slice(0,-2)))/2;
     accordionControlButtonHeight=accordionControlButtons[0].offsetHeight;
   } else {
     halfСollapseLiHeight=(+($('.bl-mob ul.nav-foot-1 li')[0].offsetHeight)+(+collapseLi.css('margin-bottom').slice(0,-2)))/2;
     accordionControlButtonHeight=30;
   }
   for (var i = 0; i < accordionUls.length; i++) {
      heightsStorage[accordionUls[i].id]=$(accordionUls[i]).find('.panel-body ul li').length*halfСollapseLiHeight+accordionControlButtonHeight+'px';
   }
  $(accordionControlButtons[0]).css({'top': heightsStorage['collapseOne']});
  function accordionPlay(that){
    for (var i = 0; i < accordionControlButtons.length; i++) {
          if ($(accordionControlButtons[i]).css('top')!='1px') {
            $(accordionControlButtons[i]).animate({
             'top': 0
            }, 300);
          }
    }
    if ($(that).hasClass('collapsed')) {        
      $(that).find('span').animate({
          'top': heightsStorage['collapse'+that.parentElement.parentElement.id.slice(7)]
      }, 300).dequeue();
    }
    setTimeout(accordionState,300);
  }
  function accordionState(){
   accordionControls.click(function(){
      accordionControls.unbind('click');
   		accordionPlay(this);
   });
  }
  accordionState();
   //mainPageCart
  var  cart=$('.Mydropdown-menu.menu-cart'),
       cartControl=$('.dropdown.cart a.dropdown-toggle, .closeCart, .popup-close-cart');
  cartControl.click(function(){
    var cartElements=cart.find('.cart-cont > li'); 
    if(cart.css('display')=='none'){
      if(window.innerWidth>=992){
        cart.css({'display':'block', 'width' : 0, 'height': 0});
        cart.animate({
          'width': '420px',
          'height': '514px'
        },500);
        cartElements.css({'left':'100%'});
        for (var i = 0; i < cartElements.length; i++) {
        	$(cartElements[i]).delay(200+i*100).animate({
        		'left':'0'
        	},500, function(){$('.closeCart').removeAttr('style')});
        }
      } else {
        var footer=+document.querySelector('.nav.navbar-nav.navbar-right.nav-right-mn').offsetHeight,
            catalogBlock=$('.catalog-search'),
            CatalogHeight=+catalogBlock[0].offsetHeight,
            cartHeight=514;
            if(!catalogBlock.hasClass('stickToTop')) CatalogHeight=0;            
            var bottom=+(window.innerHeight)-(cartHeight+CatalogHeight),
                line=bottom-footer;
            if(line<0){
                cartHeight=cartHeight+line;
                bottom=+(window.innerHeight)-(cartHeight+CatalogHeight);
            }
          cartElements.css({'left':'0'});
          cart.css({'display':'block','width' : window.innerWidth+'px', 'height': cartHeight+'px', 'right':0, 'bottom': bottom+'px'});
      	  $('.closeCart').removeAttr('style');
      }
    } else{
    	if(window.innerWidth>=992){
	      $('.closeCart').css('display','none');
	      for (var i = cartElements.length-1; i >=0 ; i--) {
	        $(cartElements[i]).delay((cartElements.length-i)*100).animate({
	          'left':'100%'
	        },300);
	      }
	      cart.delay(cartElements.length*100).animate({
	            'width': '0',
	            'height': '0'
	          },500, function(){cart.css('display','none')});
		} else{
			$('.closeCart').css('display','none');
			cart.css('display','none');
		}
    }
  });

  //blogItemsHeight, helpColumnsHeight
  var blogItems=$('.blogPreviewHolderHeight'),
      helpCols=$('.helpColHeight');
  blogItems.equalHeights();
  if(window.innerWidth>546) helpCols.equalHeights();

  var SectionPageSmallItems=$('.productsSectionItemSmall'),
      productsSectionItemSmallText=$('.productsSectionItemSmallText');

  window.onresize=function(){
    cart.removeAttr('style');
    equalHeightsRow(SectionPageSmallItems, productsSectionItemSmallText);
    
    if(blogItems){
        blogItems.removeAttr('style');
        blogItems.equalHeights();   
    };
    if(helpCols){
        helpCols.removeAttr('style');
        if(window.innerWidth>546) helpCols.equalHeights();   
    }; 
  };

 equalHeightsRow(SectionPageSmallItems, productsSectionItemSmallText);

  function equalHeightsRow(colection, textBlock){
    var MaxH=0;
    colection.css('height', 'auto');
    textBlock.css('height', 'auto');
    for (var i = 0; i < colection.length; i++) {
      if (MaxH<colection[i].clientHeight) {
          MaxH=colection[i].clientHeight;
      }   
    }
    colection.css('height', MaxH+'px');
    textBlock.css('height', MaxH-2+'px');
  };   
    
    //sliderViewedItems
    $('.viewedItemsCarousel').slick({
        slidesToShow:3,
        prevArrow:'<button type="button" class="slick-prev"></button>',
        nextArrow: '<button type="button" class="slick-next"></button>',
        responsive: [
        {
            breakpoint: 1390,
            settings: {
                slidesToShow: 2
            }
        },
        {
            breakpoint: 768,
            settings: {
                slidesToShow: 1
            }
        }
        ]
     });

    //catalogItemsTitleHover
    var catalogHeaderLinks=$('.catalogItem .catalogHeader a');
    if (catalogHeaderLinks){
        catalogHeaderLinks.mouseover(function(){
            this.parentElement.parentElement.style.borderColor="#FFD148";
        });
        catalogHeaderLinks.mouseleave(function(){
            this.parentElement.parentElement.style.borderColor="#E0E0E0";
        });
    };
    //sectionPageCarousel
    $('.sectionPageCarousel').slick({
        slidesToShow:6,
        infinite: true,
        swipeToSlide:true,
        prevArrow:'<button type="button" class="slick-prev"></button>',
        nextArrow: '<button type="button" class="slick-next"></button>',
        responsive: [{
            breakpoint: 1590,
            settings: {
                slidesToShow: 5
            }
        },{
            breakpoint: 1390,
            settings: {
                slidesToShow: 4
            }
        },{
            breakpoint: 1200,
            settings: {
                slidesToShow: 3
            }
        },{
            breakpoint: 992,
            settings: {
                slidesToShow: 2
            }
        },{
            breakpoint: 600,
            settings: {
                slidesToShow: 1
            }
        }]
     });

    //orderingPage
    var orderingPageMapControl=document.getElementById('hideMap'), orderingPageMap=$('#orderingPageMap');
    
    if(orderingPageMapControl){
        orderingPageMapControl.onclick=function(){
            if (orderingPageMapControl.innerHTML=="Скрыть карту") {
                orderingPageMap.slideUp();
                orderingPageMapControl.innerHTML="Показать карту";
            } else{
                orderingPageMap.slideDown();
                orderingPageMapControl.innerHTML="Скрыть карту";
            }
        };
    };

    var LiftToTheFloor=$('#LiftToTheFloor'),
        deliveryWayCheck=$('#deliveryWayCheck'),
        LiftToTheFloorControl=document.getElementById('LiftToTheFloorControl'),
        deliveryWayCheckControl=document.getElementById('deliveryWayCheckControl'),
        LiftToTheFloorDependents=$('#numberOfFloor, #regularLift, #serviceLift, #technicalFloor'),
        deliveryWayDependent=$('#deliveryWay'),
        deliveryTimePrice=$('.orderingCheckbox.additionalServices .deliveryTimePrice');
    if(LiftToTheFloorControl){
        LiftToTheFloorControl.onclick=function(){
            if (LiftToTheFloor.prop( "checked" )) {
              LiftToTheFloorDependents.prop( "disabled", true );
              LiftToTheFloorDependents.prop( "checked", false );
              $(LiftToTheFloorDependents[0]).prop("value", '');
              if (!deliveryWayCheck.prop( "checked" )) LiftToTheFloorControl.parentElement.style.backgroundColor='#FFFFFF';                 
            } else{
                LiftToTheFloorDependents.prop( "disabled", false );
                LiftToTheFloorControl.parentElement.style.backgroundColor='#EDEDED';
            }
        }
        deliveryWayCheckControl.onclick=function(){
            if (deliveryWayCheck.prop( "checked" )) {
                deliveryWayDependent.prop( "disabled", true );
                deliveryWayDependent.prop( "value", '' );
                if (!LiftToTheFloor.prop( "checked" )) LiftToTheFloorControl.parentElement.style.backgroundColor='#FFFFFF';
                deliveryTimePrice.removeAttr('style');
            } else{
                deliveryWayDependent.prop( "disabled", false );
                LiftToTheFloorControl.parentElement.style.backgroundColor='#EDEDED';
                deliveryTimePrice[0].style.color='#FB6A4B';
            }
        }
    };

    var orderingSelectControl=$('.orderingCallLabelHolder label');

    orderingSelectControl.click(function(){
        orderingSelectControl.parent().removeClass('active');
        $(this).parent().addClass('active');
    });

    var deliveryTimeControls=$('.deliveryTime > li > label');
    
    deliveryTimeControls.click(function(){
        if(!$('#' + this.getAttribute('for')).prop( "checked" )){
            deliveryTimeControls.parent().removeAttr('style');
            this.parentElement.style.backgroundColor='#EDEDED';
            $('.orderingdeliveryTimeHolder').slideUp();
            $(this).next().slideDown();
        }
    });

    // catalog page scripts
    $('.catalogItem').equalHeights();
    window.onresize=function(){
        $('.catalogItem').removeAttr('style');
        $('.catalogItem').equalHeights();
    };

});

 //cartAdd
var cartIcon = $('.cartIconHolder .cartIcon'),
    cartOK = $('.cartIconHolder .cartOK'),
    addButtons = $('.btn-in-cart__is-added__title, .btn-to-cart, .cart-is-added__pict, .btn-in-cart');

function cartAnimate() {
    cartIcon.animate({
        'left': '100%'
    }, 300, function () {
        cartIcon.css('left', '-100%');
        cartIcon.animate({
            'left': '0'
        }, 300);
    });
    cartOK.animate({
        'top': '100%'
    }, 300, function () {
        cartOK.removeClass('emptyCart');
        cartOK.css('top', '-100%');
        cartOK.animate({
            'top': '5%'
        }, 300);
    });
}
addButtons.click(function (e) {
    cartAnimate();
    e.preventDefault();
});

// profile page
new Clipboard('.copy-btn');