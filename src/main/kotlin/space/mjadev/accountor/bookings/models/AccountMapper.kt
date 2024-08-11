package space.mjadev.accountor.bookings.models

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import space.mjadev.accountor.bookings.db.AccountDto

@Mapper(uses = [BookingMapper::class])
interface AccountMapper {

    companion object {
        val INSTANCE = Mappers.getMapper(AccountMapper::class.java)
    }

    @Mapping(source = "user", target = "userId")
    fun map(dto: AccountDto): Account
}