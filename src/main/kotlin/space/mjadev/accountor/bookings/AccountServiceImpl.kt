package space.mjadev.accountor.bookings

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.db.BookingRepository
import space.mjadev.accountor.bookings.exceptions.http.NotFoundException
import space.mjadev.accountor.bookings.exceptions.http.TechException
import space.mjadev.accountor.bookings.models.Account
import space.mjadev.accountor.bookings.models.AccountMapper

@ApplicationScoped
class AccountServiceImpl: AccountService {

    @Inject
    private lateinit var accountRepository: AccountRepository
    @Inject
    private lateinit var bookingRepository: BookingRepository

    @Transactional
    override fun add(request: AccountService.InsertAccountRequest) = AccountMapper.INSTANCE
        .map(accountRepository.save(request.toDto())) ?: throw TechException("failed to create $request")

    override fun get(accountId: Long): Account {
        val account = accountRepository.findById(accountId).orElseThrow { NotFoundException() }
        account.bookings.addAll(bookingRepository.findBookingsByAccount(account))
        return AccountMapper.INSTANCE.map(account) ?: throw TechException()
    }
}