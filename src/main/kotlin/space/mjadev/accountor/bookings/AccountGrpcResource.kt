package space.mjadev.accountor.bookings

import io.quarkus.grpc.GrpcService
import io.smallrye.common.annotation.Blocking
import io.smallrye.mutiny.Multi
import space.mjadev.accountor.bookings.api.AccountHandler
import space.mjadev.accountor.bookings.api.BookingsApi

@GrpcService
class AccountGrpcResource(
    private val accountService: AccountService): AccountHandler {

        @Blocking
        override fun getAccount(request: BookingsApi.AccountRequest?): Multi<BookingsApi.Account> {
            return Multi.createFrom().iterable(
                accountService.getAll().map { BookingsApi.Account.newBuilder()
                    .setAccountId(it.accountId)
                    .setName(it.name)
                    .setUserId(it.userId)
                    .setDescription(it.description)
                    .build()
                }
            )
        }

}