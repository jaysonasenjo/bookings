package space.mjadev.accountor.bookings.models

import space.mjadev.accountor.bookings.db.AccountDto
import space.mjadev.accountor.bookings.exceptions.http.TechException

class AccountMapper private constructor() {

    companion object {
        val INSTANCE: AccountMapper = AccountMapper()
    }

    fun map(dto: AccountDto?): Account? = dto?.toModel()

    private fun AccountDto.toModel(): Account = Account(
        accountId = accountId ?: throw TechException("missing id $this"),
        name = name,
        userId = user,
        description = description,
        bookings = bookings.mapNotNull { BookingMapper.INSTANCE.map(it) }
    )
}