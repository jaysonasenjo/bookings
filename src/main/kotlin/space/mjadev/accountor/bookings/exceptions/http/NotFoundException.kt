package space.mjadev.accountor.bookings.exceptions.http

class NotFoundException(message: String? = null) : HttpException(message) {
    override fun getErrorCode(): ErrorCode = ErrorCode.NOT_FOUND
}