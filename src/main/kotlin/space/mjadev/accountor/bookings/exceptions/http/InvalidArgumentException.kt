package space.mjadev.accountor.bookings.exceptions.http

class InvalidArgumentException(message: String? = null) : HttpException(message) {
    override fun getErrorCode() = ErrorCode.INVALID
}