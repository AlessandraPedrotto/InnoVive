document.addEventListener('DOMContentLoaded', () => {
    //toggle della classe 'hidden' sugli header delle richieste
    const header = document.querySelector('.request-header');
    header.addEventListener('click', () => {
        const requestBody = document.querySelector('.request-body');
        requestBody.classList.toggle('hidden');
    });

    //toggle della classe 'hidden' del form per assegnare l' utente
    const assignUserButton = document.querySelector('.assign-user-button');
    assignUserButton.addEventListener('click', () => {
        const assignUserForm = document.querySelector('.assign-user-content');
        assignUserForm.classList.toggle('hidden');
    });

    //toggle della classe 'hidden' del form per assegnare lo status
    const assignStatusButton = document.querySelector('.assign-status-button');
    assignStatusButton.addEventListener('click', () => {
        const assignStatusForm = document.querySelector('.assign-status-content');
        assignStatusForm.classList.toggle('hidden');
    });
});

