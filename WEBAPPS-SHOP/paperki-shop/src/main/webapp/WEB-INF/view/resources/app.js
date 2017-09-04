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
        url: "/api/addtocart",
        data: addItemToJson(pnt, quantity),
        success: function(response) {
            if(response.code == "OK") {
                alert("Товар под кодом("+pnt+") успено добавлен в корзину!");
            } else if (response.code == "NOT_FOUND") {
                alert("На складе недостаточное количество товара");
            } else if(response.code == "INTERNAL_SERVER_ERROR") {
                serverAlert();
                console.log(response.message);
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
        url: "/api/deletefromcart",
        data: JSON.stringify({"pnt":pnt}),
        success: function(response) {
            if(response.code == "OK") {
                alert("Товар под кодом("+pnt+") успено удален из корзины!");
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
             }else if(response.code == "INTERNAL_SERVER_ERROR") {
                 console.log(response);
                 mapErrorSubscribe(response.object);
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
             } else if(response.code == "BAD_REQUEST") {
                 console.log(response);
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

function callBackToJSON () {
    return JSON.stringify({
         "name": $('#name-subscribe').val(),
         "phone": $('#email-subscribe').val(),
         "comment":
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
    alert("Возникла ошибка при попытке подписки на рассылку: "+object);
}

function mapErrorCallBackForm(object) {
    alert("Возникла ошибка при попытке оставить запрос на обратный звонок: "+object);
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


function toFavorites() {
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
