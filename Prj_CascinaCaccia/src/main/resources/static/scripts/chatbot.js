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
        botText.innerHTML = getBotResponse(question);

        const buttonsContainer = document.createElement('div');
        buttonsContainer.classList.add('buttons-container');

        // Handle additional questions for "Informazioni sulla cascina"
        if (question === "Informazioni sulla cascina") {
            const followUpQuestions = [
                "Dove si trova la cascina?",
                "Come posso contattarvi?",
                "Come prenoto una visita?"
            ];

            followUpQuestions.forEach(followUp => {
                const followUpButton = document.createElement('button');
                followUpButton.textContent = followUp;
                followUpButton.onclick = () => handleQuestionClick(followUp, buttonsContainer);
                buttonsContainer.appendChild(followUpButton);
            });
        }
        
        // Handle additional questions for "La storia di Cascina Caccia"
        if (question === "La storia di Cascina Caccia") {
            const followUpQuestions = [
                "Cos'è Cascina Caccia?",
                "Chi era Bruno Caccia?"
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
        askMoreButton.textContent = "Chiedi altro";
        askMoreButton.onclick = function () {
            // Disable all buttons in the current group
            disableAllButtons();

            // Add "Ask something else" as a user message
            const userMessage = document.createElement('div');
            userMessage.classList.add('message', 'user');

            const userText = document.createElement('div');
            userText.classList.add('text');
            userText.textContent = "Chiedi altro";
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
    botText.textContent = "Come posso aiutarti?";
    
    const buttonsContainer = document.createElement('div');
    buttonsContainer.classList.add('buttons-container');
    
    const questions = [
        "La storia di Cascina Caccia",
        "Informazioni sulla cascina",
        "Attività da svolgere"
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
        "La storia di Cascina Caccia": "Cosa desideri sapere?",
        "Informazioni sulla cascina": "Cosa desideri sapere?",
        "Attività da svolgere": "Oltre alle attività di sensibilizzazione sull'argomento mafia, offriamo anche laboratori manuali, che includono la raccolta dei frutti prodotti in cascina e la loro preparazione in cucina, oltre a attività artistiche per la creazione di candele. Per avere maggiori dettagli clicca <a href='/attivita' target='_blank'>qui</a>.",
        "Dove si trova la cascina?": "La Cascina si trova a San Sebastiano da Po, in provincia di Torino, Piemonte.",
        "Come posso contattarvi?": "Puoi contattarci compilando il modulo per le informazioni, che trovi cliccando sul pulsante rotondo con la 'i' qua di lato oppure <a href='#informationFormContainer' onclick='toggleInformationForm()'>qui</a>.",
        "Come prenoto una visita?": "Puoi prenotare una visita cliccando sul tasto 'Prenota' nel menu in alto oppure <a href='/prenota' target='_blank'>qui</a>.",
    	"Cos'è Cascina Caccia?": "Cascina Caccia era un bene della famiglia Belfiore, legata alla 'Ndrangheta, e fu confiscata dopo la condanna di Domenico Belfiore, mandante dell’omicidio del giudice Bruno Caccia. Oggi è un luogo simbolo di riscatto. Rappresenta la vittoria della giustizia sulla criminalità organizzata.",
    	"Chi era Bruno Caccia?": "Bruno Caccia era un giudice torinese ucciso nel 1983 per la sua lotta contro la criminalità organizzata. La sua morte, causata da Domenico Belfiore, mandante dell'omicidio, ha portato alla confisca di beni legati alla famiglia Belfiore, tra cui questa Cascina."
    };

    return responses[userMessage] || "Scusami, non ho capito.";
}

// Scroll to the bottom of the chat
function scrollToBottom() {
    const chatMessages = document.getElementById('chatMessages');
    chatMessages.scrollTop = chatMessages.scrollHeight;
}



//  INFORMATION FORM

function toggleInformationForm() {
    const form = document.getElementById('informationFormContainer');
    if (form.classList.contains('show')) {
        form.classList.remove('show');
    }else{
        form.classList.add('show');
    }
}