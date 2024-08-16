package space.mjadev.accountor.bookings.db

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BookingRepository: PanacheRepository<BookingDto>
