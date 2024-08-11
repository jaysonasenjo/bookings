package space.mjadev.accountor.bookings.db

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class AccountDto(

    @Id
    @GeneratedValue
    @Column(name = "id_account", nullable = false, updatable = false)
    var accountId: Long? = null,

    @Column(name = "txt_name", nullable = false)
    var name: String,

    @Column(name = "id_user", nullable = false)
    var user: String,

    @Column(name = "txt_description")
    var description: String? = null,

    @OneToMany(mappedBy = "account")
    val bookings: MutableList<BookingDto> = mutableListOf()
)
