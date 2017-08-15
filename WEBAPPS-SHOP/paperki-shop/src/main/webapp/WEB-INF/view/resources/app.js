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
            }
            else if(response.code == 'INTERNAL_SERVER_ERROR') {
                alert(response.message);
                console.log(response.object);
            }
        },
        error: serverAlert()
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
                alert(response.message);
                console.log(response.object);
            }
        },
        error: serverAlert()
    });
}

function login() {
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
                alert(response.message);
                location.reload();
            }else if(response.code == 'NOT_FOUND') {
                console.log(response);
                alertLoginForm(response.object);
            } else if(response.code == 'INTERNAL_SERVER_ERROR') {
                alert(response.message);
                console.log(response.object);
            }
        },
        error: serverAlert()
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
        "enterprise":       $('#isEnterprise').attr("checked") == "checked" ? true:false
    });
}

function serverAlert() {
    alert('Возникла непредвиденная ошибка сервера или сервер недоступен.\n' +
        'Пожалуйста перезагрузите страницу или свяжитесь со службой поддержки(+375-29-715-60-60)');
}

// ==================================================================

function showEnterpriseForm() {
    if($('#check-isenterprise').attr("checked") == "checked") $('#enterpriseForm').hide;
    else $('#enterpriseForm').show;
}

function alertLoginForm(object) {
    console.log(object);
}
