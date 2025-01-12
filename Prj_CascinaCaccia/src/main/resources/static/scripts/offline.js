let isLoggingOut = false;

// Mark user as online
function markUserOnline() {
    const email = document.querySelector("#email").value;
    if (email) {
        fetch('/set-user-online', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email }),
        }).catch((err) => console.error("Failed to mark user online:", err));
    }
}

// Mark user as offline
function markUserOffline(email) {
    if (email) {
        const url = '/set-user-offline?email=' + encodeURIComponent(email);
        const blob = new Blob([], { type: 'application/x-www-form-urlencoded' });

        // Attempt to send the beacon
        const sent = navigator.sendBeacon(url, blob);

        // Fallback to fetch if beacon fails
        if (!sent) {
            fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: new URLSearchParams({ email }),
            }).catch((err) => console.error('Failed to mark user offline:', err));
        }
    }
}

// Handle page unload (tab close or refresh)
window.addEventListener("unload", function () {
    const email = document.querySelector("#email").value;
    if (!isLoggingOut) {
        markUserOffline(email);
    }
});

// Handle navigation within the website
document.addEventListener("click", (event) => {
    const target = event.target.closest("a");
    if (target && target.origin === window.location.origin) {
        markUserOnline();
    }
});

// Handle explicit logout
document.getElementById("logoutBtn").addEventListener("click", () => {
    isLoggingOut = true;
    markUserOffline();
});

// Initialize: Mark user as online when the page loads
document.addEventListener("DOMContentLoaded", () => {
    markUserOnline();
});


