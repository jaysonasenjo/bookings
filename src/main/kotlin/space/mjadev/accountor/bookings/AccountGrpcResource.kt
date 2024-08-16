package space.mjadev.accountor.bookings

import com.google.protobuf.Empty
import io.grpc.Status
import io.quarkus.grpc.GrpcService
import io.smallrye.mutiny.Uni
import space.mjadev.accountor.bookings.api.AccountHandler
import space.mjadev.accountor.bookings.api.BookingsApi
import space.mjadev.accountor.bookings.api.BookingsApi.AccountList
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException
import space.mjadev.accountor.bookings.models.Account

@GrpcService
class AccountGrpcResource(
    private val accountService: AccountService): AccountHandler {

    override fun createAccount(request: BookingsApi.CreateAccountRequest?): Uni<BookingsApi.Account> {
        return accountService.add(
            name = request?.name ?: throw InvalidArgumentException("invalid request $request"),
            userId = request.user,
            description = request.description
        )
            .onFailure()
            .transform { Status.INVALID_ARGUMENT.withCause(it.cause).asRuntimeException()}
            .onItem()
            .transform { mapToAccount(it) }
            .log()
    }

    override fun getAccounts(request: Empty?): Uni<BookingsApi.AccountList>? {
            return accountService.getAll()
                .onItem()
                .transform { accounts ->
                    AccountList.newBuilder()
                    .addAllAccounts(accounts.map { mapToAccount(it) })
                    .build()
                }
                .log()
        }

    private fun mapToAccount(account: Account): BookingsApi.Account =
        BookingsApi.Account.newBuilder()
            .setAccountId(account.accountId)
            .setName(account.name)
            .setUserId(account.userId)
            .setDescription(account.description)
            .build()
}