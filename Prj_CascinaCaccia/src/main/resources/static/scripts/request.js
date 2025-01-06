document.addEventListener('DOMContentLoaded', () => {
    //toggle della classe 'hidden' sugli header delle richieste
    const headers = document.querySelectorAll('.request-header');
    const bodies = document.querySelectorAll('.request-body');

    for (let i = 0; i < headers.length; i++) {
        headers[i].addEventListener('click', () => {
            bodies[i].classList.toggle('hidden');
            headers[i].classList.toggle('request-header-selected');
        });
    };

    //toggle della classe 'hidden' del form per assegnare l' utente
    const assignUserButton = document.querySelectorAll('.assign-user-button');
    const assignUserContent = document.querySelectorAll('.assign-user-content');

    for (let i = 0; i < assignUserButton.length; i++) {
        assignUserButton[i].addEventListener('click', () => {
            assignUserContent[i].classList.toggle('hidden');
        });
    }

    //toggle della classe 'hidden' del form per assegnare lo status
    const assignStatusButton = document.querySelectorAll('.assign-status-button');
    const assignStatusForm = document.querySelectorAll('.assign-status-content');

    for (let i = 0; i < assignStatusButton.length; i++) {
        assignStatusButton[i].addEventListener('click', () => {
            assignStatusForm[i].classList.toggle('hidden');
        });
    }

    //toggle della classe hidden del form per assegnare l utente tramite il bottone 'X'
    const closeUserAssignmentButtons = document.querySelectorAll('.close-user-assignment');
    const closeStatusAssignmentButtons = document.querySelectorAll('.close-status-assignment');

    for (let i = 0; i < closeUserAssignmentButtons.length; i++) {
        closeUserAssignmentButtons[i].addEventListener('click', () => {
            assignUserContent[i].classList.toggle('hidden');
        });
    }

    for (let i = 0; i < closeStatusAssignmentButtons.length; i++) {
        closeStatusAssignmentButtons[i].addEventListener('click', () => {
            assignStatusForm[i].classList.toggle('hidden');
        });
    }
});

