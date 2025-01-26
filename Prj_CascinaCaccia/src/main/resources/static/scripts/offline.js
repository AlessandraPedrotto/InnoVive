/**
 * @file: offline.js
 * @author: InnoVive
 * This script manages the user's heartbeat to keep the session alive by periodically sending
 * a request to the server. It checks if the user email exists in the cookies and, if so, sends a
 * heartbeat request every 10 seconds.
 * 
 */

/**
 * Sends a heartbeat request to the server to keep the user session alive.
 * This function is invoked every 10 seconds if a valid user email is found in the cookies.
 * 
 * @function
 * @param {string} email - The user's email fetched from the cookies.
 * @returns {void}
 */
const email = getCookie("userEmail");

if (email) {
    setInterval(() => {
        /**
         * Sends a POST request to the server to indicate the user is active.
         * This request is sent every 10 seconds if the user's email exists.
         * 
         * @async
         * @function
         * @returns {void}
         */
        fetch("/heartbeat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email: email }),
        }).then((response) => {
            if (!response.ok) {
                console.error("Heartbeat failed:", response.statusText);
            }
        }).catch((error) => console.error("Heartbeat error:", error));
    }, 10000); // Every 10 seconds
}

/**
 * Retrieves the value of a cookie by its name.
 * 
 * @function
 * @param {string} name - The name of the cookie to retrieve.
 * @returns {string|null} - The value of the cookie, or null if the cookie is not found.
 */
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(";").shift();
}
