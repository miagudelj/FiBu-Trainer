/**
 * view-controller for users.html
 *
 * FiBu-Trainer
 *
 * @author Mia Gudelj
 * @since 2020-02-24
 */

/**
 * register listeners and load the user data
 */
$(document).ready(
    function () {
        loadBenutzer();
        loadKlassen();


        $("#saveUserButton").click(saveBenutzer);

        /**
         * listener for buttons (edit and delete) within blocks
         */
        // todo
        $("#deleteBenutzerButton").click(function () {
            if (confirm("Wollen Sie diese Benutzer wirklich löschen?")) {
                deleteBenutzer(this.value);
            }
        });
    }
);

/**
 * loads the Benutzer from the webservice *
 */

function loadBenutzer() {

    $.ajax({
        url: "/user/list",
        type: "GET",
        dataType: "json"
    })

        .done(listBenutzer)

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href("./klassen.html");
            } else if (xhr.status == 404) {
                alert("keine Benutzer vorhanden");
            } else {
                alert("Fehler beim Lesen der Benutzer");
            }
        })
}


/**
 * shows the data of this user
 * @param  tier  the user data to be shown
 */
function showBenutzer(user) {

    $("#username").val(tier.tierUUID);
    $("#art").val(tier.art);
    $("#name").val(tier.name);
    $("#geburtsdatum").val(tier.geburtsdatum);
    $("#alter").val(tier.alter);
    $("#beine").val(tier.beine);
    $("#fell").prop("checked", tier.fell);
    $("#zoo").val(tier.zoo.zooUUID);

    if (Cookies.get("userRole") != "admin") {
        $("#art, #name, #geburtsdatum, #alter, #beine, #fell, #zoo").prop("readonly", true);
        $("#save, #reset").prop("disabled", true);
    }
}

/**
 * shows all Benutzer as blocks
 *
 * @param benutzerData all Benutzer as an array
 */
function listBenutzer(benutzerData) {

    $(".list").html("");

    var modalData = "";

    $.each(benutzerData, function (userID, benutzer) {
        modalData += `<div class="col-sm-12 col-md-3">`;
        modalData += `    <div class="block">`;
        modalData += `        <div class="row">`;
        modalData += `            <div class="col-8">`;
        modalData += `                <a href='./users.html?userID=" + userID + "'>`;
        modalData += `                    <h3>${benutzer.name}</h3>`;
        modalData += `                </a>`;
        modalData += `            </div>`;
        modalData += `            <div class="col-2">`;
        modalData += `                <button class="btn edit" id="editButton" title="Benutzer bearbeiten" data-toggle="modal" data-target="#editUserModal">`;
        modalData += `                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">`;
        modalData += `                        <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>`;
        modalData += `                    </svg>`;
        modalData += `                </button>`;
        modalData += `                <!-- modal -->`;
        modalData += `                <div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="ModalLongTitle" aria-hidden="true">`;
        modalData += `                    <div class="modal-dialog" role="document">`;
        modalData += `                        <div class="modal-content">`;
        modalData += `                            <div class="modal-header">`;
        modalData += `                                <h3 class="modal-title" id="ModalLongTitle">Benutzer bearbeiten</h3>`;
        modalData += `                            </div>`;
        modalData += `                            <div class="modal-body">`;
        modalData += `                                <!-- form -->`;
        modalData += `                                <div class="form-group">`;
        modalData += `                                    <input type="text" name="username" id="username" class="form-control" placeholder="Username" value="'${benutzer.username}'" autofocus required/>`;
        modalData += `                                    <input type="text" name="password" id="password" class="form-control" placeholder="Passwort" autofocus required/>`;
        modalData += `                                    <div class="row">`;
        modalData += `                                        <div class="col-6">`;
        modalData += `                                            <select class="form-control" id="roleSelect">`;
        modalData += `                                                <option>Admin</option>`;/*todo need?*/
        modalData += `                                                <option>Schüler</option>`;/*todo need?*/
        modalData += `                                            </select>`;
        modalData += `                                        </div>`;
        modalData += `                                        <div class="col-6" id="klasseSelect">`;
        modalData += `                                            <select class="form-control" size="5">`;
        modalData += `                                            ${benutzer}`; //todo get class
        modalData += `                                            </select>`;
        modalData += `                                        </div>`;
        modalData += `                                    </div>`;
        modalData += `                                </div>`;
        modalData += `                            </div>`;
        modalData += `                            <div class="modal-footer">`;
        modalData += `                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen </button>`;
        modalData += `                                <button type="button" class="btn btn-primary" id="saveUserButton">Speichern</button>`;
        modalData += `                            </div>`;
        modalData += `                        </div>`;
        modalData += `                    </div>`;
        modalData += `                </div>`;
        modalData += `            </div>`;
        modalData += `            <div class="col-2">`;
        modalData += `                <button class='btn delete' id='deleteBenutzerButton" + userID + "' value='" + userID + "' title='Benutzer löschen'>`;
        modalData += `                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">`;
        modalData += `                        <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>`;
        modalData += `                    </svg>`;
        modalData += `                </button>`;
        modalData += `            </div>`;
        modalData += `        </div>`;
        modalData += `    </div>`;
        modalData += `</div>`;

    })
    $(".list").html(modalData);
}

/**
 * send delete request for a Benutzer
 * @param userID
 */
function deleteBenutzer(userID) {
    $.ajax({
        //TODO frege /user/delete?{userID}
        url: "/user/delete?userID=" + userID,
        dataType: "text",
        type: "DELETE",
    })

        .done(function (data) {
            loadBenutzer();
            alert("Benutzer gelöscht");

        })

        .fail(function (xhr, status, errorThrown) {
            alert("Fehler beim Löschen der Benutzer");
        })
}

/**
 * send insert request for a new Benutzer
 */
function saveBenutzer(form) {

    form.preventDefault();
    var userID = $.urlParam("userID");

    var url = "/user/";
    var type = "";

    if (userID) {
        //TODO was ish mit dem?
        url += "update";
        type = "PUT";
    } else {
        url += "save";
        type = "POST";
    }

    $.ajax({
        url: url,
        dataType: "text",
        type: type,
        data: $("#saveUserButton").serialize()
    })

        .done(function (jsonData) {
            window.location.href = "./users.html";
        })

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                alert("Dieser Benutzer existiert nicht");
            } else {
                alert("Fehler beim Speichern des Benutzers");
            }
        })
}

/**
 * loads all Klassen for list in dropbox from the webservice
 */
function loadKlassen() {
    $.ajax({
        url: "/class/list",
        dataType: "json",
        type: "GET"
    })
        .done(showKlassen())

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                alert("Keine Klasse gefunden");
            } else {
                window.location.href = "./users.html";
            }
        })
}

/**
 * lists all Klassen in dropdown
 * @param klassen
 */
function showKlassen(klassen) {

    $.each(klassen, function (classID, klasse) {
        $('#klasseSelect').append($('<option>', {
            value: klasse.classID,
            text: klasse.name
        }));
    });
}

/**
 * lists all roles in dropdown from the webservice
 * @param roles
 */
//todo do we need this ?
/*
function showRoles(roles) {

    $.each(roles, function (role) {
        $('#klasseSelect').append($('<option>', {
            value: role.??,
            text : ?
        }));
    });
}*/