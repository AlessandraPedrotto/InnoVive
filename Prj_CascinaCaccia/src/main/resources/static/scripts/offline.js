const email = getCookie("userEmail");

if (email) {
    setInterval(() => {
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

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(";").shift();
}

