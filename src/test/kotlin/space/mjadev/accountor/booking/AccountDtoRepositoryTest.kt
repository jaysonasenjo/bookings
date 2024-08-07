package space.mjadev.accountor.booking

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import space.mjadev.accountor.bookings.Account
import space.mjadev.accountor.bookings.db.AccountRepository

@QuarkusTest
@Transactional
class AccountDtoRepositoryTest {

    @Inject
    lateinit var repository: AccountRepository

    @Test
    fun when_insertNewAccount_then_returnAccountWithId() {
        val account = repository.add(Account(
            name = "Kotlin",
            userId = "some@email.com",
        ))

        assertTrue(repository.isPersistent(account))

        assertNotNull(account)
        assertNotNull(account.accountId)
        assertEquals(account.name, "Kotlin")
    }
}