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
        loadThemes();

        /**
         * listener for buttons within themeForm
         */
        // TODO DO WE NEED THIS?
        /*
        $("#tierForm").on("click", "button", function () {
            if (confirm("Wollen Sie dieses Buch wirklich löschen?")) {
                deleteTier(this.value);
            }
        });*/
    }
);

/**
 * loads all the themes from the webservice
 *
 */
function loadThemes() {

    $.ajax({
        url: "./zoo/list",
        type: "GET",
        dataType: "json"
    })

        .done(showTheme)

        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href("../index.html");
            } else if (xhr.status == 404) {
                $("#message").text("keine Themen vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen der Themen");
            }
        })
}


/**
 * shows all themes as blocks
 *
 * @param themeData all themes as an array
 */
function showTheme(themeData) {

    $("#message").empty();
    $("#blocks > .list").html("");

    var tableData = "";

    $.each(themeData, function (themeUUID, theme) {

        tableData += `<div class="col-sm-12 col-md-4">`;
        tableData += `    <div class="block">`;
        tableData += `    <a href="./unterthemen.html">`;
        tableData += `        <h3>${theme.name}</h3>`;
        tableData += `    </a>`;
        tableData += `    <div class="evaluation">`;
        tableData += `        <div class="row">`;
        tableData += `            <div class="col correct">`;
        tableData += `                <i>${theme.correct} / ${theme.total} richtig gelöst</i>`;
        tableData += `            </div>`;
        tableData += `        </div>`;
        tableData += `        <div class="row">`;
        tableData += `            <div class="col solved">`;
        tableData += `                <i>${theme.solved} / ${theme.total} gelöst</i>`;
        tableData += `            </div>`;
        tableData += `        </div>`;
        tableData += `    </div>`;
        tableData += `</div>`;
    })
    $("#blocks > .list").html(tableData);
}


