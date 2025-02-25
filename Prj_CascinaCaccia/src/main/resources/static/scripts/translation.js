/**
 * @file: translation.js
 * @author: InnoVive
 * This script handles changing the language of the website.
 * It allows users to select a language, store their choice in localStorage, 
 * and updates the page content by loading a translation file.
 * It also updates the language in the URL and on the page.
 * 
 * - Saves the selected language in localStorage.
 * - Loads the translation file for the selected language.
 * - Updates the text content of page elements based on the language.
 * - Updates the language flag and text for language switching.
 * 
 * @function
 * @listens {DOMContentLoaded} - Waits for the page to load before running the code.
 */
let currentLanguage = localStorage.getItem('selectedLanguage') || 'it';

/**
 * Changes the language of the page by loading a translation file 
 * and updating the content of all relevant elements.
 * 
 * @param {string} lang - The language code (e.g., 'it' for Italian, 'en' for English).
 */
function changeLanguage(lang) {
  // Save the selected language in localStorage
  localStorage.setItem('selectedLanguage', lang);
  currentLanguage = lang;

  // Update the 'lang' attribute on the HTML element
  document.documentElement.setAttribute('lang', lang);

  // Update the URL with the selected language as a query parameter
  const currentUrl = new URL(window.location.href);
  currentUrl.searchParams.set('lang', lang);
  window.history.replaceState({}, '', currentUrl.toString());

  // Load the translation file for the selected language
  fetch(`/translation/${lang}.json`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Translation file for ${lang} could not be loaded.`);
      }
      return response.json();
    })
    .then(translations => {
      localStorage.setItem(`translations_${lang}`, JSON.stringify(translations));

      // Update all elements that need to be translated
      const elementsToTranslate = document.querySelectorAll("[data-translate]");
      elementsToTranslate.forEach(element => {
        const translationKey = element.getAttribute("data-translate");
        if (translations[translationKey]) {
          // Update text content for non-input elements
          if (element.hasAttribute("data-translate-html")) {
            element.innerHTML = translations[translationKey]; // Allow HTML translations
          } else if (element.tagName === "OPTION") {
            // Handle <option> elements in dropdowns
            element.textContent = translations[translationKey]; 
          } else if (element.tagName !== "INPUT" && element.tagName !== "TEXTAREA") {
            element.textContent = translations[translationKey];
          } else {
            // Update placeholder for input and textarea elements
            element.placeholder = translations[translationKey];
          }
        }
      });

      // Update UI with translations (if necessary)
      updateUIWithTranslations(translations);

      // Trigger the custom language change event
      document.dispatchEvent(new CustomEvent('languageChange', { detail: { lang: lang } }));
    })
    .catch(error => {
      console.error("Error loading translations:", error);
      changeLanguage('it'); // Default to Italian if there's an error
    });
}

/**
 * Sets the language flag on the page based on the saved language in localStorage.
 */
function setLangFlagFromStorage() {
  let langFlagValue = localStorage.getItem('selectedLanguage') || 'it';

  // Find all elements with the id "lang-flag" and set the value
  const elements = document.querySelectorAll('#lang-flag');
  elements.forEach((element) => {
    element.value = langFlagValue;
  });
}

/**
 * Runs after the page has loaded and sets the language based on localStorage.
 * It also checks the URL for the language parameter and updates the page accordingly.
 */
document.addEventListener("DOMContentLoaded", function () {
  // Get the selected language from localStorage or default to 'it'
  let selectedLanguage = localStorage.getItem("selectedLanguage") || "it";

  // Check if the URL already has the 'lang' parameter
  const currentUrl = new URL(window.location.href);
  const urlLang = currentUrl.searchParams.get("lang");

  // If the URL lang parameter is missing or doesn't match the selected language
  if (!urlLang || urlLang !== selectedLanguage) {
    currentUrl.searchParams.set("lang", selectedLanguage);
    window.history.replaceState({}, '', currentUrl.toString());
  }

  // Set the lang attribute on the HTML element
  document.documentElement.setAttribute('lang', selectedLanguage);

  // Change the language and update UI
  changeLanguage(selectedLanguage);
  updateLanguageSwitcher(selectedLanguage);

  // Log the selected language to the console
  console.log("Selected language:", selectedLanguage);

  // Set the language flag
  window.onload = setLangFlagFromStorage;
});

/**
 * Logs the translations to the console (can be customized to update UI with translations).
 * 
 * @param {Object} translations - The translations loaded from the JSON file.
 */
function updateUIWithTranslations(translations) {
  // For now, just log the translations
  console.log("Translations loaded:", translations);
}

/**
 * Returns the correct text based on the selected language.
 * 
 * @param {string} italianText - The text in Italian.
 * @param {string} englishText - The text in English.
 * @returns {string} The text in the selected language.
 */
function getLocalizedText(italianText, englishText) {
  return currentLanguage === 'it' ? italianText : englishText;
}

/**
 * Updates the language switcher UI based on the selected language.
 * It changes the flag image and the text for both desktop and mobile views.
 * 
 * @param {string} lang - The selected language (either 'it' or 'en').
 */
function updateLanguageSwitcher(lang) {
  const flagElement = document.getElementById("desktopLanguageFlag");
  const textElement = document.getElementById("desktopLanguageText");

  if (lang === "en") {
    flagElement.src = "/img/uk-flag.png";
    flagElement.alt = "English";
    textElement.textContent = "English";
  } else {
    flagElement.src = "/img/italy-flag.png";
    flagElement.alt = "Italiano";
    textElement.textContent = "Italiano";
  }

  // For mobile language switcher
  const mobileFlagElement = document.getElementById("mobileLanguageFlag");
  const mobileTextElement = document.getElementById("mobileLanguageText");

  if (lang === "en") {
    mobileFlagElement.src = "/img/uk-flag.png";
    mobileFlagElement.alt = "English";
    mobileTextElement.textContent = "English";
  } else {
    mobileFlagElement.src = "/img/italy-flag.png";
    mobileFlagElement.alt = "Italiano";
    mobileTextElement.textContent = "Italiano";
  }
}

/**
 * Toggles the language on mobile view by switching between 'it' and 'en'.
 * It changes the language and reloads the page with the new language.
 */
function toggleLanguageMobile() {
  const currentLanguage = localStorage.getItem("selectedLanguage") || "it";
  const newLanguage = currentLanguage === "it" ? "en" : "it";

  localStorage.setItem("selectedLanguage", newLanguage);

  // Reload the page with the new language as a query parameter
  const currentUrl = new URL(window.location.href);
  currentUrl.searchParams.set('lang', newLanguage);
  window.location.href = currentUrl.toString();
}
