package space.mjadev.accountor.bookings.db

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<AccountDto, Long>


