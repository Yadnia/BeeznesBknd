const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const chatArea = document.querySelector('#chat-messages');

let stompClient = null;
let username = null;

function connect(event) {
    username = document.querySelector('#username').value.trim(); // Obtener el nombre de usuario

    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('http://localhost:8080/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({ username: username }));

    // Mostrar el nombre de usuario
    document.querySelector('#connected-user-fullname').textContent = username;
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    displayMessage(message.senderId, message.content);
}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    messageContainer.classList.add(senderId === username ? 'sender' : 'receiver');

    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
    chatArea.scrollTop = chatArea.scrollHeight;
}

function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent) {
        const chatMessage = {
            senderId: username,
            content: messageInput.value.trim(),
            timestamp: new Date()
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = ''; // Limpiar campo de entrada
    }
    event.preventDefault();
}

function onError(error) {
    console.error('Error connecting to WebSocket', error);
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
