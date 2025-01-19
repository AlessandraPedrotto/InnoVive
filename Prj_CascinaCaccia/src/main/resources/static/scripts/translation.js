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
          if (element.tagName !== "INPUT" && element.tagName !== "TEXTAREA") {
            element.textContent = translations[translationKey];
          } else {
            // Update placeholder for input and textarea elements
            element.placeholder = translations[translationKey];
          }
        }
      });

      // Update the dropdown toggle to display the selected language
      const dropdownToggle = document.querySelector(".language-dropdown .dropdown-toggle");
      const flag = lang === "it" ? "/img/italy-flag.png" : "/img/uk-flag.png"; // Add more flags if necessary
      const languageName = lang === "it" ? "Italiano" : "English"; // Add more languages if necessary
      dropdownToggle.innerHTML = `<img src="${flag}" alt="${languageName}" class="flag"> ${languageName}`;
    })
    .catch(error => console.error("Error loading translations:", error));
}

// Set the default language (Italian) when the page loads
document.addEventListener("DOMContentLoaded", function () {
  // Check if there's a saved language in localStorage, otherwise default to 'it'
  const savedLanguage = localStorage.getItem("selectedLanguage");
  const defaultLanguage = savedLanguage || "it"; // Always default to Italian if no language is saved
  changeLanguage(defaultLanguage);
});

