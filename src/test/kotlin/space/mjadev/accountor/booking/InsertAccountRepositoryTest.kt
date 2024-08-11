package space.mjadev.accountor.booking

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import space.mjadev.accountor.bookings.AccountService
import space.mjadev.accountor.bookings.exceptions.http.ErrorCode
import space.mjadev.accountor.bookings.exceptions.http.HttpException

@QuarkusTest
class InsertAccountRepositoryTest {

    @Inject
    private lateinit var accountService: AccountService

    @Test
    fun when_insertValidAccount_then_returnAccountWithId() {
        val insertAccount = AccountService.InsertAccountRequest.create(
            name = "Kotlin",
            userId = "some@email.com",
        )
        val account = accountService.add(insertAccount)

        assertNotNull(account)
        assertNotNull(account.accountId)
        assertEquals(account.name, "Kotlin")
    }

    @Test
    fun when_emptyName_then_throwException() {
        val exception = assertThrows<HttpException> {
            AccountService.InsertAccountRequest.create(
                name = "",
                userId = "some@email.com"
            )
        }

        assertEquals(ErrorCode.INVALID, exception.getErrorCode())
    }

    @Test
    fun when_emptyUserIs_then_throwException() {
        val exception = assertThrows<HttpException> {
            AccountService.InsertAccountRequest.create(
                name = "new account",
                userId = ""
            )
        }

        assertEquals(ErrorCode.INVALID, exception.getErrorCode())
    }
}