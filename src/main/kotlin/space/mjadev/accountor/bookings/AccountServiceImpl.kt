package space.mjadev.accountor.bookings

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.exceptions.http.NotFoundException
import space.mjadev.accountor.bookings.exceptions.http.TechException
import space.mjadev.accountor.bookings.models.Account
import space.mjadev.accountor.bookings.models.AccountMapper

@ApplicationScoped
class AccountServiceImpl(
    private val accountRepository: AccountRepository
): AccountService {

    @Transactional
    override fun add(request: AccountService.InsertAccountRequest) = AccountMapper.INSTANCE
        .map(accountRepository.save(request.toDto())) ?: throw TechException("failed to create $request")

    override fun get(accountId: Long): Account {
        val account = accountRepository.findById(accountId).orElseThrow { NotFoundException() }
        return AccountMapper.INSTANCE.map(account) ?: throw TechException()
    }

    override fun getAll(): List<Account> {
        return accountRepository.findAll().toList().mapNotNull { AccountMapper.INSTANCE.map(it) }
    }
}