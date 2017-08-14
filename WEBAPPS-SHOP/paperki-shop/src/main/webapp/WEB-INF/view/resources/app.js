function register() {
    console.log("register!");
    $.ajax({
        cashe: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/login/registration",
        data: {"regisrationForm":$('#registration-form').serializeArray()},
        success: function(htmlResponse){
            $("#registration-popup").html(htmlResponse);
        },
        error: function(Response) {
            console.log(Response);
        }
    });
};

function test() {
    console.log("Test");
    $.ajax({
        cashe: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/api/test",
        data: formToJSON,
        success: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        }
    })
}



function formToJSON() {
    return JSON.stringify({
        "userId": $('#userId').val() == "" ? null : userId,
        "login": $('#login').val(),
        "password": $('#password').val(),
        "description" : $('#description').val()
    });
}
