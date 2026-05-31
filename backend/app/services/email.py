"""Brevo email service with MX validation."""

import re

import dns.resolver
import httpx

from app.config import settings
from app.core.exceptions import ValidationError
from app.core.logging import get_logger

logger = get_logger(__name__)

BREVO_API_URL = "https://api.brevo.com/v3/smtp/email"


def _validate_mx(email: str) -> bool:
    """Validate that the email domain has MX records."""
    domain = email.split("@")[-1]
    try:
        records = dns.resolver.resolve(domain, "MX")
        return len(records) > 0
    except (dns.resolver.NoAnswer, dns.resolver.NXDOMAIN, dns.resolver.NoNameservers):
        return False
    except Exception as e:
        logger.warning(f"MX validation error for {domain}: {e}")
        return True  # Allow on DNS errors to avoid false rejections


async def send_contact_email(
    name: str, email: str, subject: str, message: str
) -> None:
    """Send a contact form email via Brevo."""
    # Validate email format
    if not re.match(r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$", email):
        raise ValidationError("Invalid email format")

    # Validate MX records
    if not _validate_mx(email):
        raise ValidationError("Email domain does not accept mail")

    if not settings.brevo_api_key:
        logger.error("Brevo API key not configured")
        raise ValidationError("Email service not configured")

    payload = {
        "sender": {
            "name": "Portfolio Contact Form",
            "email": settings.brevo_sender_email,
        },
        "to": [{"email": settings.brevo_recipient_email}],
        "replyTo": {"email": email, "name": name},
        "subject": f"[Portfolio] {subject}",
        "htmlContent": (
            f"<h3>New Contact Form Submission</h3>"
            f"<p><strong>Name:</strong> {name}</p>"
            f"<p><strong>Email:</strong> {email}</p>"
            f"<p><strong>Subject:</strong> {subject}</p>"
            f"<hr>"
            f"<p>{message}</p>"
        ),
    }

    try:
        async with httpx.AsyncClient(timeout=10) as client:
            response = await client.post(
                BREVO_API_URL,
                json=payload,
                headers={
                    "api-key": settings.brevo_api_key,
                    "Content-Type": "application/json",
                },
            )
            response.raise_for_status()
            logger.info(f"Contact email sent from {email}")
    except httpx.HTTPStatusError as e:
        logger.error(f"Brevo API error: {e.response.status_code} - {e.response.text}")
        raise ValidationError("Failed to send email")
    except Exception as e:
        logger.error(f"Email sending error: {e}")
        raise ValidationError("Email service temporarily unavailable")
