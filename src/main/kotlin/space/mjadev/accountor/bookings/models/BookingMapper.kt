package space.mjadev.accountor.bookings.models

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import space.mjadev.accountor.bookings.db.BookingDto

@Mapper(uses = [AccountMapper::class])
interface BookingMapper {

    companion object {
        val INSTANCE = Mappers.getMapper(BookingMapper::class.java)
    }

    fun map(dto: BookingDto): Booking
}