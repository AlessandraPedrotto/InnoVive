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
    const succesLogo = '<svg width="100px" fill="#18d100" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 98.25 98.25" xml:space="preserve" stroke="#18d100"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <g> <g> <path d="M49.125,0C22.037,0,0,22.038,0,49.125S22.037,98.25,49.125,98.25S98.25,76.212,98.25,49.125S76.213,0,49.125,0z M49.125,88.25C27.551,88.25,10,70.699,10,49.125S27.551,10,49.125,10S88.25,27.551,88.25,49.125S70.699,88.25,49.125,88.25z"></path> <path d="M77.296,33.027L71.02,26.75c-0.442-0.442-1.227-0.442-1.668,0L39.67,56.432L28.898,45.661 c-0.441-0.442-1.225-0.442-1.668,0l-6.276,6.276c-0.222,0.222-0.346,0.521-0.346,0.834c0,0.313,0.124,0.613,0.346,0.834 l17.882,17.881c0.23,0.229,0.531,0.346,0.834,0.346c0.301,0,0.604-0.115,0.834-0.346l36.792-36.792 c0.222-0.221,0.347-0.521,0.347-0.834S77.518,33.248,77.296,33.027z"></path> </g> </g> </g></svg>';
    /** @type {string} */
    const errorLogo = '<svg width="100px" viewBox="0 0 512 512" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#e00000" stroke="#e00000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>error</title> <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"> <g id="add" fill="#db0000" transform="translate(42.666667, 42.666667)"> <path d="M213.333333,3.55271368e-14 C331.136,3.55271368e-14 426.666667,95.5306667 426.666667,213.333333 C426.666667,331.136 331.136,426.666667 213.333333,426.666667 C95.5306667,426.666667 3.55271368e-14,331.136 3.55271368e-14,213.333333 C3.55271368e-14,95.5306667 95.5306667,3.55271368e-14 213.333333,3.55271368e-14 Z M213.333333,42.6666667 C119.232,42.6666667 42.6666667,119.232 42.6666667,213.333333 C42.6666667,307.434667 119.232,384 213.333333,384 C307.434667,384 384,307.434667 384,213.333333 C384,119.232 307.434667,42.6666667 213.333333,42.6666667 Z M262.250667,134.250667 L292.416,164.416 L243.498667,213.333333 L292.416,262.250667 L262.250667,292.416 L213.333333,243.498667 L164.416,292.416 L134.250667,262.250667 L183.168,213.333333 L134.250667,164.416 L164.416,134.250667 L213.333333,183.168 L262.250667,134.250667 Z" id="error"> </path> </g> </g> </g></svg>';
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
