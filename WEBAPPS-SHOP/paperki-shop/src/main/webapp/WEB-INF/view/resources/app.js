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
    var loader = $(pntinput).parents('.btns').children('.cart-add-loader');

    showLoader();
    $(loader).show();

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
                console.log(response);
                mapCart(response.object);
                printAddedItem(response.object, pnt);
                // pntinput.parents('.add-area').hide();
            } else if (response.code == "NOT_FOUND") {
                console.log(response);
                alert("На складе недостаточное количество товара");
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
    loader.hide();
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
                console.log(response);
                mapCart(response.object);
                location.reload();
                // alert("Товар под кодом("+pnt+") успено удален из корзины!");
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                alert("Не удалось удалить из корзины товар под кодом("+pnt+")");
                console.log(response.message);
            }
        },
        error: function() {
            serverAlert();
        }
    });
    hideLoader();
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
                alert(response.message);
                location.reload();
            } else if(response.code == "NOT_ACCEPTABLE") {
                mapErrorRegisterForm(response.object);
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
                // alert(response.message);
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
                // alert(response.message);
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
    if(form.userName != null) {
        $('#send-feedback-input-name').addClass("input_error");
        $('#send-feedback-label-name').addClass('label_error');
        $('#send-feedback-label-name').attr("title", form.userName)
                                      .tooltip('fixTitle')
                                      .tooltip("show");
    } else {
        $('#send-feedback-input-name').removeClass("input_error");
        $('#send-feedback-label-name').removeClass('label_error');
        $('#send-feedback-label-name').tooltip("hide");
    }
    if (form.email != null) {
        $('#send-feedback-input-email').addClass("input_error");
        $('#send-feedback-label-email').addClass('label_error');
        $('#send-feedback-label-email').attr("title", form.email)
                                       .tooltip('fixTitle')
                                       .tooltip("show");
    } else {
        $('#send-feedback-input-email').removeClass("input_error");
        $('#send-feedback-label-email').removeClass('label_error');
        $('#send-feedback-label-email').tooltip("hide");
    }
    if (form.text != null) {
        $('#send-description-textarea').addClass("input_error");
        $('#send-description-label').addClass('label_error');
        $('#send-description-label').attr("title", form.text)
                                    .tooltip('fixTitle')
                                    .tooltip("show");
    } else {
        $('#send-description-textarea').removeClass("input_error");
        $('#send-description-label').removeClass('label_error');
        $('#send-description-label').tooltip("hide");
    }
}

function mapErrorRegisterForm(form) {
    console.log(form);
    if(form.name != null) {
        $('#registration-input-name').addClass("input_error");
        $('#registration-label-name').addClass('label_error');
        $('#registration-label-name').attr("title", form.name)
                                     .tooltip('fixTitle')
                                     .tooltip("show");
    } else {
        $('#registration-input-name').removeClass("input_error");
        $('#registration-label-name').removeClass('label_error');
        $('#registration-label-name').tooltip("hide");
    }
    if(form.email != null) {
        $('#registration-input-email').addClass("input_error");
        $('#registration-label-email').addClass('label_error');
        $('#registration-label-email').attr("title", form.email)
                                     .tooltip('fixTitle')
                                     .tooltip("show");
    } else {
        $('#registration-input-email').removeClass("input_error");
        $('#registration-label-email').removeClass('label_error');
        $('#registration-label-email').tooltip("hide");
    }
    if(form.password != null) {
        $('#registration-input-password').addClass("input_error");
        $('#registration-label-password').addClass('label_error');
        $('#registration-label-password').attr("title", form.password)
                                         .tooltip('fixTitle')
                                         .tooltip("show");
    } else {
        $('#registration-input-password').removeClass("input_error");
        $('#registration-label-password').removeClass('label_error');
        $('#registration-label-password').tooltip("hide");
    }
    if(form.confirmPassword != null) {
        $('#registration-input-confirm-password').addClass("input_error");
        $('#registration-label-confirm-password').addClass('label_error');
        $('#registration-label-confirm-password').attr("title", form.password)
                                                 .tooltip('fixTitle')
                                                 .tooltip("show");
    } else {
        $('#registration-input-confirm-password').removeClass("input_error");
        $('#registration-label-confirm-password').removeClass('label_error');
        $('#registration-label-confirm-password').tooltip("hide");
    }
    if(form.phone != null) {
        $('#registration-input-phone').addClass("input_error");
        $('#registration-label-phone').addClass('label_error');
        $('#registration-label-phone').attr("title", form.phone)
                                      .tooltip('fixTitle')
                                      .tooltip("show");
    } else {
        $('#registration-input-phone').removeClass("input_error");
        $('#registration-label-phone').removeClass('label_error');
        $('#registration-label-phone').tooltip("hide");
    }

    if($('#check-isenterprise').attr("checked") == "checked") {

        if(form.unp != null) {
            $('#registration-input-enterprise-unp').addClass("input_error");
            $('#registration-label-enterprise-unp').addClass('label_error');
            $('#registration-label-enterprise-unp').attr("title", form.unp)
                                                   .tooltip('fixTitle')
                                                   .tooltip("show");
        } else {
            $('#registration-input-enterprise-unp').removeClass("input_error");
            $('#registration-label-enterprise-unp').removeClass('label_error');
            $('#registration-label-enterprise-unp').tooltip("hide");
        }
        if(form.enterpriseName != null) {
            $('#registration-input-enterprise-name').addClass("input_error");
            $('#registration-label-enterprise-name').addClass('label_error');
            $('#registration-label-enterprise-name').attr("title", form.enterpriseName)
                                                    .tooltip('fixTitle')
                                                    .tooltip("show");
        } else {
            $('#registration-input-enterprise-name').removeClass("input_error");
            $('#registration-label-enterprise-name').removeClass('label_error');
            $('#registration-label-enterprise-name').tooltip("hide");
        }
        if(form.billingAddress != null) {
            $('#registration-input-enterprise-address').addClass("input_error");
            $('#registration-label-enterprise-address').addClass('label_error');
            $('#registration-label-enterprise-address').attr("title", form.billingAddress)
                                                       .tooltip('fixTitle')
                                                       .tooltip("show");
        } else {
            $('#registration-input-enterprise-address').removeClass("input_error");
            $('#registration-label-enterprise-address').removeClass('label_error');
            $('#registration-label-enterprise-address').tooltip("hide");
        }
        if(form.bankName != null) {
            $('#registration-input-enterprise-account-bank').addClass("input_error");
            $('#registration-label-enterprise-account-bank').addClass('label_error');
            $('#registration-label-enterprise-account-bank').attr("title", form.bankName)
                                                            .tooltip('fixTitle')
                                                            .tooltip("show");
        } else {
            $('#registration-input-enterprise-account-bank').removeClass("input_error");
            $('#registration-label-enterprise-account-bank').removeClass('label_error');
            $('#registration-label-enterprise-account-bank').tooltip("hide");
        }
        if(form.accountNumber != null) {
            $('#registration-input-enterprise-account').addClass("input_error");
            $('#registration-label-enterprise-account').addClass('label_error');
            $('#registration-label-enterprise-account').attr("title", form.accountNumber)
                                                       .tooltip('fixTitle')
                                                       .tooltip("show");
        } else {
            $('#registration-input-enterprise-account').removeClass("input_error");
            $('#registration-label-enterprise-account').removeClass('label_error');
            $('#registration-label-enterprise-account').tooltip("hide");
        }
        if(form.bankCode != null) {
            $('#registration-input-enterprise-account-bank-code').addClass("input_error");
            $('#registration-label-enterprise-account-bank-code').addClass('label_error');
            $('#registration-label-enterprise-account-bank-code').attr("title", form.bankCode)
                                                                 .tooltip('fixTitle')
                                                                 .tooltip("show");
        } else {
            $('#registration-input-enterprise-account-bank-code').removeClass("input_error");
            $('#registration-label-enterprise-account-bank-code').removeClass('label_error');
            $('#registration-label-enterprise-account-bank-code').tooltip("hide");
        }
    }
}

function mapErrorLoginForm(form) {
    console.log(form);
    if(form.login != null) {
        $('#enter-input-email').addClass("input_error");
        $("#enter-input-email-title").addClass('label_error');
        $("#enter-input-email-title").attr("title", form.login)
                                     .tooltip('fixTitle')
                                     .tooltip("show");
    } else {
        $('#enter-input-email').removeClass("input_error");
        $("#enter-input-email-title").removeClass('label_error');
        $("#enter-input-email-title").tooltip("hide");
    }

    if(form.password != null) {
        $('#enter-input-password').addClass("input_error");
        $("#enter-input-password-title").addClass('label_error');
        $("#enter-input-password-title").attr("title", form.password)
                                        .tooltip('fixTitle')
                                        .tooltip("show");
    } else {
        $('#enter-input-password').removeClass("input_error");
        $("#enter-input-password-title").removeClass('label_error');
        $("#enter-input-password-title").tooltip("hide");
    }
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
    if(object.name != null) {
        $('#callback-input-name').addClass("input_error");
        $('#callback-label-name').addClass('label_error');
        $('#callback-label-name').attr("title", object.name)
                                 .tooltip('fixTitle')
                                 .tooltip("show");
    } else {
        $('#callback-input-name').removeClass("input_error");
        $('#callback-label-name').removeClass('label_error');
        $('#callback-label-name').tooltip("hide");
    }
    if(object.phone != null) {
        $('#callback-input-phone').addClass("input_error");
        $('#callback-label-phone').addClass('label_error');
        $('#callback-label-phone').attr("title", object.phone)
                                  .tooltip('fixTitle')
                                  .tooltip("show");
    } else {
        $('#callback-input-phone').removeClass("input_error");
        $('#callback-label-phone').removeClass('label_error');
        $('#callback-label-phone').tooltip("hide");
    }
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
    if(cart.finalTotalWithVAT > 0.0) {
        $('#cart-total-with-vat').html('<span class="total-sum">'+cart.finalTotalWithVAT+'</span> руб</p>');
/*        $('#summa-c').text('Сумма: '+cart.finalTotalWithVAT+ ' руб');
*/    }
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
    if(products != null) {
        $.each( products, function( pnt, product ) {
            $('#cart-cont').append(
                '<li style="left: 0px;">'+
                    '<div class="drop-pr">'+
                        '<span class="drop-pr" aria-hidden="true" onclick="deleteFromCart('+product.pnt+')">✖</span>'+
                    '</div>'+
                    '<div class="img-cart">'+
                        '<img src="/res/img/products/'+product.pnt+'.jpg" alt="'+product.fullName+'" style="max-width: 110px;">'+
                    '</div>'+
                    '<div class="desc-cart">'+
                        '<a href="'+product.link+'" class="name-pr-cart">'+product.fullName+'</a>'+
                        '<ul class="char-cart">'+
                            '<li>'+product.shortName+'</li>'+
                        '</ul>'+
                        '<p class="price-in-cart">'+product.finalPriceWithVAT+' руб X '+product.quantity+' ед.</p>'+
                    '</div>'+
                '</li>'
            );
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
            "comments":         $('#order-customer-comment').val()
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
            "comments":         $('#order-enterprise-comment').val()

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
    hideLoader();
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


function setOwnerAndType(owner, type) {
    $('#input-add-address-owner').val(owner),
    $('#input-add-address-type').val(type)
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
    mapErrorToField(form.city, $('#input-add-address-city'), $('#label-add-address-city'));
    mapErrorToField(form.street, $('#input-add-address-street'), $('#label-add-address-street'));
    mapErrorToField(form.house, $('#input-add-address-house'), $('#label-add-address-house'));
}

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
    "unp":            $('#input-add-enterprise-unp').val(),
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
function searchProducts(str) {
    clearTimeout(delayTimer);
    delayTimer = setTimeout(function() {
        sendSearchRequest(str);
    }, 2000);
}

function sendSearchRequest(str) {
    showLoader();
    $.ajax({
        cache: false,
        async: true,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/api/search?str="+str,
        success: function(response){
            if(response.code == "FOUND") {
                mapSearchResult(response.object);
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

function mapSearchResult(array) {
    $('.drop-search-catalog').html('');
    if(array != null) {
        $.each( array, function(i, product) {
            $('.drop-search-catalog').append(
                '<div class="cont-row">'+
                    '<div class="col1">'+
                        '<img src="/res/img/pr1.png" alt="'+product.fullName+'">'+
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
    mapErrorToField(form.city, $('#input-update-address-city'), $('#label-update-address-city'));
    mapErrorToField(form.street, $('#input-update-address-street'), $('#label-update-address-street'));
    mapErrorToField(form.house, $('#input-update-address-house'), $('#label-update-address-house'));
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