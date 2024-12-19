const buttons = document.querySelectorAll('.secondary a');
const medie = document.getElementById('medie');
const superiori = document.getElementById('superiori');
const tutti = document.getElementById('tutti') ;


//  for each button, adding an event listener
buttons.forEach(button => {
    button.addEventListener('click', (e) => {
      e.preventDefault();   // manage default browser's behaviour 

      //  hiding all sections
      medie.classList.add('hidden');               // da gestire con array
      superiori.classList.add('hidden');
      tutti.classList.add('hidden');

      //  removing torunaments class
      medie.classList.remove('attivita');
      superiori.classList.remove('attivita');               // idem
      tutti.classList.remove('attivita');
      
      //  removing active class from all buttons
      buttons.forEach(btn => btn.classList.remove('active'));
  
      // showing only the clicked section
      if (button.textContent === 'Medie') {
        medie.classList.remove('hidden');
        medie.classList.add('attivita');
      } else if (button.textContent === 'Superiori') {
        superiori.classList.remove('hidden');
        superiori.classList.add('attivita');
      } else if (button.textContent === 'Tutti') {
        tutti.classList.remove('hidden');
        tutti.classList.add('attivita');
      }
      //  adding active class to the selected section
      button.classList.add('active');
    });
  });
