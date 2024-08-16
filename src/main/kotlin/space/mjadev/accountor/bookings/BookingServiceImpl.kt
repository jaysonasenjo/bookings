package space.mjadev.accountor.bookings

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.tuples.Tuple2
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import space.mjadev.accountor.bookings.db.AccountDto
import space.mjadev.accountor.bookings.db.AccountRepository
import space.mjadev.accountor.bookings.db.BookingRepository
import space.mjadev.accountor.bookings.exceptions.http.NotFoundException
import space.mjadev.accountor.bookings.models.Booking
import space.mjadev.accountor.bookings.models.BookingMapper

@ApplicationScoped
class BookingServiceImpl(
    private val accountRepository: AccountRepository,
    private val bookingRepository: BookingRepository) : BookingService {

    @Transactional
    override fun add(request: BookingService.InsertBookingRequest): Uni<Booking> {
        val bookingDto = request.toDto()
        val linkedAccount = accountRepository.findById(request.accountId).await().indefinitely()
        linkedAccount.bookings.add(bookingDto)
        return bookingRepository.persist(bookingDto).map { BookingMapper.INSTANCE.map(it) }
    }
}