// Wait for the DOM to fully load
document.addEventListener('DOMContentLoaded', function () {
    // Get the success and error messages from the DOM
    const successMessage = document.querySelector('[data-success]')?.getAttribute('data-success');
    const errorMessage = document.querySelector('[data-error]')?.getAttribute('data-error');
	const noResultsMessage = document.querySelector('[data-no-results]')?.getAttribute('data-no-results');
	const noMessageMessage = document.querySelector('[data-no-message]')?.getAttribute('data-no-message');

    // Display the message in a popup
    if (successMessage) {
        alert("Success: " + successMessage);
    } else if (errorMessage) {
        alert("Error: " + errorMessage);
    } else if (noResultsMessage) {
        alert(noResultsMessage); // Display the no results message
    } else if (noMessageMessage) {
        alert(noMessageMessage); // Display the no results message
    }
});
