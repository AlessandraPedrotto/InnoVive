/**
 * @file: message.js
 * @author: InnoVive
 * Event listener for the DOMContentLoaded event that handles the display of custom popup messages.
 * The popup message is dynamically populated based on different scenarios like success, error, no results, or no message.
 */

document.addEventListener('DOMContentLoaded', function () {
    /** @type {string|null} */
    const successMessage = document.querySelector('[data-success]')?.getAttribute('data-success');
    /** @type {string|null} */
    const errorMessage = document.querySelector('[data-error]')?.getAttribute('data-error');
    /** @type {string|null} */
    const noResultsMessage = document.querySelector('[data-no-results]')?.getAttribute('data-no-results');
    /** @type {string|null} */
    const noMessageMessage = document.querySelector('[data-no-message]')?.getAttribute('data-no-message');
  
    /** @type {HTMLElement} */
    const popup = document.getElementById('custom-popup');
    /** @type {HTMLElement} */
    const popupLogo = document.getElementById('popup-logo');
    /** @type {HTMLElement} */
    const popupClaim = document.getElementById('popup-claim');
    /** @type {HTMLElement} */
    const popupMessage = document.getElementById('popup-message');
    /** @type {HTMLElement} */
    const popupClose = document.getElementById('popup-close');
    
    let popupTimeout;
  
    /** @type {string} */
    const succesLogo = '<svg width="100px" fill="#18d100" version="1.1" ... </svg>';  // SVG for success
    /** @type {string} */
    const errorLogo = '<svg width="100px" fill="#e00000" version="1.1" ... </svg>';  // SVG for error
    /** @type {string} */
    const noResultLogo = '<img width="100px" src="/img/question-mark.svg" alt="question icon">';
  
    /**
     * Returns the appropriate claim text based on the type of message.
     * 
     * @param {string} type - The type of message: 'success', 'error', or 'no-result'.
     * @returns {string} The message to display in the popup.
     */
    function getPopupClaim(type) {
        const isSimplifiedPage = document.body.classList.contains('simplified-popup');
        if (isSimplifiedPage) {
            if (type === 'success') return successUserClaim || 'Successo!';
            if (type === 'error') return errorClaim || 'Attenzione!';
            if (type === 'no-result') return 'Nessun risultato';
        } else {
            if (type === 'success') return successClaim || 'Grazie per averci contattato!';
            if (type === 'error') return errorClaim || 'Attenzione!';
            if (type === 'no-result') return 'Nessun risultato';
        }
        return '';
    }
  
    /**
     * Retrieves the translation for a given key.
     * 
     * @param {string} key - The key for the translation to retrieve.
     * @returns {string} The translated text, or the key if not found.
     */
    function getTranslation(key) {
        return window.translations?.[key] || key;
    }
  
    /**
     * Displays the popup with the specified message.
     * The popup will automatically close after 5 seconds.
     * 
     * @param {string} message - The message to display in the popup.
     */
    function showPopup(message) {
        popupMessage.textContent = message;
        popup.classList.remove('hidden');
  
        popupTimeout = setTimeout(() => {
            closePopup();  // Popup closing after 5 seconds
        }, 5000);
    }
  
    /**
     * Closes the popup and clears any active timeouts.
     */
    function closePopup() {
        popup.classList.add('hidden');
        popupMessage.textContent = ""; 
        clearTimeout(popupTimeout); 
    }
  
    // Closing popup by user interaction
    popupClose.addEventListener('click', function () {
        closePopup();  
    });
  
    // Display the message in a popup based on the type of message present in the HTML
    if (successMessage) {
        showPopup(successMessage);
        popupLogo.innerHTML = succesLogo;
        popupClaim.innerHTML = getPopupClaim('popup.success.claim');
    } else if (errorMessage) {
        showPopup(errorMessage);
        popupLogo.innerHTML = errorLogo;
        popupClaim.innerHTML = getPopupClaim('popup.error.claim');
    } else if (noResultsMessage) {
        showPopup(noResultsMessage);
        popupLogo.innerHTML = noResultLogo;
        popupClaim.innerHTML = getPopupClaim('popup.no.result'); // Display the no results message
    } else if (noMessageMessage) {
        showPopup(noMessageMessage);
        popupLogo.innerHTML = noResultLogo;
        popupClaim.innerHTML = getPopupClaim('popup.no.result'); // Display the no results message
    }
});
