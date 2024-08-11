package space.mjadev.accountor.bookings.db

import jakarta.persistence.*

@Entity
class AccountDto {

    @Id
    @GeneratedValue
    @Column(name = "id_account", nullable = false, updatable = false)
    var accountId: Long? = null

    @Column(name = "txt_name", nullable = false)
    lateinit var name: String

    @Column(name = "id_user", nullable = false)
    lateinit var user: String

    @Column(name = "txt_description")
    var description: String? = null

    @OneToMany(targetEntity = BookingDto::class)
    var bookings: List<BookingDto>? = ArrayList()
}
