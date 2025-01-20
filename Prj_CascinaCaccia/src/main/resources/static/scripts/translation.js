// The changeLanguage function remains the same
function changeLanguage(lang) {
  // Store the selected language in localStorage
  localStorage.setItem('selectedLanguage', lang);

  // Load the corresponding JSON file for the selected language
  fetch(`/translation/${lang}.json`)
    .then(response => response.json())
    .then(translations => {
      // Update all elements with data-translate attributes
      const elementsToTranslate = document.querySelectorAll("[data-translate]");
      elementsToTranslate.forEach(element => {
        const translationKey = element.getAttribute("data-translate");
        if (translations[translationKey]) {
          // Update text content for non-input elements
          if (element.hasAttribute("data-translate-html")) {
            element.innerHTML = translations[translationKey]; // Allow HTML translations
          } else if (element.tagName !== "INPUT" && element.tagName !== "TEXTAREA") {
            element.textContent = translations[translationKey];
          } else {
            // Update placeholder for input and textarea elements
            element.placeholder = translations[translationKey];
          }
        }
      });
      
      document.dispatchEvent(new CustomEvent('languageChange', { detail: { lang: lang } }));
    })
    .catch(error => console.error("Error loading translations:", error));
}

// Set the default language (Italian) when the page loads
document.addEventListener("DOMContentLoaded", function () {
  // Check if there's a saved language in localStorage, otherwise default to 'it'
  const savedLanguage = localStorage.getItem("selectedLanguage");
  const defaultLanguage = savedLanguage || "it"; // Always default to Italian if no language is saved
  changeLanguage(defaultLanguage);
  updateLanguageSwitcher(defaultLanguage);
});

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

function toggleLanguageMobile() {
  // Get the current language from localStorage (or default to Italian)
  const currentLanguage = localStorage.getItem("selectedLanguage") || "it";

  // Toggle between 'it' and 'en'
  const newLanguage = currentLanguage === "it" ? "en" : "it";
  
  // Change language by calling the main function
  changeLanguage(newLanguage);

  // Update the language flags and text based on the selected language
  updateLanguageSwitcher(newLanguage);
}