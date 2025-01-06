//Toggle the visibility of the chatbot container
function toggleChatbot() {
    const chatbotContainer = document.getElementById('chatbotContainer');
    chatbotContainer.style.display = (chatbotContainer.style.display === 'none' || chatbotContainer.style.display === '') ? 'flex' : 'none';

    // If chatbot is shown for the first time, display the initial message
    if (chatbotContainer.style.display === 'flex' && document.getElementById('chatMessages').childElementCount === 0) {
        showInitialMessage();
    }
}

function handleQuestionClick(question, buttonsContainer) {
    // Disable all buttons in the current group when a button is clicked
    disableAllButtons();

    // Add user message with replay icon
    const userMessage = document.createElement('div');
    userMessage.classList.add('message', 'user');

    const userText = document.createElement('div');
    userText.classList.add('text');
    userText.textContent = question;

    // Add replay icon
    const replayIcon = document.createElement('span');
    replayIcon.innerHTML = "&#x21ba;"; // Unicode for refresh icon
    replayIcon.style.cursor = "pointer";
    replayIcon.style.marginLeft = "10px";
    replayIcon.style.fontSize = "12px";
    replayIcon.title = "Replay this question";

    replayIcon.onclick = () => {
        handleQuestionClick(question, buttonsContainer);
    };

    userText.appendChild(replayIcon); // Add the icon next to the text
    userMessage.appendChild(userText);
    document.getElementById('chatMessages').appendChild(userMessage);

    // Simulate bot response
    setTimeout(() => {
        const botMessage = document.createElement('div');
        botMessage.classList.add('message', 'bot');

        const botText = document.createElement('div');
        botText.classList.add('text');
        botText.textContent = getBotResponse(question);

        const buttonsContainer = document.createElement('div');
        buttonsContainer.classList.add('buttons-container');

        // Handle additional questions for "What can you do?"
        if (question === "What can you do?") {
            const followUpQuestions = [
                "How do you help with tasks?",
                "Can you provide examples?",
                "What makes you unique?"
            ];

            followUpQuestions.forEach(followUp => {
                const followUpButton = document.createElement('button');
                followUpButton.textContent = followUp;
                followUpButton.onclick = () => handleQuestionClick(followUp, buttonsContainer);
                buttonsContainer.appendChild(followUpButton);
            });
        }

        // Add "Ask something else" button
        const askMoreButton = document.createElement('button');
        askMoreButton.textContent = "Ask something else";
        askMoreButton.onclick = function () {
            // Disable all buttons in the current group
            disableAllButtons();

            // Add "Ask something else" as a user message
            const userMessage = document.createElement('div');
            userMessage.classList.add('message', 'user');

            const userText = document.createElement('div');
            userText.classList.add('text');
            userText.textContent = "Ask something else";
            userMessage.appendChild(userText);
            document.getElementById('chatMessages').appendChild(userMessage);

            // Show the initial message after user message is added
            showInitialMessage();

            // Scroll to the bottom of the chat
            scrollToBottom();
        };
        buttonsContainer.appendChild(askMoreButton);

        botMessage.appendChild(botText);
        botMessage.appendChild(buttonsContainer);
        document.getElementById('chatMessages').appendChild(botMessage);

        // Scroll to the bottom of the chat
        scrollToBottom();
    }, 500);
}

// When showing initial message, disable all buttons if they are already shown
function showInitialMessage() {
    // Disable all previous buttons before rendering new ones
    disableAllButtons();

    const botMessage = document.createElement('div');
    botMessage.classList.add('message', 'bot');
    
    const botText = document.createElement('div');
    botText.classList.add('text');
    botText.textContent = "How can I help you?";
    
    const buttonsContainer = document.createElement('div');
    buttonsContainer.classList.add('buttons-container');
    
    const questions = [
        "What is your name?",
        "What can you do?",
        "Tell me a joke!"
    ];

    questions.forEach(question => {
        const button = document.createElement('button');
        button.textContent = question;
        button.onclick = () => handleQuestionClick(question, buttonsContainer);
        buttonsContainer.appendChild(button);
    });

    botMessage.appendChild(botText);
    botMessage.appendChild(buttonsContainer);
    document.getElementById('chatMessages').appendChild(botMessage);

    // Scroll to the bottom of the chat
    scrollToBottom();
}

// Disable all buttons from previous responses
function disableAllButtons() {
    const allButtons = document.querySelectorAll('.buttons-container button');
    allButtons.forEach(button => button.disabled = true);
    allButtons.forEach(button => button.classList.add('hidden'));
}

// Bot response based on user input
function getBotResponse(userMessage) {
    const responses = {
        "What is your name?": "I am Caccia, your friendly chatbot!",
        "What can you do?": "I can answer your questions, tell jokes, and assist with tasks! Here's more I can do:",
        "Tell me a joke!": "Why don't skeletons fight each other? They don't have the guts!",
        "How do you help with tasks?": "I provide step-by-step guidance to help you complete your tasks efficiently.",
        "Can you provide examples?": "Sure! I can assist with coding, answering questions, and even creative writing.",
        "What makes you unique?": "I’m fast, adaptive, and always ready to help!"
    };

    return responses[userMessage] || "I'm sorry, I didn't understand that.";
}

// Scroll to the bottom of the chat
function scrollToBottom() {
    const chatMessages = document.getElementById('chatMessages');
    chatMessages.scrollTop = chatMessages.scrollHeight;
}
