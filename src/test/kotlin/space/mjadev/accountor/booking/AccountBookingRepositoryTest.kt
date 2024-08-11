package space.mjadev.accountor.booking

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import space.mjadev.accountor.bookings.AccountService
import space.mjadev.accountor.bookings.BookingService
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.db.BookingRepository
import java.math.BigDecimal

@QuarkusTest
class AccountBookingRepositoryTest {

    @Inject
    lateinit var accountRepository: AccountRepository
    @Inject
    lateinit var bookingRepository: BookingRepository

    @Inject
    private lateinit var accountService: AccountService
    @Inject
    private lateinit var bookingService: BookingService

    @AfterEach
    @Transactional
    fun cleanupDb() {
        bookingRepository.deleteAll()
        accountRepository.deleteAll()
    }

    @Test
    @Transactional
    fun when_accountWithBooking_then_returnBookingList() {
        val accountId = mockAccountId()
        mockBooking(accountId)

        val account = accountService.get(accountId)

        assertNotNull(account.bookings)
        assertEquals(1, account.bookings.size)
    }

    @Test
    fun when_accountWithoutBooking_then_returnEmptyList() {
        val accountId = mockAccountId()

        val account = accountService.get(accountId)

        assertNotNull(account.bookings)
        assertEquals(0, account.bookings.size)
    }

    private fun mockAccountId(): Long =
        accountService.add(AccountService.InsertAccountRequest.create(
            name = "New Account",
            userId = "some@random.user"
        )).accountId

    private fun mockBooking(accountId: Long) =
        bookingService.add(BookingService.InsertBookingRequest.create(
            name = "booking 1",
            accountId = accountId,
            amount = BigDecimal(200)
        )).bookingId
}