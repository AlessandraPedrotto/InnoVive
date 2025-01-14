const buttons = document.querySelectorAll('.secondary a');
const medie = document.getElementById('medie');
const superiori = document.getElementById('superiori');
const tutti = document.getElementById('tutti') 
const laboratori = document.getElementById('laboratori') 


//  for each button, adding an event listener
buttons.forEach(button => {
    button.addEventListener('click', (e) => {
      e.preventDefault();
      window.scrollTo({
          top: 70, 
          behavior: 'smooth'
      });

      //  hiding all sections
      medie.classList.add('hidden');               // da gestire con array
      superiori.classList.add('hidden');
      tutti.classList.add('hidden');
      laboratori.classList.add('hidden');

      //  removing torunaments class
      medie.classList.remove('attivita');
      superiori.classList.remove('attivita');               // idem
      tutti.classList.remove('attivita');
      laboratori.classList.remove('attivita');
      
      //  removing active class from all buttons
      buttons.forEach(btn => btn.classList.remove('active'));
  
      // showing only the clicked section
      if (button.textContent === 'Medie') {
        medie.classList.remove('hidden');
        medie.classList.add('attivita');
      } else if (button.textContent === 'Superiori') {
        superiori.classList.remove('hidden');
        superiori.classList.add('attivita');
      } else if (button.textContent === 'Laboratori') {
        laboratori.classList.remove('hidden');
        laboratori.classList.add('attivita');
      } else if (button.textContent === 'Tutti') {
        tutti.classList.remove('hidden');
        tutti.classList.add('attivita');
      }
      //  adding active class to the selected section
      button.classList.add('active');
    });
  });


// accordion menu

document.querySelectorAll('.accordion-header').forEach(header => {
  header.addEventListener('click', () => {
    const currentlyActive = document.querySelector('.accordion-header.active');
    if (currentlyActive && currentlyActive !== header) {
      currentlyActive.classList.remove('active');
      currentlyActive.nextElementSibling.style.display = 'none';
    }

    header.classList.toggle('active');
    const content = header.nextElementSibling;
    if (header.classList.contains('active')) {
      content.style.display = 'block';
    } else {
      content.style.display = 'none';
    }
  });
});