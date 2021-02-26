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

       /* if (Cookies.get("userRole") == "admin") {
            showUsersButton();
        } else {
            showUsersButton();
        }*/
        loadThemes();
    }
);

/**
 * loads all the themes from the webservice
 *
 */
function loadThemes() {

    $.ajax({
        url: "./themeblock/list",
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

function showUsersButton() {

    $("#userverwaltung").html("");

    var tableData = "";
    tableData += `<a class="nav-link" href="userverwaltung.html">Benutzer</a>`;

    $("#userverwaltung").html(tableData)
    $(".nav-link").attr("style", "color: white !important");
    $("#userverwaltung").attr("style", "margin-left: 2vw !important");
}


