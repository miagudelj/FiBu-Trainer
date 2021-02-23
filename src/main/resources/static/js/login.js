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
    var formData = {
        username : $("#username").val(),
        password : $("#password").val()
    }
    $.ajax({
        url: window.location.origin+"/user/login",
        contentType : "application/json",
        type: "POST",
        dataType: 'json',
        data: JSON.stringify(formData)
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
        url: "/user/logout",
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
            $("#message").text("Es ist ein Fehler aufgetreten");
        })
}