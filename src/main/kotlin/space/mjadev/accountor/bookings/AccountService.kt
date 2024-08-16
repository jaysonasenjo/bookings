package space.mjadev.accountor.bookings

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import space.mjadev.accountor.bookings.db.AccountDto
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException
import space.mjadev.accountor.bookings.models.Account
import java.util.stream.Stream

interface AccountService {

    fun add(name: String,
            userId: String,
            description: String? = null): Uni<Account>
    fun get(accountId: Long): Uni<Account>
    fun getAll(): Uni<List<Account>>

}