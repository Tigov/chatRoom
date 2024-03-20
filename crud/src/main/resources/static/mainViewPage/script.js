document.addEventListener("DOMContentLoaded", function () {
  const token = localStorage.getItem("token");
  loadRooms();

  console.log(token);
  async function loadRooms() {
    try {
      const res = await fetch("/rooms", {
        headers: {
          Authorization: token,
        },
      });
      const allRooms = await res.json();

      const roomsList = document.getElementById("rooms");
      roomsList.innerHTML = ""; // Clear existing entries

      allRooms.forEach((room) => {
        const listItem = document.createElement("li");
        listItem.textContent = `Room ${room.id}: ${room.numberOfUsersInRoom} users`;
        listItem.onclick = () => loadMessages(room.id); // Attach click event to load messages
        roomsList.appendChild(listItem);
      });
    } catch (error) {
      console.error("Error fetching rooms:", error);
    }
  }

  async function loadMessages(roomId) {
    try {
      const response = await fetch(`/rooms/${roomId}/messages`, {
        headers: {
          Authorization: token,
        },
      });
      const messages = await response.json();
      console.log(messages);
      const messageArea = document.getElementById("message-area");
      const roomTitle = document.getElementById("room-title");
      roomTitle.textContent = `Messages in Room ${roomId}`;
      const messagesDiv = document.getElementById("messages");
      messagesDiv.innerHTML = "";
      messages.forEach((message) => {
        console.log(message);
        const date = new Date(message.timestamp);
        const messageDiv = document.createElement("div");
        messageDiv.classList.add("message");
        messageDiv.innerHTML = `<span class="user">${
          message.msgUsername
        }:</span> ${
          message.text
        } <span class="timestamp"> ${date.toLocaleString()} </span>`;
        messagesDiv.appendChild(messageDiv);
      });
      messageArea.style.display = "block";
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  }

  const messageForm = document.getElementById("message-form");
  messageForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const messageInput = document.getElementById("message-input");
    const messageText = messageInput.value;
    //const roomId = ...; // The current room ID you are in
  });
});
