package space.mjadev.accountor.bookings.exceptions.http

abstract class HttpException(message: String? = null) : RuntimeException(message) {

    abstract fun getErrorCode(): ErrorCode
}