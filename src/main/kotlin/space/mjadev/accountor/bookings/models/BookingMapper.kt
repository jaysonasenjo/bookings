package space.mjadev.accountor.bookings.models

import space.mjadev.accountor.bookings.db.BookingDto
import space.mjadev.accountor.bookings.exceptions.http.TechException

class BookingMapper private constructor(){

    companion object {
        val INSTANCE: BookingMapper = BookingMapper()
    }

    fun map(dto: BookingDto?): Booking? = dto?.toModel()

    private fun BookingDto.toModel(): Booking = Booking(
        bookingId = bookingId ?: throw TechException("missing id $this"),
        name = name,
        amount = amount,
        currency = currency,
        description = description
    )
}