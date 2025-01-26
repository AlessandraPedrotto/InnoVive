/**
 * Initializes the navigation menu behavior by setting up event listeners
 * for buttons and managing the display of corresponding sections.
 * 
 * This function:
 * - Sets up a smooth scroll to the secondary menu.
 * - Hides all sections and ensures only the target section is displayed.
 * - Updates button states (active/inactive) based on the user interaction.
 */
function initializeMenuBehavior() {
  document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.secondary a');  // Secondary navbar links
    const sections = {  // Object with sections to display/hide
      'medie': document.getElementById('medie'),
      'superiori': document.getElementById('superiori'),
      'tutti': document.getElementById('tutti')
    };

    buttons.forEach(button => {
      button.addEventListener('click', (e) => {
        e.preventDefault();

        // Scroll the page to the secondary menu
        window.scrollTo({
          top: 70,
          behavior: 'smooth'
        });

        // Hide all sections and remove 'attivita' class
        Object.values(sections).forEach(section => {
          section.classList.add('hidden');
          section.classList.remove('attivita');
        });

        // Remove 'active' class from all buttons
        buttons.forEach(btn => btn.classList.remove('active'));

        const target = button.getAttribute('data-target');
        const section = sections[target];
        
        if (section) {
          section.classList.remove('hidden');
          section.classList.add('attivita');
        }

        // Add 'active' class to the clicked button
        button.classList.add('active');
      });
    });
  });
}

// Call the function to initialize behavior
initializeMenuBehavior();
