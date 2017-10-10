$( document ).ready(function() {
    $('#enterpriseForm').hide();
});

function addToCart(pnt) {
    quantity = $('#'+pnt).val();
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
                alert("Товар под кодом("+pnt+") успено добавлен в корзину!");
            } else if (response.code == "NOT_FOUND") {
                console.log(response);
                alert("На складе недостаточное количество товара");
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                console.log(response.message);
                serverAlert();
            }
        },
        error: function() {
            serverAlert();
        }
    });
}

function deleteFromCart(pnt) {
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
}

function register() {
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/registration",
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
}

function logout() {
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/logout",
        data: {"logout":true},
        success: function(response){
            if(response.code == "OK") {
                alert(response.message);
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
}

function login() {
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/login",
        data: authFormToJSON(),
        success: function(response){
            if(response.code == "FOUND") {
                alert(response.message);
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
}

function subscribe() {
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
}

function callback() {
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
}

function feedback() {
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
}

function feedbackToJSON () {
    return JSON.stringify({
        "userName": $('#send-feedback-input-name').val(),
        "email": $('#send-feedback-input-email').val(),
        "text": $('#send-description-textarea').val()
    });
}

function callBackToJSON () {
    return JSON.stringify({
         "name": $('#callback-input-name').val(),
         "phone": $('#callback-input-phone').val(),
         "comment": $('#callback-textarea').val()
    });
}

function subscribeToJSON() {
    return JSON.stringify({
         "name": $('#name-subscribe').val(),
         "email": $('#email-subscribe').val()
    });
}

function addItemToJson(pnt, quantity) {
    return JSON.stringify({
        "pnt": pnt, 
        "quantity": quantity
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
    console.log(form);
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

function changeName() {
    alert('Функциональность временно недоступна');
}

function updateUser() {
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

function printCartItems(products) {
    $('#cart-cont').html('');
    if(products != null) {
        $.each( products, function( pnt, product ) {
            $('#cart-cont').append(
                '<li style="left: 0px;">'+
                    '<div class="drop-pr">'+
                        '<span class="  drop-pr" aria-hidden="true">✖</span>'+
                    '</div>'+
                    '<div class="img-cart">'+
                        '<img src="/res/img/products/'+product.pnt+'.jpg" alt="'+product.fullName+'" style="max-width: 110px;">'+
                    '</div>'+
                    '<div class="desc-cart">'+
                        '<a href="/catalog/null" class="name-pr-cart">'+product.fullName+'</a>'+
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
		if (form.name != null) {
			$('#order-input-customer-name').addClass("input_error");
			$('#order-label-customer-name').addClass('label_error');
			$('#order-label-customer-name').attr("title", form.name)
                                 		   .tooltip('fixTitle')
                                           .tooltip("show");
		} else {
			$('#order-input-customer-name').removeClass("input_error");
			$('#order-label-customer-name').removeClass('label_error');
			$('#order-label-customer-name').tooltip("hide");
		}

		if (form.email != null) {
			$('#order-input-customer-email').addClass("input_error");
			$('#order-label-customer-email').addClass('label_error');
			$('#order-label-customer-email').attr("title", form.email)
                                 		    .tooltip('fixTitle')
                                            .tooltip("show");
		} else {
			$('#order-input-customer-email').removeClass("input_error");
			$('#order-label-customer-email').removeClass('label_error');
			$('#order-label-customer-email').tooltip("hide");
		}

		if (form.phone != null) {
			$('#order-input-customer-phone').addClass("input_error");
			$('#order-label-customer-phone').addClass('label_error');
			$('#order-label-customer-phone').attr("title", form.phone)
                                 		    .tooltip('fixTitle')
                                            .tooltip("show");
		} else {
			$('#order-input-customer-phone').removeClass("input_error");
			$('#order-label-customer-phone').removeClass('label_error');
			$('#order-label-customer-phone').tooltip("hide");
		}

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
        if (form.name != null) {
            $('#order-input-enterprise-name').addClass("input_error");
            $('#order-label-enterprise-name').addClass('label_error');
            $('#order-label-enterprise-name').attr("title", form.name)
                                             .tooltip('fixTitle')
                                             .tooltip("show");
        } else {
            $('#order-input-enterprise-name').removeClass("input_error");
            $('#order-label-enterprise-name').removeClass('label_error');
            $('#order-label-enterprise-name').tooltip("hide");
        }

        if (form.email != null) {
            $('#order-input-enterprise-email').addClass("input_error");
            $('#order-label-enterprise-email').addClass('label_error');
            $('#order-label-enterprise-email').attr("title", form.email)
                                              .tooltip('fixTitle')
                                              .tooltip("show");
        } else {
            $('#order-input-enterprise-email').removeClass("input_error");
            $('#order-label-enterprise-email').removeClass('label_error');
            $('#order-label-enterprise-email').tooltip("hide");
        }

        if (form.phone != null) {
            $('#order-input-enterprise-phone').addClass("input_error");
            $('#order-label-enterprise-phone').addClass('label_error');
            $('#order-label-enterprise-phone').attr("title", form.phone)
                                              .tooltip('fixTitle')
                                              .tooltip("show");
        } else {
            $('#order-input-enterprise-phone').removeClass("input_error");
            $('#order-label-enterprise-phone').removeClass('label_error');
            $('#order-label-enterprise-phone').tooltip("hide");
        }

        if (form.unp != null) {
            $('#order-input-enterprise-unp').addClass("input_error");
            $('#order-label-enterprise-unp').addClass('label_error');
            $('#order-label-enterprise-unp').attr("title", form.unp)
                                            .tooltip('fixTitle')
                                            .tooltip("show");
        } else {
            $('#order-input-enterprise-unp').removeClass("input_error");
            $('#order-label-enterprise-unp').removeClass('label_error');
            $('#order-label-enterprise-unp').tooltip("hide");
        }

        if (form.address != null) {
            $('#order-input-enterprise-address').addClass("input_error");
            $('#order-label-enterprise-address').addClass('label_error');
            $('#order-label-enterprise-address').attr("title", form.address)
                                                .tooltip('fixTitle')
                                                .tooltip("show");
        } else {
            $('#order-input-enterprise-address').removeClass("input_error");
            $('#order-label-enterprise-address').removeClass('label_error');
            $('#order-label-enterprise-address').tooltip("hide");
        }

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
    $.ajax({
        cache: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "api/catalog/viewtype",
        data: JSON.stringify({"type":type}),
        success: function(response){
            location.reload();
        },
        error: function () {
            location.reload();
        }
    });
}