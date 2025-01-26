/**
 * @file: notification.js
 * @author: InnoVive
* Event listener for the DOMContentLoaded event to handle the initialization of the notification badge
* and the functionality for the "Visualizza richieste" button.
 * 
 */




document.addEventListener('DOMContentLoaded', function () {
    /** @type {HTMLElement} */
    const badge = document.getElementById('notificationBadge');
    /** @type {HTMLElement} */
    const viewRequestsButton = document.getElementById('viewRequestsButton');
    
    // If the badge has a non-zero value, show it
    if (badge && badge.innerText > 0) {
        badge.style.display = 'inline';
    }

    // Handle the click event for the "Visualizza richieste" button
    if (viewRequestsButton) {
        /**
         * Event handler for the click event on the "Visualizza richieste" button.
         * It hides the notification badge and optionally updates the badge number to 0.
         * 
         * @function
         * @returns {void}
         */
        viewRequestsButton.addEventListener('click', function () {
            // Hide the notification badge when the user clicks on the "Visualizza richieste"
            badge.style.display = 'none';
            
            // Optionally, update the badge number to 0 (if you want to reflect this change on the UI)
            badge.innerText = '0';
        });
    }
});