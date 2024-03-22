document.addEventListener("DOMContentLoaded", function () {
  let socket = null;
  const token = localStorage.getItem("token");
  loadRooms();
  let currentRoomId = null;
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
        listItem.onclick = async () => loadMessages(room.id); // Attach click event to load messages
        roomsList.appendChild(listItem);
      });
    } catch (error) {
      console.error("Error fetching rooms:", error);
    }
  }

  async function loadMessages(roomId) {
    currentRoomId = roomId;

    // Close the previous WebSocket connection if it exists
    if (socket !== null) {
      socket.close();
    }

    // Create a new WebSocket connection
    socket = new WebSocket(
      "ws://" +
        location.hostname +
        ":" +
        location.port +
        `/rooms/${roomId}/messages?token=${token}`
    );
    const messageArea = document.getElementById("message-area");
    const roomTitle = document.getElementById("room-title");
    roomTitle.textContent = `Messages in Room ${roomId}`;
    const messagesDiv = document.getElementById("messages");
    messagesDiv.innerHTML = "";
    try {
      socket.onmessage = (msg) => {
        //we recieve a msg from the server
        let data = JSON.parse(msg.data);
        console.log(data);

        data.forEach((message) => {
          const date = new Date(message.timestamp);
          const messageDiv = document.createElement("div");
          messageDiv.classList.add("message");
          messageDiv.innerHTML = `<span class="user">${
            message.username
          }:</span> ${
            message.text
          } <span class="timestamp"> ${date.toLocaleString()} </span>`;
          messagesDiv.appendChild(messageDiv);
        });
        messageArea.style.display = "block";
      };

      //send a message
      const messageForm = document.getElementById("message-form");
      messageForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const messageInput = document.getElementById("message-input");
        const messageText = messageInput.value;
        console.log(messageText);

        // Send the message object as a JSON string
        socket.send(JSON.stringify(messageText));
        messageInput.value = "";
      });
      socket.onclose = () => console.log(`You have left room ${roomId}`);
    } catch (errror) {
      console.log(error);
    }
  }

  // const messageForm = document.getElementById("message-form");
  // messageForm.addEventListener("submit", async (event) => {
  //   event.preventDefault();
  //   const messageInput = document.getElementById("message-input");
  //   const messageText = messageInput.value;
  //   console.log(messageText);
  //   const response = await fetch(`/rooms/${currentRoomId}/sendMessage`, {
  //     method: "POST",
  //     headers: {
  //       Authorization: token,
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify({ text: messageText }),
  //   });
  //   const data = await response.json();
  //   console.log(data);
  // });
});
