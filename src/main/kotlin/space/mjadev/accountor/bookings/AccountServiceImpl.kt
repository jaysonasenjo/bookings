package space.mjadev.accountor.bookings

import io.grpc.Status
import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import space.mjadev.accountor.bookings.db.AccountDto
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException
import space.mjadev.accountor.bookings.models.Account
import space.mjadev.accountor.bookings.models.AccountMapper

@ApplicationScoped
class AccountServiceImpl(
    private val accountRepository: AccountRepository
): AccountService {

    @WithSession
    @WithTransaction
    override fun add(name: String,
                     userId: String,
                     description: String?
    ): Uni<Account> {
        if (name.isBlank() || userId.isBlank()) {
            throw InvalidArgumentException("invalid parameter name=$name userId=$userId")
        }

        return accountRepository.persistAndFlush(AccountDto(
            name = name,
            user = userId,
            description = description
        ))
            .onItem()
            .transform { AccountMapper.INSTANCE.map(it) }
    }

    override fun get(accountId: Long): Uni<Account> {
        return accountRepository.findById(accountId).map { AccountMapper.INSTANCE.map(it) }
    }

    @WithSession
    override fun getAll(): Uni<List<Account>> = accountRepository.listAll()
        .onItem().transform { it.mapNotNull { AccountMapper.INSTANCE.map(it) } };
}