package space.mjadev.accountor.bookings

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.models.AccountMapper

@ApplicationScoped
class AccountServiceImpl: AccountService {

    @Inject
    private lateinit var accountRepository: AccountRepository

    @Transactional
    override fun add(request: AccountService.InsertAccountRequest) = AccountMapper.INSTANCE
        .map(accountRepository.save(request.toDto()))
}