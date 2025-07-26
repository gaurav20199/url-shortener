// Form switching functionality
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const forgotPasswordForm = document.getElementById('forgotPasswordForm');
const showRegister = document.getElementById('showRegister');
const showLogin = document.getElementById('showLogin');
const showForgotPassword = document.querySelector('.forgot-password');
const backToLogin = document.getElementById('backToLogin');
const logoutBtn = document.getElementById('logoutBtn');
const displayUsername = document.getElementById('displayUsername');

// Check if user is logged in
function checkLoginStatus() {
    const loggedInUser = localStorage.getItem('loggedInUser');
    if (loggedInUser) {
        displayUsername.textContent = loggedInUser;
    }
}

// Show Register Form
showRegister.addEventListener('click', (e) => {
    e.preventDefault();
    loginForm.classList.add('hidden');
    forgotPasswordForm.classList.add('hidden');
    registerForm.classList.remove('hidden');
});

// Show Login Form
showLogin.addEventListener('click', (e) => {
    e.preventDefault();
    registerForm.classList.add('hidden');
    forgotPasswordForm.classList.add('hidden');
    loginForm.classList.remove('hidden');
});

// Show Forgot Password Form
showForgotPassword.addEventListener('click', (e) => {
    e.preventDefault();
    loginForm.classList.add('hidden');
    registerForm.classList.add('hidden');
    forgotPasswordForm.classList.remove('hidden');
});

// Back to Login from Forgot Password
backToLogin.addEventListener('click', (e) => {
    e.preventDefault();
    forgotPasswordForm.classList.add('hidden');
    loginForm.classList.remove('hidden');
});

// Logout functionality
logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('loggedInUser');
    loginForm.classList.remove('hidden');
});

// Password visibility toggle
document.querySelectorAll('input[type="password"]').forEach(input => {
    const togglePassword = document.createElement('button');
    togglePassword.type = 'button';
    togglePassword.className = 'fas fa-eye password-toggle';

    input.parentElement.appendChild(togglePassword);

    togglePassword.addEventListener('click', () => {
        const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
        input.setAttribute('type', type);
        togglePassword.className = `fas fa-eye${type === 'password' ? '' : '-slash'} password-toggle`;
    });
});

// Check login status on page load
checkLoginStatus();

document.addEventListener('DOMContentLoaded', function () {
    const toastElForLogout = document.getElementById('logoutToast');
    const toastElForInvalidCredentials = document.getElementById('invalidCredentialsToast');
    if (toastElForLogout || toastElForInvalidCredentials) {
        const toast = toastElForLogout?new bootstrap.Toast(toastElForLogout, { delay: 5000 }):
                                       new bootstrap.Toast(toastElForInvalidCredentials, { delay: 5000 }) ;
        toast.show();
    }
});

function closeToast() {
  const toast = document.querySelector('.toast');
  if (toast) {
    toast.classList.add('toast-hide');
    setTimeout(() => {
      toast.remove();
    }, 400);
  }
}

setTimeout(() => {
  closeToast();
}, 3000);

