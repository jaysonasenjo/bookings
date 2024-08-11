package space.mjadev.accountor.bookings.exceptions.http

enum class ErrorCode(code: Int) {

    INVALID(400),
    NOT_FOUND(404)
}