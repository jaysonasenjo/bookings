package space.mjadev.accountor.bookings.db

import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository: JpaRepository<BookingDto, Long> {

    fun findBookingsByAccount(account: AccountDto): List<BookingDto>
}
