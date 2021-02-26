/**
 * view-controller for klassen.html
 *
 * FiBu-Trainer
 *
 * @author Mia Gudelj
 * @since 2020-02-26
 */

/**
 * register listeners and load the animal data
 */
$(document).ready(
    function() {
        loadZoos();
        loadTier();

        /**
         * listener for submitting the form
         */
        $("#tiereditForm").submit(saveTier);

        /**
         * listener for button [abbrechen], redirects to gehege
         */
        $("#cancel").click(function () {
            window.location.href = "./gehege.html";
        });
    }
);