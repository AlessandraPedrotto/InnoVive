/**
 * @file: main.js
 * @author: InnoVive
 * 
 * The script contains the logic for the responsive navbar menu toggle, chatbot, 
 * information form, status messages, and offline functionality. These 
 * are included in the main.js because they are used across all pages.
 * 
 */


/**
 * toggle function to manage responsive navbar menu
 * @returns {void} 
 */
function toggleMenu() {
    const menu = document.getElementById('mobileMenu');
    menu.classList.toggle('active');
  }

/**
 * toggle the visibility of the chatbot container
 * @returns {void} 
 */
function toggleChatbot() {
  const chatbotContainer = document.getElementById('chatbotContainer');
  chatbotContainer.style.display = (chatbotContainer.style.display === 'none' || chatbotContainer.style.display === '') ? 'flex' : 'none';

  // If chatbot is shown for the first time, display the initial message
  if (chatbotContainer.style.display === 'flex' && document.getElementById('chatMessages').childElementCount === 0) {
      showInitialMessage();
  }
}

/**
 * Toggle between languages (Italian or English)
 * @param {string} language - The selected language ('it' or 'en')
 */
function toggleLanguage(language) {
  localStorage.setItem('selectedLanguage', language);
  
  // Clear the chat messages and re-render the initial message in the selected language
  document.getElementById('chatMessages').innerHTML = ''; // Clear previous messages
  showInitialMessage(); // Re-render initial message with the new language
}

/**
* function to handle the clicked question
* @param {string} question - the selected question
* @param {object} buttonsContainer - the node element with all buttons
* @returns {void} 
*/
function handleQuestionClick(question, buttonsContainer) {
  // Disable all buttons in the current group when a button is clicked
  disableAllButtons();

  // Add user message with replay icon
  const userMessage = document.createElement('div');
  userMessage.classList.add('message', 'user');

  const userText = document.createElement('div');
  userText.classList.add('text');
  userText.textContent = question;

  // Add replay icon
  const replayIcon = document.createElement('span');
  replayIcon.innerHTML = "&#x21ba;"; // Unicode for refresh icon
  replayIcon.style.cursor = "pointer";
  replayIcon.style.marginLeft = "10px";
  replayIcon.style.fontSize = "12px";
  replayIcon.title = "Replay this question";

  replayIcon.onclick = () => {
      handleQuestionClick(question, buttonsContainer);
  };

  userText.appendChild(replayIcon); // Add the icon next to the text
  userMessage.appendChild(userText);
  document.getElementById('chatMessages').appendChild(userMessage);

  // Simulate bot response
  setTimeout(() => {
      const botMessage = document.createElement('div');
      botMessage.classList.add('message', 'bot');

      const botText = document.createElement('div');
      botText.classList.add('text');
      botText.innerHTML = getBotResponse(question);

      const buttonsContainer = document.createElement('div');
      buttonsContainer.classList.add('buttons-container');

      // Handle additional questions for "Informazioni sulla cascina"
      if (question === getLocalizedText("Informazioni sulla cascina", "Information about the farm")) {
          const followUpQuestions = [
              getLocalizedText("Dove si trova la cascina?", "Where is the farm located?"),
              getLocalizedText("Come posso contattarvi?", "How can I contact you?"),
              getLocalizedText("Come prenoto una visita?", "How can I book a visit?")
          ];

          followUpQuestions.forEach(followUp => {
              const followUpButton = document.createElement('button');
              followUpButton.textContent = followUp;
              followUpButton.onclick = () => handleQuestionClick(followUp, buttonsContainer);
              buttonsContainer.appendChild(followUpButton);
          });
      }
      
      // Handle additional questions for "La storia di Cascina Caccia"
      if (question === getLocalizedText("La storia di Cascina Caccia", "History of Cascina Caccia")) {
          const followUpQuestions = [
              getLocalizedText("Cos'è Cascina Caccia?", "What is Cascina Caccia?"),
              getLocalizedText("Chi era Bruno Caccia?", "Who was Bruno Caccia?")
          ];

          followUpQuestions.forEach(followUp => {
              const followUpButton = document.createElement('button');
              followUpButton.textContent = followUp;
              followUpButton.onclick = () => handleQuestionClick(followUp, buttonsContainer);
              buttonsContainer.appendChild(followUpButton);
          });
      }

      // Add "Ask something else" button
      const askMoreButton = document.createElement('button');
      askMoreButton.textContent = getLocalizedText("Chiedi altro", "Ask something else");
      askMoreButton.onclick = function () {
          // Disable all buttons in the current group
          disableAllButtons();

          // Add "Ask something else" as a user message
          const userMessage = document.createElement('div');
          userMessage.classList.add('message', 'user');

          const userText = document.createElement('div');
          userText.classList.add('text');
          userText.textContent = getLocalizedText("Chiedi altro", "Ask something else");
          userMessage.appendChild(userText);
          document.getElementById('chatMessages').appendChild(userMessage);

          // Show the initial message after user message is added
          showInitialMessage();

          // Scroll to the bottom of the chat
          scrollToBottom();
      };
      buttonsContainer.appendChild(askMoreButton);

      botMessage.appendChild(botText);
      botMessage.appendChild(buttonsContainer);
      document.getElementById('chatMessages').appendChild(botMessage);

      // Scroll to the bottom of the chat
      scrollToBottom();
  }, 500);
}

/**
 * Changes the language of the application and loads the corresponding translations.
 * The selected language is stored in localStorage, and the corresponding translation
 * file is fetched from the server. The translations are then applied globally.
 * 
 * @param {string} lang - The language code to switch to (e.g., 'en', 'it', etc.).
 * @throws {Error} If there is an issue with fetching the translation file.
 * 
 * @example
 * // Change the language to Italian
 * changeLanguage('it');
 */
function changeLanguage(lang) {
  // Store the selected language in localStorage
  localStorage.setItem('selectedLanguage', lang);

  // Load the corresponding JSON file for the selected language
  fetch(`/translation/${lang}.json`)
    .then(response => response.json())
    .then(translations => {
      window.translations = translations; // Store translations globally

      // Update current language
      currentLanguage = lang;

      // Trigger the language change event for the chatbot
      document.dispatchEvent(new CustomEvent('languageChange', { detail: { lang: lang } }));
    })
    .catch(error => console.error("Error loading translations:", error));
}


/**
* function that show initial message and disable the previous buttons
* @returns {void} 
*/
function showInitialMessage() {
  // Disable all previous buttons before rendering new ones
  disableAllButtons();

  const botMessage = document.createElement('div');
  botMessage.classList.add('message', 'bot');

  const botText = document.createElement('div');
  botText.classList.add('text');
  botText.textContent = getLocalizedText("Come posso aiutarti?", "How can I help you?");

  const buttonsContainer = document.createElement('div');
  buttonsContainer.classList.add('buttons-container');

  const questions = [
    getLocalizedText("La storia di Cascina Caccia", "History of Cascina Caccia"),
    getLocalizedText("Informazioni sulla cascina", "Information about the farm"),
    getLocalizedText("Attività da svolgere", "Activities to do")
  ];

  questions.forEach(question => {
    const button = document.createElement('button');
    button.textContent = question;
    button.onclick = () => handleQuestionClick(question, buttonsContainer);
    buttonsContainer.appendChild(button);
  });

  botMessage.appendChild(botText);
  botMessage.appendChild(buttonsContainer);
  document.getElementById('chatMessages').appendChild(botMessage);

  // Scroll to the bottom of the chat
  scrollToBottom();
}

/**
* function that disable all buttons from previous responses
* @returns {void} 
*/
function disableAllButtons() {
  const allButtons = document.querySelectorAll('.buttons-container button');
  allButtons.forEach(button => button.disabled = true);
  allButtons.forEach(button => button.classList.add('hidden'));
}


/**
* function that return the bot response base on user input
* @param {string} userMessage - the user input
* @returns {string} response
*/
function getBotResponse(userMessage) {
  const responses = {
      [getLocalizedText("La storia di Cascina Caccia", "History of Cascina Caccia")]: getLocalizedText("Cosa desideri sapere?", "What would you like to know?"),
      [getLocalizedText("Informazioni sulla cascina", "Information about the farm")]: getLocalizedText("Cosa desideri sapere?", "What would you like to know?"),
      [getLocalizedText("Attività da svolgere", "Activities to do")]: getLocalizedText("Oltre alle attività di sensibilizzazione sull'argomento mafia, offriamo anche laboratori manuali, che includono la raccolta dei frutti prodotti in cascina e la loro preparazione in cucina, oltre a attività artistiche per la creazione di candele. Per avere maggiori dettagli clicca <a href='http://localhost:8080/attivita' target='_blank'>qui</a>.", "In addition to activities raising awareness about mafia issues, we also offer hands-on workshops, including fruit picking from the farm and cooking, as well as artistic activities like candle-making. For more details, click <a href='http://localhost:8080/attivita' target='_blank'>here</a>."),
      [getLocalizedText("Dove si trova la cascina?", "Where is the farm located?")]: getLocalizedText("La Cascina si trova a San Sebastiano da Po, in provincia di Torino, Piemonte.", "The farm is located in San Sebastiano da Po, in the province of Turin, Piedmont."),
      [getLocalizedText("Come posso contattarvi?", "How can I contact you?")]: getLocalizedText("Puoi contattarci compilando il modulo per le informazioni, che trovi cliccando sul pulsante rotondo con la 'i' qua di lato oppure <a href='javascript:void(0);' onclick='toggleInformationForm(); toggleChatbot();'>qui</a>.", "You can contact us by filling out the information form, which you can find by clicking the round button with the 'i' or <a href='javascript:void(0);' onclick='toggleInformationForm(); toggleChatbot();'>here</a>."),
      [getLocalizedText("Come prenoto una visita?", "How can I book a visit?")]: getLocalizedText("Puoi prenotare una visita cliccando sul tasto 'Prenota' nel menu in alto oppure  <a href='http://localhost:8080/prenota' target='_blank'>qui</a>.", "You can book a visit by clicking the 'Book' button in the top menu or <a href='http://localhost:8080/prenota' target='_blank'>here</a>."),
      [getLocalizedText("Cos'è Cascina Caccia?", "What is Cascina Caccia?")]: getLocalizedText("Cascina Caccia era un bene della famiglia Belfiore, legata alla 'Ndrangheta, e fu confiscata dopo la condanna di Domenico Belfiore, mandante dell’omicidio del giudice Bruno Caccia. Oggi è un luogo simbolo di riscatto. Rappresenta la vittoria della giustizia sulla criminalità organizzata.", "Cascina Caccia was once a property of the Belfiore family, linked to the 'Ndrangheta, and was confiscated after the conviction of Domenico Belfiore, the mastermind behind the murder of Judge Bruno Caccia. Today it stands as a symbol of redemption. It represents the victory of justice over organized crime."),
      [getLocalizedText("Chi era Bruno Caccia?", "Who was Bruno Caccia?")]: getLocalizedText("Bruno Caccia era un giudice torinese ucciso nel 1983 per la sua lotta contro la criminalità organizzata. La sua morte, causata da Domenico Belfiore, mandante dell'omicidio, ha portato alla confisca di beni legati alla famiglia Belfiore, tra cui questa Cascina.", "Bruno Caccia was a Turin judge murdered in 1983 for his fight against organized crime. His death, orchestrated by Domenico Belfiore, led to the confiscation of assets tied to the Belfiore family, including this farm.")
  };

  return responses[userMessage] || getLocalizedText("Scusami, non ho capito.", "Sorry, I didn't understand.");
}

/**
* Function to return localized text based on the selected language
* @param {string} italianText - The Italian version of the text
* @param {string} englishText - The English version of the text
* @returns {string} - The text based on the current selected language
*/
function getLocalizedText(italianText, englishText) {
  return currentLanguage === 'it' ? italianText : englishText;
}

/**
* function to scroll to the bottom of the chat
* @returns {void} 
*/
function scrollToBottom() {
  const chatMessages = document.getElementById('chatMessages');
  chatMessages.scrollTop = chatMessages.scrollHeight;
}



/**
* toggle the visibility of the information form container
* @returns {void} 
*/
function toggleInformationForm() {
  const form = document.getElementById('informationFormContainer');
  if (form.classList.contains('show')) {
      form.classList.remove('show');
  }else{
      form.classList.add('show');
  }
}




/**
* Sends a heartbeat request to the server to keep the user session alive.
* This function is invoked every 10 seconds if a valid user email is found in the cookies.
* 
* @function
* @param {string} email - The user's email fetched from the cookies.
* @returns {void}
*/
const email = getCookie("userEmail");

if (email) {
   setInterval(() => {
       /**
        * Sends a POST request to the server to indicate the user is active.
        * This request is sent every 10 seconds if the user's email exists.
        * 
        * @async
        * @function
        * @returns {void}
        */
       fetch("/heartbeat", {
           method: "POST",
           headers: { "Content-Type": "application/json" },
           body: JSON.stringify({ email: email }),
       }).then((response) => {
           if (!response.ok) {
               console.error("Heartbeat failed:", response.statusText);
           }
       }).catch((error) => console.error("Heartbeat error:", error));
   }, 10000); // Every 10 seconds
}

/**
* Retrieves the value of a cookie by its name.
* 
* @function
* @param {string} name - The name of the cookie to retrieve.
* @returns {string|null} - The value of the cookie, or null if the cookie is not found.
*/
function getCookie(name) {
   const value = `; ${document.cookie}`;
   const parts = value.split(`; ${name}=`);
   if (parts.length === 2) return parts.pop().split(";").shift();
}

  
// STATUS MESSAGGES

document.addEventListener('DOMContentLoaded', function () {
  
  const successMessage = document.querySelector('[data-success]')?.getAttribute('data-success');
  const errorMessage = document.querySelector('[data-error]')?.getAttribute('data-error');
  const errorSession = document.querySelector('[data-error-session]')?.getAttribute('data-error-session');
  const noResultsMessage = document.querySelector('[data-no-results]')?.getAttribute('data-no-results');
  const noMessageMessage = document.querySelector('[data-no-message]')?.getAttribute('data-no-message');

  const popup = document.getElementById('custom-popup');
  const popupLogo = document.getElementById('popup-logo');
  const popupClaim = document.getElementById('popup-claim');
  const popupMessage = document.getElementById('popup-message');
  const popupClose = document.getElementById('popup-close');
  let popupTimeout;

  const succesLogo = '<svg width="100px" fill="#18d100" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 98.25 98.25" xml:space="preserve" stroke="#18d100"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <g> <g> <path d="M49.125,0C22.037,0,0,22.038,0,49.125S22.037,98.25,49.125,98.25S98.25,76.212,98.25,49.125S76.213,0,49.125,0z M49.125,88.25C27.551,88.25,10,70.699,10,49.125S27.551,10,49.125,10S88.25,27.551,88.25,49.125S70.699,88.25,49.125,88.25z"></path> <path d="M77.296,33.027L71.02,26.75c-0.442-0.442-1.227-0.442-1.668,0L39.67,56.432L28.898,45.661 c-0.441-0.442-1.225-0.442-1.668,0l-6.276,6.276c-0.222,0.222-0.346,0.521-0.346,0.834c0,0.313,0.124,0.613,0.346,0.834 l17.882,17.881c0.23,0.229,0.531,0.346,0.834,0.346c0.301,0,0.604-0.115,0.834-0.346l36.792-36.792 c0.222-0.221,0.347-0.521,0.347-0.834S77.518,33.248,77.296,33.027z"></path> </g> </g> </g></svg>';
  const errorLogo = '<svg width="100px" viewBox="0 0 512 512" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#e00000" stroke="#e00000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <title>error</title> <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"> <g id="add" fill="#db0000" transform="translate(42.666667, 42.666667)"> <path d="M213.333333,3.55271368e-14 C331.136,3.55271368e-14 426.666667,95.5306667 426.666667,213.333333 C426.666667,331.136 331.136,426.666667 213.333333,426.666667 C95.5306667,426.666667 3.55271368e-14,331.136 3.55271368e-14,213.333333 C3.55271368e-14,95.5306667 95.5306667,3.55271368e-14 213.333333,3.55271368e-14 Z M213.333333,42.6666667 C119.232,42.6666667 42.6666667,119.232 42.6666667,213.333333 C42.6666667,307.434667 119.232,384 213.333333,384 C307.434667,384 384,307.434667 384,213.333333 C384,119.232 307.434667,42.6666667 213.333333,42.6666667 Z M262.250667,134.250667 L292.416,164.416 L243.498667,213.333333 L292.416,262.250667 L262.250667,292.416 L213.333333,243.498667 L164.416,292.416 L134.250667,262.250667 L183.168,213.333333 L134.250667,164.416 L164.416,134.250667 L213.333333,183.168 L262.250667,134.250667 Z" id="error"> </path> </g> </g> </g></svg>';
  const noResultLogo = '<img width="100px" src="/img/question-mark.svg" alt="question icon">'

  // Function to determine which message to show
  function getPopupClaim(type) {
    const isSimplifiedPage = document.body.classList.contains('simplified-popup');
    if (isSimplifiedPage) {
        if (type === 'success') return successUserClaim || 'Successo!';
        if (type === 'error') return  errorClaim || 'Attenzione!';
        if (type === 'no-result') return 'Nessun risultato';
    } else {
      if (type === 'success') return successClaim || 'Grazie per averci contattato!';
        if (type === 'error') return errorClaim || 'Attenzione!';
        if (type === 'no-result') return 'Nessun risultato';
    }
    return '';
  }

  // Function to get the translation for a specific key
  function getTranslation(key) {
    return window.translations?.[key] || key;
  }
    
  function showPopup(message) {
      popupMessage.textContent = message;
      popup.classList.remove('hidden');

      popupTimeout = setTimeout(() => {
          closePopup();  // popup closing after 5 seconds
      }, 5000);
  }

  function closePopup() {
      popup.classList.add('hidden');
      popupMessage.textContent = ""; 
      clearTimeout(popupTimeout); 
  }
  // closing popup by user interaction
  popupClose.addEventListener('click', function () {
      closePopup();  
  });
  // Display the message in a popup
  if (successMessage) {
      showPopup(successMessage);
      popupLogo.innerHTML = succesLogo;
      popupClaim.textContent = getTranslation('popup.success.claim');
  } else if (errorMessage) {
      showPopup(errorMessage);
      popupLogo.innerHTML = errorLogo;
      popupClaim.textContent = getTranslation('popup.error.claim');
  }  else if (errorSession) {
      showPopup(errorSession);
      popupLogo.innerHTML = errorLogo;
      popupClaim.textContent = getTranslation('popup.error.claim');
  } else if (noResultsMessage) {
      showPopup(noResultsMessage)
      popupLogo.innerHTML = noResultLogo
      popupClaim.textContent = getTranslation('popup.no.result'); // Display the no results message
  } else if (noMessageMessage) {
      showPopup(noMessageMessage)
      popupLogo.innerHTML = noResultLogo
      popupClaim.textContent = getTranslation('popup.no.result'); // Display the no results message
  }
});
