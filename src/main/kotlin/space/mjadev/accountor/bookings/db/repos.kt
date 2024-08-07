package space.mjadev.accountor.bookings.db

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import space.mjadev.accountor.bookings.Account

@ApplicationScoped
class AccountRepository: PanacheRepository<AccountDto> {

    fun add(account: Account): AccountDto {
        val accountDto = AccountDto()
        accountDto.name = account.name
        accountDto.description = account.description
        accountDto.user = account.userId
        persist(accountDto)
        return accountDto
    }
}

@ApplicationScoped
class BookingRepository: PanacheRepository<BookingDto>