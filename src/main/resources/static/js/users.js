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


        $("#saveBenutzer").click(saveBenutzer());

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
 * loads the Benutzer from the webservice
 *
 */

function loadBenutzer() {

    $.ajax({
        // TODO url korrigieren
        url: "./benutzer/list",
        type: "GET",
        dataType: "json"
    })

        .done(showBenutzer)

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href("./userverwaltung.html");
            } else if (xhr.status == 404) {
                alert("keine Benutzer vorhanden");
            } else {
                alert("Fehler beim Lesen der Benutzer");
            }
        })
}


/**
 * shows all Benutzern as blocks
 *
 * @param benutzerData all Benutzern as an array
 */
function showBenutzer(benutzerData) {

    $(".list").html("");

    var blockData = "";

    $.each(benutzerData, function (benutzerUUID, benutzer) {
        // TODO usertype?
        if (Cookies.get("userType") == "ADMIN") {
            blockData += `<div class="col-sm-12 col-md-3">`;
            blockData += `    <div class="block">`;
            blockData += `        <div class="row">`;
            blockData += `            <div class="col-8">`;
            blockData += `                <a href='./users.html?benutzerUUID=" + benutzerUUID + "'>`;
            blockData += `                    <h3>${benutzer.name}1</h3>`;
            blockData += `                </a>`;
            blockData += `            </div>`;
            blockData += `            <div class="col-2">`;
            blockData += `                <button class="btn edit" id="editButton" title="Benutzer bearbeiten" >`;
            blockData += `                    <a class='btn' role='button' href='./benutzerEdit.html?benutzerUUID=" + benutzerUUID + "'>`;
            blockData += `                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">`;
            blockData += `                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>`;
            blockData += `                        </svg>`;
            blockData += `                    </a>`;
            blockData += `                </button>`;
            blockData += `            </div>`;
            blockData += `            <div class="col-2">`;
            blockData += `                <button class='btn delete' id='deleteBenutzerButton" + benutzerUUID + "' value='" + benutzerUUID + "' title='Benutzer löschen'>`;
            blockData += `                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">`;
            blockData += `                        <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>`;
            blockData += `                    </svg>`;
            blockData += `                </button>`;
            blockData += `            </div>`;
            blockData += `        </div>`;
            blockData += `    </div>`;
            blockData += `</div>`;

        } else {
            // TODO ändere
            alert("Was wetsh du alter");

        }
    })
    $(".list").html(blockData);
}


/**
 * send delete request for a Benutzer
 * @param benutzerUUID
 */
function deleteBenutzer(benutzerUUID) {
    $.ajax({
        // TODO richtige url korrigieren
        url: "benutzer/delete?benutzerUUID=" + benutzerUUID,
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
//TODO parameter needed?
function saveBenutzer() {
    //TODO
}