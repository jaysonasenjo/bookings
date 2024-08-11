package space.mjadev.accountor.booking

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import space.mjadev.accountor.bookings.AccountService
import space.mjadev.accountor.bookings.BookingService
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.db.BookingRepository
import java.math.BigDecimal

@QuarkusTest
@Transactional
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
    fun cleanupDb() {
        bookingRepository.deleteAll()
        accountRepository.deleteAll()
    }

    @Test
    fun when_accountWithBooking_then_returnBookingList() {
        val accountId = mockAccountId()
        val bookingId = mockBooking(accountId)

        val account = accountRepository.findById(accountId).orElseThrow()
        val booking = bookingRepository.findById(bookingId).orElseThrow()

        assertIterableEquals(
            listOf(booking),
            account?.bookings
        )
    }

    @Test
    fun when_accountWithoutBooking_then_returnEmptyList() {

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