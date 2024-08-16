package space.mjadev.accountor.bookings.exceptions.http

enum class ErrorCode private constructor(
    val code: Int
) {

    INVALID(3),
    NOT_FOUND(5),
    SERVER_ERROR(2);

    fun getGrpcCode() = code
}