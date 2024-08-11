package space.mjadev.accountor.booking

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import space.mjadev.accountor.bookings.AccountService
import space.mjadev.accountor.bookings.BookingService
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.db.BookingRepository
import space.mjadev.accountor.bookings.exceptions.http.ErrorCode
import space.mjadev.accountor.bookings.exceptions.http.HttpException
import java.math.BigDecimal

@QuarkusTest
class InsertBookingRepositoryTest {

    @Inject
    private lateinit var accountRepository: AccountRepository
    @Inject
    private lateinit var bookingRepository: BookingRepository

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
    fun when_validInsertBooking_then_returnNewId() {
        val accountId = mockAccountId()
        val bookingDto = bookingService.add(BookingService.InsertBookingRequest.create(
            name = "booking 1",
            accountId = accountId,
            amount = BigDecimal(200),
        ))

        assertNotNull(bookingDto)
        assertNotNull(bookingDto.bookingId)
        assertEquals("booking 1", bookingDto.name)
        assertEquals(BigDecimal(200), bookingDto.amount)
    }

    @Test
    fun when_accountIdNotExist_then_throwException() {
        val exception = assertThrows<HttpException> {
            bookingService.add(BookingService.InsertBookingRequest.create(
                name = "booking 1",
                accountId = 0L,
                amount = BigDecimal(200),
            ))
        }

        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode())
    }

    @Test
    fun when_emptyName_then_throwException() {
        val exception = assertThrows<HttpException> {
            val accountId = mockAccountId()
            bookingService.add(BookingService.InsertBookingRequest.create(
                name = "",
                accountId = accountId,
                amount = BigDecimal(200),
            ))
        }

        assertEquals(ErrorCode.INVALID, exception.getErrorCode())
    }

    private fun mockAccountId(): Long =
        accountService.add(AccountService.InsertAccountRequest.create(
            name = "New Account",
            userId = "some@random.user"
        )).accountId

}