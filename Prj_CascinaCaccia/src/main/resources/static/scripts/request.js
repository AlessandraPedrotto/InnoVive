/**
 * @file: request.js
 * @author: InnoVive
 * Toggles visibility of various elements on the page when certain actions are performed.
 * The script listens for `click` events and toggles the `hidden` class for corresponding elements.
 * 
 * - Handles the visibility toggle for request headers and bodies.
 * - Handles the visibility toggle for user assignment forms.
 * - Handles the visibility toggle for status assignment forms.
 * - Handles the visibility toggle for closing the user and status assignment forms via 'X' buttons.
 * 
 * @function
 * @listens {DOMContentLoaded} - Waits for the DOM to fully load before attaching event listeners.
 */
document.addEventListener('DOMContentLoaded', () => {
    /**
     * Toggles the 'hidden' class on the request body and header when a request header is clicked.
     * Only one request body is visible at a time, and clicking another header will close the current body.
     * 
     * @param {HTMLElement} headers - The headers of the requests.
     * @param {HTMLElement} bodies - The corresponding bodies of the requests.
     */
    const headers = document.querySelectorAll('.request-header');
    const bodies = document.querySelectorAll('.request-body');

    for (let i = 0; i < headers.length; i++) {
        headers[i].addEventListener('click', () => {
            // Close previously opened body if a different header is clicked
            for (let j = 0; j < headers.length; j++) {
                if (headers[j].classList.contains('request-header-selected') && j !== i) {
                    headers[j].classList.toggle('request-header-selected');
                    bodies[j].classList.toggle('hidden');
                }
            }
            bodies[i].classList.toggle('hidden');
            headers[i].classList.toggle('request-header-selected');
        });
    };

    /**
     * Toggles the 'hidden' class on the user assignment form when the corresponding button is clicked.
     * 
     * @param {HTMLElement} assignUserButton - The button to trigger the user assignment form toggle.
     * @param {HTMLElement} assignUserContent - The content of the user assignment form.
     */
    const assignUserButton = document.querySelectorAll('.assign-user-button');
    const assignUserContent = document.querySelectorAll('.assign-user-content');

    for (let i = 0; i < assignUserButton.length; i++) {
        assignUserButton[i].addEventListener('click', () => {
            assignUserContent[i].classList.toggle('hidden');
        });
    }

    /**
     * Toggles the 'hidden' class on the status assignment form when the corresponding button is clicked.
     * 
     * @param {HTMLElement} assignStatusButton - The button to trigger the status assignment form toggle.
     * @param {HTMLElement} assignStatusForm - The content of the status assignment form.
     */
    const assignStatusButton = document.querySelectorAll('.assign-status-button');
    const assignStatusForm = document.querySelectorAll('.assign-status-content');

    for (let i = 0; i < assignStatusButton.length; i++) {
        assignStatusButton[i].addEventListener('click', () => {
            assignStatusForm[i].classList.toggle('hidden');
        });
    }

    /**
     * Toggles the 'hidden' class on the user assignment form when the close button is clicked.
     * 
     * @param {HTMLElement} closeUserAssignmentButtons - The buttons used to close the user assignment form.
     * @param {HTMLElement} assignUserContent - The content of the user assignment form.
     */
    const closeUserAssignmentButtons = document.querySelectorAll('.close-user-assignment');
    
    for (let i = 0; i < closeUserAssignmentButtons.length; i++) {
        closeUserAssignmentButtons[i].addEventListener('click', () => {
            assignUserContent[i].classList.toggle('hidden');
        });
    }

    /**
     * Toggles the 'hidden' class on the status assignment form when the close button is clicked.
     * 
     * @param {HTMLElement} closeStatusAssignmentButtons - The buttons used to close the status assignment form.
     * @param {HTMLElement} assignStatusForm - The content of the status assignment form.
     */
    const closeStatusAssignmentButtons = document.querySelectorAll('.close-status-assignment');

    for (let i = 0; i < closeStatusAssignmentButtons.length; i++) {
        closeStatusAssignmentButtons[i].addEventListener('click', () => {
            assignStatusForm[i].classList.toggle('hidden');
        });
    }
});
