document.addEventListener("DOMContentLoaded", function () {
  let socket = null;
  const token = localStorage.getItem("token");
  loadRooms();
  let currentRoomId = null;
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
        listItem.id = `room-${room.id}`; // Add an id to each room
        console.log(`Created room with id: ${listItem.id}`); // Log the id of each room
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
        //we receive a msg from the server
        let data = JSON.parse(msg.data);
        console.log(data);
        if (!Array.isArray(data)) {
          // if response is not an array
          if (data.Update) {
            let updateRoomId = data.roomId;
            //if its an update user numbers (if someone joins a room somewhere, update its numberOfUsersInRoom tag)
            const roomListItem = document.getElementById(
              `room-${updateRoomId}`
            );
            console.log(roomListItem);
            if (roomListItem) {
              roomListItem.textContent = `Room ${updateRoomId}: ${data.numberOfUsersInRoom} users`;
            }
          }
        } else if (Array.isArray(data)) {
          // if its a regular message
          data.forEach((message) => {
            const date = new Date(message.timestamp);
            const messageDiv = document.createElement("div");
            messageDiv.classList.add("message");
            messageDiv.innerHTML = `<span class="user">${
              message.username
            }:</span> ${
              message.text
            } <span class="timestamp"> ${date.toLocaleString()} </span>`;
            messagesDiv.insertBefore(messageDiv, messagesDiv.firstChild);
          });
          messageArea.style.display = "block";
        }
      };

      socket.onclose = async function (event) {
        const roomListItem = document.getElementById(`room-${roomId}`);
        console.log(roomListItem);
        let response = await fetch(`/rooms/getNumUsersFromRoomId/${roomId}`, {
          headers: {
            Authorization: token,
          },
        });
        let data = await response.json();

        let numberOfUsers = data.numberOfUsersInRoom;
        console.log(
          `NUMBER OF USERS ON SOCKET CLOSE: ${numberOfUsers} for roomId ${currentRoomId}`
        );
        if (roomListItem) {
          roomListItem.textContent = `Room ${roomId}: ${numberOfUsers} users`;
        }
      };
    } catch (error) {
      console.log(error);
    }
  }

  //send a message
  const messageForm = document.getElementById("message-form");
  messageForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const messageInput = document.getElementById("message-input");
    const messageText = messageInput.value;

    // Send the message object as a JSON string
    socket.send(JSON.stringify(messageText));
    messageInput.value = "";
  });

  window.addEventListener("beforeunload", () => {
    localStorage.clear();
    if (socket !== null) {
      socket.close();
    }
  });
});
