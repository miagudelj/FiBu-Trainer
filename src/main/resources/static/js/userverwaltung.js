/**
 * Funktionen für back-button
 *
 * @author  Mia Gudelj
 * @since   2020-02-28
 * @version 1.0
 */

$(document).ready(
    function () {
        $("#userverwaltung").click(function () {
            if (Cookies.get("userRole") == "USER") {
                modal();
            } else {
                window.location.href = "./klassen.html";
            }
        });
    });

/**
 * opens modal for changing password
 */
function modal() {

    var modalData = "";

    //TODO kann nicht stimmen
    function modal(userID, benutzer) {
        modalData += `                <!-- modal -->`;
        modalData += `                <div class="modal fade" id="editPasswordModal" tabindex="-1" role="dialog" aria-labelledby="ModalLongTitle" aria-hidden="true">`;
        modalData += `                    <div class="modal-dialog" role="document">`;
        modalData += `                        <div class="modal-content">`;
        modalData += `                            <div class="modal-header">`;
        modalData += `                                <h3 class="modal-title" id="ModalLongTitle">Passwort ändern</h3>`;
        modalData += `                            </div>`;
        modalData += `                            <div class="modal-body">`;
        modalData += `                                <!-- form -->`;
        modalData += `                                <div class="form-group">`;
        modalData += `                                    <input type="text" name="password" id="password" class="form-control" placeholder="Neues Passwort" value="'${benutzer.password}'" autofocus required/>`;
        modalData += `                                    <input type="text" name="password" id="password" class="form-control" placeholder="Passwort wiederholen" autofocus required/>`;
        modalData += `                                </div>`;
        modalData += `                            </div>`;
        modalData += `                            <div class="modal-footer">`;
        modalData += `                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen </button>`;
        modalData += `                                <button type="button" class="btn btn-primary" id="savePasswordButton">Speichern</button>`;
        modalData += `                            </div>`;
        modalData += `                        </div>`;
        modalData += `                    </div>`;
        modalData += `                </div>`;
    }

    $(".savePasswordButton").click(savePassword);
    $(".container").html(modalData);
}

/**
 * saves new password from student
 * @param form
 */
//TODO funktioniert nicht
function savePassword(form) {

    form.preventDefault();

    $.ajax({
        url: "/user/update",
        dataType: "text",
        type: "PUT",
        data: $("#password").serialize()
    })

        .done(function (jsonData) {
            window.location.href = "./home.html";
        })

        .fail(function (xhr, status, errorThrown) {
            alert("Fehler beim Speichern des Passworts");
        })
}

