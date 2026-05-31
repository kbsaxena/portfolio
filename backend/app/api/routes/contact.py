"""Contact form endpoint with Brevo email integration."""

from fastapi import APIRouter
from pydantic import BaseModel, EmailStr, Field

from app.core.exceptions import ValidationError
from app.core.logging import get_logger
from app.services.email import send_contact_email

logger = get_logger(__name__)

router = APIRouter(prefix="/api/contact", tags=["contact"])


class ContactRequest(BaseModel):
    name: str = Field(..., min_length=2, max_length=100)
    email: EmailStr
    subject: str = Field(..., min_length=5, max_length=200)
    message: str = Field(..., min_length=10, max_length=5000)


@router.post("")
async def contact(body: ContactRequest):
    """Handle contact form submission."""
    try:
        await send_contact_email(
            name=body.name,
            email=body.email,
            subject=body.subject,
            message=body.message,
        )
        return {"status": "success", "message": "Message sent successfully"}
    except ValidationError:
        raise
    except Exception as e:
        logger.error(f"Contact form error: {e}")
        raise ValidationError("Failed to send message. Please try again later.")
