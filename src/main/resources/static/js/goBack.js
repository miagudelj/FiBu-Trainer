/**
 * Funktionen für back-button
 *
 * @author  Mia Gudelj
 * @since   2020-02-28
 * @version 1.0
 */

$(document).ready(
    function () {

        $(".aufgabe > #backButton").click(function () {
            window.location.href = "./unterthemen.html";
        });

        $(".unterthemen > #backButton").click(function () {
            window.location.href = "./home.html";
        });
        $(".klassen > #backButton").click(function () {
            window.location.href = "./home.html";
        });
        $(".users > #backButton").click(function () {
            window.location.href = "./klassen.html";
        });

    });
