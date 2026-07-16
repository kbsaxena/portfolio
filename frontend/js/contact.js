(function () {
    'use strict';
    var form = document.getElementById('contactForm');
    var status = document.getElementById('formStatus');
    var submitBtn = document.getElementById('contactSubmit');
    if (!form) return;

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        var honeypot = form.querySelector('input[name="website"]');
        if (honeypot && honeypot.value) { status.textContent = 'Sent!'; return; }

        var name = document.getElementById('contactName').value.trim();
        var email = document.getElementById('contactEmail').value.trim();
        var message = document.getElementById('contactMessage').value.trim();

        if (!name || !email || !message) { status.textContent = 'Please fill all fields.'; status.style.color = '#ef4444'; return; }
        if (message.length < 10) { status.textContent = 'Message too short.'; status.style.color = '#ef4444'; return; }

        submitBtn.disabled = true;
        submitBtn.textContent = 'Sending...';
        status.textContent = '';

        try {
            var res = await fetch('https://api.kbsaxena.in/api/contact', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name: name, email: email, subject: 'Portfolio Contact Form', message: message }),
            });
            var data = await res.json();
            if (res.ok) { status.textContent = 'Message sent successfully!'; status.style.color = '#10b981'; form.reset(); }
            else { status.textContent = data.error || 'Failed to send.'; status.style.color = '#ef4444'; }
        } catch (err) { status.textContent = 'Network error.'; status.style.color = '#ef4444'; }
        finally { submitBtn.disabled = false; submitBtn.textContent = 'Send Message'; }
    });
})();
