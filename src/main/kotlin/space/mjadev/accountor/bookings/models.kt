package space.mjadev.accountor.bookings

data class Account(
    val accountId: Long? = null,
    var userId: String,
    var name: String,
    var description: String? = null
)