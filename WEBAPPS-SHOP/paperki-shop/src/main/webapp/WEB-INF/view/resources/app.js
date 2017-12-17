$( document ).ready(function() {
    $('#enterpriseForm').hide();
    hideLoader();
});

function showLoader() {
    $('#loader-popup').removeClass('hide').addClass('show');
}

function hideLoader() {
    $('#loader-popup').removeClass('show').addClass('hide');
}

function mapErrorToField(formField, input, label) {
    if(formField != null) {
        input.addClass("input_error");
        label.addClass('label_error');
        label.attr("title", formField).tooltip('fixTitle').tooltip("show");
    } else {
        input.removeClass("input_error");
        label.removeClass('label_error');
        label.tooltip("hide");
    }
}

function FormToJson(formArray) {

  var arr = {};
  for (var i = 0; i < formArray.length; i++){
    arr[formArray[i]['name']] = formArray[i]['value'];
  }
  return arr;
}

function ResetForm(event) {
    var form = $(event).parents('.popup-content').children('form');
    internalResetForm(form);
}

function internalResetForm(form) {
    form.find('input:text, input:hidden, input:password, input:file, select, textarea').val('')
        .removeClass("input_error");

    form.find('input:radio, input:checkbox')
        .removeAttr('checked')
        .removeAttr('selected')
        .removeClass("input_error");

    form.find('.input__label-content').removeClass('label_error').tooltip("hide");
}

function addToCart(pnt) {
    var pntinput = $('#'+pnt);
    var popupConfirm = pntinput.parents('.btns').children('.product-add-popup-confirm');
    popupConfirm.removeClass('is-visible');
    popupConfirm.children('.textarea').html('');

    showLoader();

    quantity = pntinput.val();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/cart/addtocart",
        data: addItemToJson(pnt, quantity),
        success: function(response) {
            if(response.code == "OK") {
                mapCart(response.object);
                printAddedItem(response.object, pnt);
                pntinput.val(1);
            } else if (response.code == "LOCKED") {
                mapConfirmQuantity(pntinput, pnt, response.object);
            } else if (response.code == "NOT_FOUND") {
                alert("Запрошенный товар недоступен");
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                serverAlert();
            }
        },
        error: function() {
            serverAlert();
        }
    });
    hideLoader();
}


function mapConfirmQuantity(pntinput, pnt, quantityAvailable) {
    var popupConfirm = pntinput.parents('.btns').children('.product-add-popup-confirm');
    // var addBtn = pntinput.parents('.btns').children('.add-btn');

    popupConfirm.children('.textarea').html('В наличии только: <strong style="color: red;">'+quantityAvailable+'</strong> ед.');
    popupConfirm.children('a.confirm').attr('onclick', 'confirmAddToCart('+pnt+', '+quantityAvailable+')');
    popupConfirm.addClass('is-visible');
}

function confirmAddToCart(pnt, quantity) {
    $('#'+pnt).val(quantity);
    addToCart(pnt);
}


function deleteFromCart(pnt) {
    showLoader();
    $.ajax({
        cache: false,
        async: true,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/cart/deletefromcart",
        data: JSON.stringify({"pnt":pnt}),
        success: function(response) {
            if(response.code == "OK") {
                mapCart(response.object);
                location.reload();
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                hideLoader();
                alert("Не удалось удалить из корзины товар под кодом("+pnt+")");
                console.log(response.message);
            }
        },
        error: function() {
            serverAlert();
        }
    });
}

function register() {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/registration",
        data: regFormToJSON(),
        success: function(response){
            if(response.code == "CREATED") {
                location.reload();
            } else if(response.code == "NOT_ACCEPTABLE") {
                mapErrorRegisterForm(response.object);
                hideLoader();
            }
            else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                hideLoader();
                serverAlert();
            }
        },
        error: function () {
            hideLoader();
            serverAlert();
        }
    });
}

function logout() {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/logout",
        data: {"logout":true},
        success: function(response){
            if(response.code == "OK") {
                location.reload();
            }
            else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
    });
    hideLoader();
}

function login() {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/login",
        data: authFormToJSON(),
        success: function(response){
            if(response.code == "FOUND") {
                location.reload();
            }else if(response.code == "NOT_FOUND") {
                mapErrorLoginForm(response.object);
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
    });
    hideLoader();
}

function subscribe() {
    showLoader();
    $.ajax({
         cache: false,
         async: false,
         type: "POST",
         contentType: "application/json",
         dataType: "json",
         url: "/api/subscribe",
         data: subscribeToJSON(),
         success: function(response){
             if(response.code == "OK") {
                 alert("Вы успешно подписаны на рассылку");
             } else if(response.code == "BAD_REQUEST") {
                console.log(response);
                mapErrorSubscribe(response.object);
             }else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
             }
         },
         error: function () {
             serverAlert();
         }
    });
    hideLoader();
}

function callback() {
    showLoader();
    $.ajax({
         cache: false,
         async: false,
         type: "POST",
         contentType: "application/json",
         dataType: "json",
         url: "/api/callback",
         data: callBackToJSON(),
         success: function(response){
             if(response.code == "OK") {
                 alert("Запрос на обратный звонок успешно оставлен,\nИДЕНТИФИКАТОР ЗАПРОСА: "+response.object);
                 mapErrorCallBackForm(response.object);
             } else if(response.code == "BAD_REQUEST") {
                 mapErrorCallBackForm(response.object);
             } else if(response.code == "INTERNAL_SERVER_ERROR") {
                 console.log(response);
                 serverAlert();
             }
         },
         error: function () {
             serverAlert();
         }
    });
    hideLoader();
}

function feedback() {
    showLoader();
    $.ajax({
         cache: false,
         async: false,
         type: "POST",
         contentType: "application/json",
         dataType: "json",
         url: "/api/feedback",
         data: feedbackToJSON(),
         success: function(response){
             if(response.code == "OK") {
                 alert("Благодарим Вас за отзыв!,\nИДЕНТИФИКАТОР ЗАПРОСА: "+response.object+".\nПосле проверки модератором отзыв будет опубликован");
                 mapErrorFeedbackForm(response.object);
             } else if(response.code == "BAD_REQUEST") {
                 mapErrorFeedbackForm(response.object);
             } else if(response.code == "INTERNAL_SERVER_ERROR") {
                 console.log(response);
                 serverAlert();
             }
         },
         error: function () {
             serverAlert();
         }
    });
    hideLoader();
}

function feedbackToJSON () {
    return JSON.stringify({
        "userName":         $('#send-feedback-input-name').val(),
        "email":            $('#send-feedback-input-email').val(),
        "text":             $('#send-description-textarea').val()
    });
}

function callBackToJSON () {
    return JSON.stringify({
        "name":             $('#callback-input-name').val(),
        "phone":            $('#callback-input-phone').val(),
        "comment":          $('#callback-textarea').val()
    });
}

function subscribeToJSON() {
    return JSON.stringify({
        "name":             $('#name-subscribe').val(),
        "email":            $('#email-subscribe').val()
    });
}

function addItemToJson(pnt, quantity) {
    return JSON.stringify({
        "pnt":              pnt, 
        "quantity":         quantity
    });
}

function authFormToJSON() {
    return JSON.stringify({
        "login":            $('#enter-input-email').val(),
        "password":         $('#enter-input-password').val()
    });
}

function regFormToJSON() {
    return JSON.stringify({
        "name":             $('#registration-input-name').val(),
        "email":            $('#registration-input-email').val(),
        "subscribe":        $('#check-subscribe').attr("checked") == "checked" ? true:false,
        "password":         $('#registration-input-password').val(),
        "passwordConfirm":  $('#registration-input-confirm-password').val(),
        "autopass":         $('#check-autopassword').attr("checked") == "checked" ? true:false,
        "phone":            $('#registration-input-phone').val(),
        "enterprise":       $('#check-isenterprise').attr("checked") == "checked" ? true:false,
        "unp":              $('#registration-input-enterprise-unp').val(),
        "enterpriseName":   $('#registration-input-enterprise-name').val(),
        "billingAddress":   $('#registration-input-enterprise-address').val(),
        "accountNumber":    $('#registration-input-enterprise-account').val(),
        "bankName":         $('#registration-input-enterprise-account-bank').val(),
        "bankCode":         $('#registration-input-enterprise-account-bank-code').val()
    });
}

function mapErrorFeedbackForm(form) {
    mapErrorToField(form.userName, $('#send-feedback-input-name'), $('#send-feedback-label-name'));
    mapErrorToField(form.email, $('#send-feedback-input-email'), $('#send-feedback-label-email'));
    mapErrorToField(form.text, $('#send-description-textarea'), $('#send-description-label'));
}

function mapErrorRegisterForm(form) {
    mapErrorToField(form.name, $('#registration-input-name'), $('#registration-label-name'));
    mapErrorToField(form.email, $('#registration-input-email'), $('#registration-label-email'));
    mapErrorToField(form.password, $('#registration-input-password'), $('#registration-label-password'));
    mapErrorToField(form.confirmPassword, $('#registration-input-confirm-password'), $('#registration-label-confirm-password'));
    mapErrorToField(form.phone, $('#registration-input-phone'), $('#registration-label-phone'));

    if($('#check-isenterprise').attr("checked") == "checked") {
        mapErrorToField(form.unp, $('#registration-input-enterprise-unp'), $('#registration-label-enterprise-unp'));
        mapErrorToField(form.enterpriseName, $('#registration-input-enterprise-name'), $('#registration-label-enterprise-name'));
        mapErrorToField(form.billingAddress, $('#registration-input-enterprise-address'), $('#registration-label-enterprise-address'));
        mapErrorToField(form.bankName, $('#registration-input-enterprise-account-bank'), $('#registration-label-enterprise-account-bank'));
        mapErrorToField(form.accountNumber, $('#registration-input-enterprise-account'), $('#registration-label-enterprise-account'));
        mapErrorToField(form.bankCode, $('#registration-input-enterprise-account-bank-code'), $('#registration-label-enterprise-account-bank-code'));
    }
}

function mapErrorLoginForm(form) {
    mapErrorToField(form.login, $('#enter-input-email'), $("#enter-input-email-title"));
    mapErrorToField(form.password, $('#enter-input-password'), $("#enter-input-password-title"));
}

function mapErrorSubscribe(object){
    if(object.name != null) {
        $('#name-subscribe').attr("title", object.name)
                            .tooltip('fixTitle')
                            .tooltip("show");
    } else {
        $('#name-subscribe').tooltip("hide");
    }
    if(object.email != null) {
        $('#email-subscribe').attr("title", object.email)
                             .tooltip('fixTitle')
                             .tooltip("show");
    } else {
        $('#email-subscribe').tooltip("hide");
    }

}

function mapErrorCallBackForm(object) {
    mapErrorToField(object.name, $('#callback-input-name'), $('#callback-label-name'));
    mapErrorToField(object.phone, $('#callback-input-phone'), $('#callback-label-phone'));
}

function serverAlert() {
    alert('Возникла непредвиденная ошибка сервера или сервер недоступен.\n' +
        'Пожалуйста перезагрузите страницу или свяжитесь со службой поддержки(+375-29-715-60-60)');
}

function showEnterpriseForm() {
    if($('#check-isenterprise').attr("checked") == "checked") $('#enterpriseForm').show("slow");
    else $('#enterpriseForm').hide("slow");
}

function toOrderPage() {
    var orderURL = '/order';
    document.location.href = orderURL;
}


function toFavorites(pnt) {
    alert('Функциональность временно недоступна');
}

function activatePromo() {
    alert('Функциональность временно недоступна');
}




function mapCart(cart) {
    $('#cart-total-with-vat').html('');
    $('#nav-cart-total-with-vat').html('');
    if(cart.finalTotalWithVAT > 0.0) {
        $('#cart-total-with-vat').html('<span class="total-sum">'+cart.finalTotalWithVAT+'</span> руб</p>');
        $('#nav-cart-total-with-vat').html('<span class="total-sum">'+cart.finalTotalWithVAT+'</span> руб</p>');
    }
    printCartItems(cart.items);
}

function printAddedItem(cart, pnt) {
    $('#in-cart-item-'+pnt).html('');
    $('#in-cart-item-'+pnt).html(
        '<div class="cart-is-added">'+
            '<div class="cart-is-added__pict">'+
                '<img src="/res/img/cart-is-added.png" alt="added to cart">'+
            '</div>'+
        '<div class="cart-is-added__number">'+
        '<p><span>'+cart.items[pnt].quantity+'</span> ед.</p></div></div>');
}

function printCartItems(products) {
    $('#cart-cont').html('');
    $('#nav-cart-cont').html('');
    if(products != null) {
        $.each( products, function( pnt, product ) {
            var cartProductHtml =
                '<li style="left: 0px;">'+
                '<div class="drop-pr">'+
                '<span class="drop-pr" aria-hidden="true" onclick="deleteFromCart('+product.pnt+')">✖</span>'+
                '</div>'+
                '<div class="img-cart">'+
                '<img src="/catimagesold/'+product.imageName+'" alt="'+product.fullName+'" style="max-width: 110px;">'+
                '</div>'+
                '<div class="desc-cart">'+
                '<a href="'+product.link+'" class="name-pr-cart">'+product.fullName+'</a>'+
                '<ul class="char-cart">'+
                '<li>'+product.shortName+'</li>'+
                '</ul>'+
                '<p class="price-in-cart">'+product.finalPriceWithVAT+' руб X '+product.quantity+' ед.</p>'+
                '</div>'+
                '</li>';
            $('#cart-cont').append(cartProductHtml);
            $('#nav-cart-cont').append(cartProductHtml);
        });
    }
}


/*===================================================================================*/


function confirmOrder() {
    submitOrder();
}

function redirectToOrderDone(token) {
    document.location.href = '/order/'+token;
}

function switchOrderType(type) {
    $('#order-type-switcher').val(type);
}

function submitOrder() {
    // console.log(FormToJson($('#order-customer-form').serializeArray()));
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/order/submit",
        data: orderFormToJSON(),
        success: function(response) {
            if(response.code == "OK") {
                redirectToOrderDone(response.object);
            } else if (response.code == "BAD_REQUEST") {
                console.log(response);
                mapErrorOrderForm(response.object);
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function() {
            serverAlert();
        }
    });
    hideLoader();
}

function orderFormToJSON() {
    if($('#order-type-switcher').val() == 1) {
        return JSON.stringify({
            "type":             $('#order-type-switcher').val(),
            "name" :            $('#order-input-customer-name').val(),
            "email":            $('#order-input-customer-email').val(),
            "phone":            $('#order-input-customer-phone').val(),
            "shipment_id":      $('#customer-shipment-chosen input[type=radio]:checked').val(),
            "shipment_address": $('#order-input-customer-address-value').val(),
            "payment_id":       $('#customer-payment-chosen input[type=radio]:checked').val(),
            "comment":          $('#order-customer-comment').val()
        });
    } else if($('#order-type-switcher').val() == 2) {
        return JSON.stringify({
            "type":             $('#order-type-switcher').val(),
            "email":            $('#order-input-enterprise-email').val(),
            "phone":            $('#order-input-enterprise-phone').val(),
            "enterprise-name":  $('#order-input-enterprise-name').val(),
            "unp":              $('#order-input-enterprise-unp').val(),
            "address":          $('#order-input-enterprise-address').val(),
            "shipment_id":      $('#enterprise-shipment-chosen input[type=radio]:checked').val(),
            "shipment_address": $('#order-input-enterprise-address-value').val(),
            "payment_id":       $('#enterprise-payment-chosen input[type=radio]:checked').val(),
            "comment":          $('#order-enterprise-comment').val()

            // "subscribe":        $('#check-subscribe').attr("checked") == "checked" ? true:false
        
        });
    }
}

function mapErrorOrderForm(form) {
	if($('#order-type-switcher').val() == 1) {

        mapErrorToField(form.name, $('#order-input-customer-name'), $('#order-label-customer-name'));
        mapErrorToField(form.email, $('#order-input-customer-email'), $('#order-label-customer-email'));
        mapErrorToField(form.phone, $('#order-input-customer-phone'), $('#order-label-customer-phone'));

		if (form.shipment != null) {
			$('#customer-shipment-chosen').attr("title", form.shipment)
                                 		  .tooltip('fixTitle')
                                          .tooltip("show");
		} else {
			$('#customer-shipment-chosen').tooltip("hide");
		}

		if (form.payment != null) {
			$('#customer-payment-chosen').attr("title", form.payment)
                                 		 .tooltip('fixTitle')
                                         .tooltip("show");
		} else {
			$('#customer-payment-chosen').tooltip("hide");
		}

		document.location.href = '#customer-tab';
		
	} else if($('#order-type-switcher').val() == 2) {

        mapErrorToField(form.name, $('#order-input-enterprise-name'), $('#order-label-enterprise-name'));
        mapErrorToField(form.email, $('#order-input-enterprise-email'), $('#order-label-enterprise-email'));
        mapErrorToField(form.phone, $('#order-input-enterprise-phone'), $('#order-label-enterprise-phone'));
        mapErrorToField(form.unp, $('#order-input-enterprise-unp'), $('#order-label-enterprise-unp'));
        mapErrorToField(form.address, $('#order-input-enterprise-address'), $('#order-label-enterprise-address'));

        if (form.shipment != null) {
            $('#enterprise-shipment-chosen').attr("title", form.shipment)
                                            .tooltip('fixTitle')
                                            .tooltip("show");
        } else {
            $('#enterprise-shipment-chosen').tooltip("hide");
        }

        if (form.payment != null) {
            $('#enterprise-payment-chosen').attr("title", form.payment)
                                           .tooltip('fixTitle')
                                           .tooltip("show");
        } else {
            $('#enterprise-payment-chosen').tooltip("hide");
        }

        document.location.href = '#enterprise-tab';

	}
}



/*===================================================================================*/
function sendSortType() {
    setSortType($('select[name=sort-by]').val());
}

function setSortType(type) {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/catalog/sortedby",
        data: JSON.stringify({"type":type}),
        success: function(response){
            location.reload();
        },
        error: function () {
            location.reload();
        }
    });
}

function seViewType(type) {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/catalog/viewtype",
        data: JSON.stringify({"type":type}),
        success: function(response){
            location.reload();
        },
        error: function () {
            location.reload();
        }
    });
}

/*===================================================================================*/

function updatePersonalData() {
    showLoader();
    $.ajax({
         cache: false,
         async: false,
         type: "POST",
         contentType: "application/json",
         dataType: "json",
         url: "/api/user/update",
         data: updatePersonalDataFormToJSON(),
         success: function(response){
             if(response.code == "OK") {
                location.reload();
                // document.location.href = '#enterprise-tab';
             } else if(response.code == "BAD_REQUEST") {
                mapErrorUpdatePerson(response.object);
                console.log(response);
             } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
             }
         },
         error: function () {
             serverAlert();
         }
    });
    hideLoader();
}

function updatePersonalDataFormToJSON() {
    return JSON.stringify({
        "name":     $('#update-personal-data-person').val(),
        "email":    $('#update-personal-data-email').val(),
        "phone":    $('#update-personal-data-phone').val(),
        "birthday": $('.update-personal-data-birthday').val()
    });
}

function mapErrorUpdatePerson (form) {
    mapErrorToField(form.name, $('#update-personal-data-person'), $('#label-update-personal-data-person'));
    mapErrorToField(form.email, $('#update-personal-data-email'), $('#label-update-personal-data-email'));
    mapErrorToField(form.phone, $('#update-personal-data-phone'), $('#label-update-personal-data-phone'));
    mapErrorToField(form.birthday, $('.update-personal-data-birthday'), $('#label-update-personal-data-birthday'));
}

/*=========================================================================================*/


function changePassword() {
    showLoader();
    $.ajax({
         cache: false,
         async: false,
         type: "POST",
         contentType: "application/json",
         dataType: "json",
         url: "/api/user/changepassword",
         data: changePasswordDataFormToJSON(),
         success: function(response){
             if(response.code == "OK") {
                alert("пароль успешно изменен!");
                // location.reload();
                document.location.href = '/';
             } else if(response.code == "BAD_REQUEST") {
                mapErrorChangePassword(response.object);
                console.log(response);
             } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
             }
         },
         error: function () {
             serverAlert();
         }
    });
    hideLoader();
}

function changePasswordDataFormToJSON() {
    return JSON.stringify({
        "oldPassword":          $('#change-password-current-password').val(),
        "newPassword":          $('#change-password-new-password').val(),
        "newPasswordConfirm":   $('#change-password-new-password-confirm').val()
    });
}

function mapErrorChangePassword (form) {
    mapErrorToField(form.oldPassword, $('#change-password-current-password'), $('#label-change-password-current-password'));
    mapErrorToField(form.newPassword, $('#change-password-new-password'), $('#label-change-password-new-password'));
    mapErrorToField(form.newPasswordConfirm, $('#change-password-new-password-confirm'), $('#label-change-password-new-password-confirm'));
}

/*=========================================================================================*/


/*=========================================================================================*/

function addEnterprise() {
    showLoader();
  $.ajax({
    cache: false,
    async: false,
    type: "POST",
    contentType: "application/json",
    dataType: "json",
    url: "/api/user/addenterprise",
    data: enterpriseFormToJSON(),
    success: function(response){
      if(response.code == "OK") {
        location.reload();
        // document.location.href = '#enterprise-tab';
      } else if(response.code == "BAD_REQUEST") {
        mapErrorEnterpriseForm(response.object);
        console.log(response);
      } else if(response.code == "INTERNAL_SERVER_ERROR") {
        console.log(response);
        serverAlert();
      }
    },
    error: function () {
      serverAlert();
    }
  });
  hideLoader();
}

function enterpriseFormToJSON() {
  return JSON.stringify({
    "name":           $('#input-add-enterprise-name').val(),
    "unp":            $('#input-add-enterprise-unp').val()
  });
}

function mapErrorEnterpriseForm (form) {
  mapErrorToField(form.name, $('#input-add-enterprise-name'), $('#label-add-enterprise-name'));
  mapErrorToField(form.unp, $('#input-add-enterprise-unp'), $('#label-add-enterprise-unp'));
}

/*=========================================================================================*/

function updateEnterprise() {
    showLoader();
    $.ajax({
    cache: false,
    async: false,
    type: "POST",
    contentType: "application/json",
    dataType: "json",
    url: "/api/user/updateenterprise",
    data: enterpriseUpdateFormToJSON(),
    success: function(response){
      if(response.code == "OK") {
        location.reload();
      } else if(response.code == "BAD_REQUEST") {
        mapErrorEnterpriseUpdateForm(response.object);
        console.log(response);
      } else if(response.code == "INTERNAL_SERVER_ERROR") {
        console.log(response);
        serverAlert();
      }
    },
    error: function () {
      serverAlert();
    }
  });
    hideLoader();
}

function enterpriseUpdateFormToJSON() {
  return JSON.stringify({
    "name":           $('#input-update-enterprise-name').val()
  });
}

function mapErrorEnterpriseUpdateForm (form) {
  mapErrorToField(form.name, $('#input-update-enterprise-name'), $('#label-update-enterprise-name'));
}

/*=========================================================================================*/

var delayTimer;
function searchProducts() {
    clearTimeout(delayTimer);
    delayTimer = setTimeout(function() {
        sendSearchRequest($('#search-popup-input').val());
    }, 2000);
}

function sendSearchRequest(str) {
    showLoader();
    $.ajax({
        cache: false,
        async: true,
        type: "GET",
        contentType: "application/json",
        url: "/api/search?str="+str,
        success: function(response){
            if(response.code == "FOUND") {
                mapSearchResult(response.object);
                hideLoader();
            } else if(response.code == "BAD_REQUEST") {
                console.log(response);
                hideLoader();
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
                hideLoader();
            }
        },
        error: function () {
            serverAlert();
            hideLoader();
        }
    });
}

function mapSearchResult(array) {
    $('.drop-search-catalog').html('');
    if(array != null) {
        $.each( array, function(i, product) {
            $('.drop-search-catalog').append(
                '<div class="cont-row">'+
                    '<div class="col1">'+
                        '<img style="max-width: 55px;" src="/catimagesold/'+product.imageName+'" alt="'+product.fullName+'">'+
                        '<h5><a href="'+product.link+'">'+product.fullName+'</a></h5>'+
                        '<div class="rating"></div>'+
                        '<p>Код: '+product.pnt+'</p>'+
                    '</div>'+
                    '<div class="col2">'+product.finalPriceWithVAT+' руб</div>'+
                    '<div class="col3">' +
                        '<div>'+
                            '<div class="dec">-</div>'+
                            '<input type="text" name="french-hens" class="inde" value="1">'+
                            '<div class="inc">+</div>'+
                        '</div>'+
                    '</div>'+
                    '<div class="btn-to-cart" onclick="addToCart('+product.pnt+')">' +
                        '<img src="/res/img/buy.png" alt="Купить">' +
                    '</div>'+
                '</div>'
            );
        });
    }
}


/*=========================================================================================*/


function getAndMapAddress(idAddress) {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/getaddress?id="+idAddress,
        success: function(response){
            if(response.code == "OK") {
                mapAddressToUpdateForm(response.object);
            } else if(response.code == "BAD_REQUEST") {
                console.log(response);
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
    });
    hideLoader();
}

function mapAddressToUpdateForm(address) {
    $('#input-edit-address-owner').val(address.ownerId);
    $('#input-edit-address-type').val(address.type);
    $('#input-edit-address-id').val(address.id);

    mapAddresUpdateField(address.index, $('#input-edit-address-index'));
    mapAddresUpdateField(address.city, $('#input-edit-address-city'));
    mapAddresUpdateField(address.street, $('#input-edit-address-street'));
    mapAddresUpdateField(address.house, $('#input-edit-address-house'));
    mapAddresUpdateField(address.housePart, $('#input-edit-address-house-part'));
    mapAddresUpdateField(address.houseOffice, $('#input-edit-address-house-office'));
    mapAddresUpdateField(address.description, $('#input-edit-address-description'));
}

function addressEditFormToJSON() {
    return JSON.stringify({
        "ownerId":          $('#input-edit-address-owner').val(),
        "type":             $('#input-edit-address-type').val(),
        "index":            $('#input-edit-address-index').val(),
        "city":             $('#input-edit-address-city').val(),
        "street":           $('#input-edit-address-street').val(),
        "house":            $('#input-edit-address-house').val(),
        "housePart":        $('#input-edit-address-house-part').val(),
        "houseOffice":      $('#input-edit-address-house-office').val(),
        "description":      $('#input-edit-address-description').val(),
        "id":               $('#input-edit-address-id').val()
    });
}

function mapAddresUpdateField(value, input) {
    if(value == "") {
        $(input).parents('span').removeClass('input--filled');
    } 
    else {
        $(input).parents('span').addClass('input--filled');
        $(input).val(value);
    } 
}


/*=========================================================================================*/


function setOwnerAndType(owner, type) {
    $('#input-add-address-owner').val(owner);
    $('#input-add-address-type').val(type);
}

function AddAddress() {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/addressadd",
        data: addressFormToJSON(),
        success: function(response){
            if(response.code == "OK") {
                location.reload();
                // document.location.href = '#enterprise-tab';
            } else if(response.code == "BAD_REQUEST") {
                mapErrorAddressForm(response.object);
                console.log(response);
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
    });
    hideLoader();
}

function addressFormToJSON() {
    return JSON.stringify({
        "ownerId":          $('#input-add-address-owner').val(),
        "type":             $('#input-add-address-type').val(),
        "index":            $('#input-add-address-index').val(),
        "city":             $('#input-add-address-city').val(),
        "street":           $('#input-add-address-street').val(),
        "house":            $('#input-add-address-house').val(),
        "housePart":        $('#input-add-address-house-part').val(),
        "houseOffice":      $('#input-add-address-house-office').val(),
        "description":      $('#input-add-address-description').val()
    });
}

function mapErrorAddressForm (form) {
    mapErrorToField(form.index, $('#input-add-address-index'), $('#label-add-address-index'));
    mapErrorToField(form.city, $('#input-add-address-city'), $('#label-add-address-city'));
    mapErrorToField(form.street, $('#input-add-address-street'), $('#label-add-address-street'));
    mapErrorToField(form.house, $('#input-add-address-house'), $('#label-add-address-house'));
    mapErrorToField(form.housePart, $('#input-add-address-house-part'), $('#label-add-address-house-part'));
    mapErrorToField(form.houseOffice, $('#input-add-address-house-office'), $('#label-add-address-house-office'));
    mapErrorToField(form.description, $('#input-add-address-description'), $('#label-add-address-description'));
}

/*=========================================================================================*/

function UpdateAddress() {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/addressupdate",
        data: addressEditFormToJSON(),
        success: function(response){
            if(response.code == "OK") {
                internalResetForm($('#address-edit-form'));
                location.reload();
            } else if(response.code == "BAD_REQUEST") {
                mapErrorAddressUpdateForm(response.object);
                console.log(response);
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
    });
    hideLoader();
}

function mapErrorAddressUpdateForm (form) {
    mapErrorToField(form.index, $('#input-edit-address-index'), $('#label-edit-address-index'));
    mapErrorToField(form.city, $('#input-edit-address-city'), $('#label-edit-address-city'));
    mapErrorToField(form.street, $('#input-edit-address-street'), $('#label-edit-address-street'));
    mapErrorToField(form.house, $('#input-edit-address-house'), $('#label-edit-address-house'));
    mapErrorToField(form.housePart, $('#input-edit-address-house-part'), $('#label-edit-address-house-part'));
    mapErrorToField(form.houseOffice, $('#input-edit-address-house-office'), $('#label-edit-address-house-office'));
    mapErrorToField(form.description, $('#input-edit-address-description'), $('#label-edit-address-description'));
}

/*=========================================================================================*/


function deleteAddress(idAddress) {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "DELETE",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/addressdelete?id="+idAddress,
        success: function(response){
            if(response.code == "OK") {
                location.reload();
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
    });
    hideLoader();
}

function sendPasswordRestoreRequest() {
    restorePassword($('#input-password-recovery-request').val());
}

function restorePassword(login) {
    showLoader();
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/user/passwordrestore",
        data: JSON.stringify({"userLogin":login}),
        success: function(response){
            if(response.code == "OK") {
                document.location.href = '/password';
            } else if(response.code == "BAD_REQUEST") {
                mapBadPasswordRecoveryRequest(response.object);
                hideLoader();
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response);
                hideLoader();
                serverAlert();
            }
        },
        error: function () {
            hideLoader();
            serverAlert();
        }
    });

}

function mapBadPasswordRecoveryRequest(errorForm) {
    mapErrorToField(errorForm.message, $('#input-password-recovery-request'), $('#label-password-recovery-request'));
}