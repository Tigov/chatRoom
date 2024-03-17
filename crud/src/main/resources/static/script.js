// document.addEventListener("DOMContentLoaded", function () {
//   loadRooms();

//   function loadRooms() {
//     fetch("/api/rooms") // Replace with your actual API endpoint
//       .then((response) => response.json())
//       .then((rooms) => {
//         const roomsList = document.getElementById("rooms");
//         roomsList.innerHTML = "";
//         rooms.forEach((room) => {
//           const li = document.createElement("li");
//           li.textContent = room.name;
//           li.onclick = () => loadMessages(room.id);
//           roomsList.appendChild(li);
//         });
//       });
//   }

//   function loadMessages(roomId) {
//     fetch(`/api/rooms/${roomId}/messages`) // Replace with your actual API endpoint
//       .then((response) => response.json())
//       .then((messages) => {
//         const messageArea = document.getElementById("message-area");
//         const roomTitle = document.getElementById("room-title");
//         roomTitle.textContent = `Messages in Room ${roomId}`;
//         const messagesDiv = document.getElementById("messages");
//         messagesDiv.innerHTML = "";
//         messages.forEach((message) => {
//           const messageDiv = document.createElement("div");
//           messageDiv.classList.add("message");
//           messageDiv.innerHTML = `<span class="user">${message.user}:</span> ${message.text}`;
//           messagesDiv.appendChild(messageDiv);
//         });
//         messageArea.style.display = "block";
//       });
//   }
// });
