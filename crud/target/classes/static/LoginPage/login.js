function toggleForms() {
  var loginForm = document.getElementById("login-form");
  var signupForm = document.getElementById("signup-form");
  if (loginForm.style.display === "none") {
    loginForm.style.display = "block";
    signupForm.style.display = "none";
  } else {
    loginForm.style.display = "none";
    signupForm.style.display = "block";
  }
}

async function handleLogin() {
  var username = document.getElementById("login-username").value;
  var password = document.getElementById("login-password").value;

  var response = await fetch("/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `username=${encodeURIComponent(
      username
    )}&password=${encodeURIComponent(password)}`,
  });

  if (response.ok) {
    var token = await response.text();
    localStorage.setItem("token", token);
    window.location.href = "/mainViewPage/index.html";
  } else {
    alert("Incorrect login credentials");
  }
}
