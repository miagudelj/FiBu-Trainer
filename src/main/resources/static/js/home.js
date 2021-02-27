/**
 * view-controller for home.html
 *
 * FiBu-Trainer
 *
 * @author Mia Gudelj
 * @since 2020-01-29
 */

/**
 * register listeners and load the themes data
 */
$(document).ready(
    function () {
        loadUser();
        loadThemes();
    }
);

/**
 * loads the
 */
function loadUser() {

    $.ajax({
        //todo vini frege weg parameter
        //url: "/class/read?userID=" + userID,
        type: "GET",
        dataType: "json"
    })

        .done(
            function () {
                $("#userverwaltung").click(function () {
                    if (userID.role == "USER") {
                        modal();
                    } else {
                        window.location.href = "./klassen.html"
                    }
                });
            }
        )

        .fail(function (xhr, status, errorThrown) {
                alert("Es ist ein Fehler aufgetreten")
        })
}
/**
 * loads all the themes from the webservice
 *
 */
function loadThemes() {

    $.ajax({
        url: "/blocks/themes/list",
        type: "GET",
        dataType: "json"
    })

        .done(showTheme)

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href("../index.html");
            } else if (xhr.status == 404) {
                alert("keine Themen vorhanden");
            } else {
                alert("Fehler beim Lesen der Themen");
            }
        })
}


/**
 * shows all themes as blocks
 *
 * @param themeData all themes as an array
 */
function showTheme(themeData) {

    $(".list").html("");

    var tableData = "";

    $.each(themeData, function (themeblock) {

        tableData += `<div class="col-sm-12 col-md-4">`;
        tableData += `    <div class="block">`;
        tableData += `    <a href="./unterthemen.html">`;
        tableData += `        <h3>${themeblock.name}</h3>`;
        tableData += `    </a>`;
        tableData += `    <div class="evaluation">`;
        tableData += `        <div class="row">`;
        tableData += `            <div class="col correct">`;
        tableData += `                <i>${themeblock.questionblock.correct} / ${themeblock.questionblock.total} richtig gelöst</i>`;//TODO total questions in java
        tableData += `            </div>`;
        tableData += `        </div>`;
        tableData += `        <div class="row">`;
        tableData += `            <div class="col solved">`;
        tableData += `                <i>${themeblock.questionblock.solved} / ${themeblock.questionblock.total} gelöst</i>`; //TODO total questions in java
        tableData += `            </div>`;
        tableData += `        </div>`;
        tableData += `    </div>`;
        tableData += `</div>`;
    })
    $(".list").html(tableData);
}


function modal() {

    var modalData = "";
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

