"""Application exception hierarchy."""


class AppError(Exception):
    """Base application error."""

    status_code: int = 500
    error_code: str = "INTERNAL_ERROR"
    message: str = "An unexpected error occurred"

    def __init__(self, message: str | None = None, **kwargs):
        self.message = message or self.__class__.message
        self.details = kwargs
        super().__init__(self.message)

    def to_dict(self) -> dict:
        result = {
            "error": self.error_code,
            "message": self.message,
            "status_code": self.status_code,
        }
        if self.details:
            result["details"] = self.details
        return result


class ValidationError(AppError):
    status_code = 400
    error_code = "VALIDATION_ERROR"
    message = "Invalid input provided"


class AuthenticationError(AppError):
    status_code = 401
    error_code = "AUTHENTICATION_ERROR"
    message = "Authentication required"


class ForbiddenError(AppError):
    status_code = 403
    error_code = "FORBIDDEN"
    message = "Access denied"


class CSRFError(AppError):
    status_code = 403
    error_code = "CSRF_ERROR"
    message = "CSRF validation failed"


class IPBlockedError(AppError):
    status_code = 403
    error_code = "IP_BLOCKED"
    message = "Your IP has been temporarily blocked"


class RateLimitError(AppError):
    status_code = 429
    error_code = "RATE_LIMIT_EXCEEDED"
    message = "Too many requests, please try again later"


class ServiceUnavailableError(AppError):
    status_code = 503
    error_code = "SERVICE_UNAVAILABLE"
    message = "Service temporarily unavailable"


class InternalError(AppError):
    status_code = 500
    error_code = "INTERNAL_ERROR"
    message = "An internal error occurred"
