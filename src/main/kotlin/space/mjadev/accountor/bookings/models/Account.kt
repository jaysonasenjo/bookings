package space.mjadev.accountor.bookings.models

data class Account(
    val accountId: Long,
    var userId: String,
    var name: String,
    var description: String? = null,
    var bookings: List<Booking>? = ArrayList()
)
