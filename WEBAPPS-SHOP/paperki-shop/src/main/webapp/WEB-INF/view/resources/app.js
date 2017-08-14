function register() {
    console.log("register!");
    $.ajax({
        case: false,
        async: false,
        type: "POST",
        contentType: "application/json",
        dataType: "html",
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


/*
function formToJSON() {
    return JSON.stringify({
        "userId": $('#userId').val() == "" ? null : userId,
        "login": $('#login').val(),
        "password": $('#password').val(),
        "description" : $('#description').val()
    });
}*/
