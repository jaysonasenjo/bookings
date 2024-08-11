package space.mjadev.accountor.bookings.exceptions.http

class TechException(message: String? = null) : HttpException(message) {
    override fun getErrorCode(): ErrorCode = ErrorCode.SERVER_ERROR
}