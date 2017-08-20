$( document ).ready(function() {
    $('#enterpriseForm').hide();
});

function register() {
    $.ajax({
        cashe: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/registration",
        data: regFormToJSON(),
        success: function(response){
            if(response.code == 'CREATED') {
                alert(response.message);
                location.reload();
            } else if(response.code == "NOT_ACCEPTABLE") {
                mapErrorRegisterForm(response);
            }
            else if(response.code == 'INTERNAL_SERVER_ERROR') {
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
        cashe: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/logout",
        data: {"logout":true},
        success: function(response){
            if(response.code == 'OK') {
                alert(response.message);
                location.reload();
            }
            else if(response.code == 'INTERNAL_SERVER_ERROR') {
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
    cleanLoginErrors();
    $.ajax({
        cashe: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/login",
        data: authFormToJSON(),
        success: function(response){
            if(response.code == 'FOUND') {
                location.reload();
            }else if(response.code == 'NOT_FOUND') {
                mapErrorLoginForm(response.object);
            } else if(response.code == 'INTERNAL_SERVER_ERROR') {
                console.log(response);
                serverAlert();
            }
        },
        error: function () {
            serverAlert();
        }
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
        /*"birthDate":        $('#datepicker').val(),*/
        // if enterprise
        "enterprise":       $('#check-isenterprise').attr("checked") == "checked" ? true:false,
        "unp":              $('#registration-input-enterprise-unp').val(),
        "enterpriseName":   $('#registration-input-enterprise-name').val(),
        "billingAddress":   $('#registration-input-enterprise-address').val(),
        "accountNumber":    $('#registration-input-enterprise-account').val(),
        "bankName":         $('#registration-input-enterprise-account-bank').val(),
        "bankCode":         $('#registration-input-enterprise-account-bank-code').val()
    });
}

function mapErrorLoginForm(form) {
    if(form.login != null) {
        $('#enter-input-email').addClass("input_email_error");
        $("#enter-input-email").tooltip({
            title : form.login,
        });
        $('#enter-input-email').tooltip("show");
    }
    if(form.password != null) {
        $('#enter-input-password').addClass("input_password_error");
        $("#enter-input-password").tooltip({
            title : form.password,
        });
        $("#enter-input-password").tooltip("show");
    }
    console.log(form);
}

function cleanLoginErrors(){
    $('#enter-input-email').removeClass("input_email_error");
    $('#enter-input-email').tooltip("hide");
    $('#enter-input-password').removeClass("input_password_error");
    $('#enter-input-password').tooltip("hide");
}

function mapErrorRegisterForm(form) {
    console.log(form);
}

function serverAlert() {
    alert('Возникла непредвиденная ошибка сервера или сервер недоступен.\n' +
        'Пожалуйста перезагрузите страницу или свяжитесь со службой поддержки(+375-29-715-60-60)');
}

// ==================================================================

function showEnterpriseForm() {
    if($('#check-isenterprise').attr("checked") == "checked") $('#enterpriseForm').show("slow");
    else $('#enterpriseForm').hide("slow");
}
