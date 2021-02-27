/**
 * Funktionen für Login und Logout
 *
 * @author  Mia Gudelj
 * @since   2020-12-25
 * @version 1.0
 */

$( document ).ready(
    function () {

        $("#login-form").submit(sendLogin);
        $("#logoutButton").click(sendLogout);

    });

/**
 * Login
 * @param form
 */
function sendLogin(form) {

    form.preventDefault();
    var formJqObj = $("#login-form");
    var formDataObj = {};
    (function(){
        formJqObj.find(":input").not("[type='submit']").not("[type='reset']").each(function(){
            var thisInput = $(this);
            formDataObj[thisInput.attr("name")] = thisInput.val();
        });
    })();
    console.log(formDataObj);
    $.ajax({
        url: window.location.origin+"/user/login",
        contentType : "application/json",
        type: "POST",
        dataType: 'json',
        data: JSON.stringify(formDataObj)
    })

        /**
         * Request success
         */
        .done(function (jsonData) {
            window.location.href = "./pages/home.html";
        })

        /**
         * Request failed
         */
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Benutzername/Passwort unbekannt");
            } else {
                $("#message").text("Es ist ein Fehler aufgetreten");
            }
        })
}

/**
 * Logout
 */
function sendLogout() {

    $.ajax({
        type: "GET",
        url: window.location.origin+"/user/logout",
        type: "DELETE",
        dataType: "text"
    })

        /**
         * Request success
         */
        .done(function (jsonData) {
            window.location.href = "../index.html"
        })

        /**
         * Request failed
         */
        .fail(function (xhr, status, errorThrown) {
            alert("Es ist ein Fehler aufgetreten");
        })
}