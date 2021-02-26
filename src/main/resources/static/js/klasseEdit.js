/**
 * view-controller for klasseEdit.html
 *
 * FiBu-Trainer
 *
 * @author Mia Gudelj
 * @since 2020-02-26
 */

/**
 * register listeners and load the klassen and user data
 */
$(document).ready(
    function() {
        loadKlasse();
        loadBenutzer();

        /**
         * listener for saving
         */
        $("#saveKlasse").click(saveKlasse);

        /**
         * listener for button [abbrechen], redirects to klassen
         */
        $("#cancel").click(function () {
            window.location.href = "./klassen.html";
        });
    }
);


/**
 *  loads the data of this klasse
 *
 */
function loadKlasse() {

    var classID = $.urlParam("classID");

    if (classID !== null && classID != -1) {
        $.ajax({
            url: "./class/read?classID=" + classID,
            type: "GET",
            dataType: "json"
        })

            .done(showKlasse)

            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 403) {
                    window.location.href = "../index.html";
                } else if (xhr.status == 404) {
                    alert("kein Klasse gefunden");
                } else {
                    window.location.href = "./klassen.html";
                }
            })
    }
}


/**
 * shows the data of this klasse
 * @param  klasse  the klasse data to be shown
 */
function showKlasse(klasse) {

    $("#klassenname").val(klasse.name);
    // TODO in dropdown select all students who are in this class
}

/**
 * sends the class data to the webservice
 * @param form the form being submitted
 */
function saveKlasse(form) {
    form.preventDefault();

    $.ajax({
        url: "./class/update",
        dataType: "text",
        type: "PUT",
        data: $("#saveKlasse").serialize()
    })

        .done(function (jsonData) {
            window.location.href = "./klassen.html";
        })

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
               alert("Diese Klasse exisklasset nicht");
            } else {
               alert("Fehler beim Speichern der Klasse");
            }
        })
}

/**
 * loads the data of the users
 */
function loadBenutzer() {
    $.ajax({
        url: "./user/list",
        dataType: "json",
        type: "GET"
    })
        .done(showBenutzer)

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
               alert("Kein Benutzer gefunden");
            } else {
                window.location.href = "./klassen.html";
            }
        })
}

/**
 * shows the data of the users
 *
 * @param users
 */
function showBenutzer(users) {

    $.each(users, function (userID, user) {
        $('#dropdownBenutzer').append($('<option>', {
            value: user.userID,
            text : user.username
        }));
    });
}
