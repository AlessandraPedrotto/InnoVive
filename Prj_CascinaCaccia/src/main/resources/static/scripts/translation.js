
let currentLanguage = localStorage.getItem('selectedLanguage') || 'it';

// Function to change language
function changeLanguage(lang) {
  // Store the selected language in localStorage
  localStorage.setItem('selectedLanguage', lang);
  currentLanguage = lang;
  // Update the lang attribute in the HTML tag
  document.documentElement.setAttribute('lang', lang);
  const currentUrl = new URL(window.location.href);
  currentUrl.searchParams.set('lang', lang);
  window.history.replaceState({}, '', currentUrl.toString());
  // Load the corresponding JSON file for the selected language
  fetch(`/translation/${lang}.json`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`Translation file for ${lang} could not be loaded.`);
      }
      return response.json();
    })
    .then(translations => {
      localStorage.setItem(`translations_${lang}`, JSON.stringify(translations));

      // Update all elements with data-translate attributes
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
      changeLanguage('it'); 
    });
}

function setLangFlagFromStorage() {
    let langFlagValue = localStorage.getItem('selectedLanguage') || 'it';

    // Get all elements with the id "lang-flag" (though IDs should be unique, let's handle as per your request)
    const elements = document.querySelectorAll('#lang-flag');
    
    // Loop through each element and set its content to the lang-flag value
    elements.forEach((element) => {
        element.value = langFlagValue;
    });
}

// Consolidate the DOMContentLoaded listeners
document.addEventListener("DOMContentLoaded", function () {
  // Check if there's a saved language in localStorage
  let selectedLanguage = localStorage.getItem("selectedLanguage") || "it";

  // Check if the URL already has the lang parameter
  const currentUrl = new URL(window.location.href);
  const urlLang = currentUrl.searchParams.get("lang");


  // If the URL lang parameter is missing or doesn't match the selected language
  if (!urlLang || urlLang !== selectedLanguage) {
    currentUrl.searchParams.set("lang", selectedLanguage);
    window.history.replaceState({}, '', currentUrl.toString());
  };

  // Set the lang attribute on the HTML element
  document.documentElement.setAttribute('lang', selectedLanguage);

  // Change the language and update UI
  changeLanguage(selectedLanguage);
  updateLanguageSwitcher(selectedLanguage);

  // Log the selected language
  console.log("Selected language:", selectedLanguage);
  window.onload = setLangFlagFromStorage;
});

// Function to update the UI with translations (this can be customized)
function updateUIWithTranslations(translations) {
  // For now, just log the translations
  console.log("Translations loaded:", translations);
}
function getLocalizedText(italianText, englishText) {
  return currentLanguage === 'it' ? italianText : englishText;
}
// Function to update the language switcher based on the selected language
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

// Function to toggle language on mobile view
function toggleLanguageMobile() {
  // Get the current language from localStorage (or default to Italian)
  const currentLanguage = localStorage.getItem("selectedLanguage") || "it";

  // Toggle between 'it' and 'en'
  const newLanguage = currentLanguage === "it" ? "en" : "it";

  // Change language by calling the main function
  localStorage.setItem("selectedLanguage", newLanguage);

  // Reload the page with the new language as a query parameter, preserving the lang
  const currentUrl = new URL(window.location.href);
  currentUrl.searchParams.set('lang', newLanguage);
  window.location.href = currentUrl.toString(); 
}
