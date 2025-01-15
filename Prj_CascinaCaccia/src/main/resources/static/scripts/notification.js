document.addEventListener('DOMContentLoaded', function () {
    const badge = document.getElementById('notificationBadge');
    const viewRequestsButton = document.getElementById('viewRequestsButton');
    
    // If the badge has a non-zero value, show it
    if (badge && badge.innerText > 0) {
        badge.style.display = 'inline';
    }

    // Handle the click event for the "Visualizza richieste" button
    if (viewRequestsButton) {
        viewRequestsButton.addEventListener('click', function () {
            // Hide the notification badge when the user clicks on the "Visualizza richieste"
            badge.style.display = 'none';
            
            // Optionally, update the badge number to 0 (if you want to reflect this change on the UI)
            badge.innerText = '0';
        });
    }
});
