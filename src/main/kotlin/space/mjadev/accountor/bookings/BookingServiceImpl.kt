package space.mjadev.accountor.bookings

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.db.BookingRepository
import space.mjadev.accountor.bookings.exceptions.http.NotFoundException
import space.mjadev.accountor.bookings.exceptions.http.TechException
import space.mjadev.accountor.bookings.models.Booking
import space.mjadev.accountor.bookings.models.BookingMapper

@ApplicationScoped
class BookingServiceImpl(
    private val accountRepository: AccountRepository,
    private val bookingRepository: BookingRepository) : BookingService {

    @Transactional
    override fun add(request: BookingService.InsertBookingRequest): Booking {
        val bookingDto = request.toDto()
        bookingDto.account = accountRepository.findById(request.accountId).orElseThrow { NotFoundException() }
        return BookingMapper.INSTANCE.map(bookingRepository.save(bookingDto)) ?: throw TechException("failed to create $request")
    }
}