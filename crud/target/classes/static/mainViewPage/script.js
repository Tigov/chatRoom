document.addEventListener("DOMContentLoaded", function () {
  loadRooms();

  async function loadRooms() {
    try {
      const res = await fetch("/rooms");
      const allRooms = await res.json();

      const roomsList = document.getElementById("rooms");
      roomsList.innerHTML = ""; // Clear existing entries

      allRooms.forEach((room) => {
        const listItem = document.createElement("li");
        listItem.textContent = `Room ${room.id}: ${room.numberOfUsersInRoom} users`;
        roomsList.appendChild(listItem);
      });
    } catch (error) {
      console.error("Error fetching rooms:", error);
    }
  }

  function loadMessages(roomId) {
    fetch(`/rooms/${roomId}/messages`)
      .then((response) => response.json())
      .then((messages) => {
        const messageArea = document.getElementById("message-area");
        const roomTitle = document.getElementById("room-title");
        roomTitle.textContent = `Messages in Room ${roomId}`;
        const messagesDiv = document.getElementById("messages");
        messagesDiv.innerHTML = "";
        messages.forEach((message) => {
          const messageDiv = document.createElement("div");
          messageDiv.classList.add("message");
          messageDiv.innerHTML = `<span class="user">${message.user}:</span> ${message.text}`;
          messagesDiv.appendChild(messageDiv);
        });
        messageArea.style.display = "block";
      });
  }
});
