package space.mjadev.accountor.bookings

import space.mjadev.accountor.bookings.db.BookingDto
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException
import space.mjadev.accountor.bookings.models.Booking
import java.math.BigDecimal

interface BookingService {

    fun add(request: InsertBookingRequest): Booking

    class InsertBookingRequest private constructor(
        val name: String,
        val accountId: Long,
        val amount: BigDecimal,
        val currency: String?,
        val description: String?
    ) {
        companion object {
            fun create(name: String,
                       accountId: Long,
                       amount: BigDecimal,
                       currency: String? = null,
                       description: String? = null): InsertBookingRequest {
                if (name.isBlank()) throw InvalidArgumentException()
                return InsertBookingRequest(name, accountId, amount, currency, description)
            }
        }

        fun toDto(): BookingDto = BookingDto(
            name = name,
            amount = amount,
            currency = currency,
            description = description
        )
    }
}