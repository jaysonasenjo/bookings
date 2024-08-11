package space.mjadev.accountor.bookings.models

import java.math.BigDecimal

data class Booking(
    val bookingId: Long,
    val name: String,
    var amount: BigDecimal,
    var currency: String?,
    var description: String?
)