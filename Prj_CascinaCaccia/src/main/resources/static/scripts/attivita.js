document.addEventListener('DOMContentLoaded', () => {
  const buttons = document.querySelectorAll('.secondary a');  // secondary navbar links
  const sections = {  // object with section to display/hide
    'medie': document.getElementById('medie'),
    'superiori': document.getElementById('superiori'),
    'tutti': document.getElementById('tutti')
  };

  buttons.forEach(button => {
    button.addEventListener('click', (e) => {
      e.preventDefault();

      // scroll the page to the secondary menu
      window.scrollTo({
        top: 70,
        behavior: 'smooth'
      });

      // hide all sections and remove attivita class
      Object.values(sections).forEach(section => {
        section.classList.add('hidden');
        section.classList.remove('attivita');
      });

      // remove active class from all buttons
      buttons.forEach(btn => btn.classList.remove('active'));

	  const target = button.getAttribute('data-target');
      const section = sections[target];
      
      if (section) {
        section.classList.remove('hidden');
        section.classList.add('attivita');
      }

      // add active class to the clicked button
      button.classList.add('active');
    });
  });
});
