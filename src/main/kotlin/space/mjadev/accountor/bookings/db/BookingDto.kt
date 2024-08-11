package space.mjadev.accountor.bookings.db

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class BookingDto {

    @Id
    @GeneratedValue
    @Column(name = "id_booking", nullable = false, updatable = false)
    var bookingId: Long? = null

    @Column(name = "txt_name", nullable = false)
    lateinit var name: String

    @JoinColumn(name = "id_account", nullable = false)
    @ManyToOne
    lateinit var account: AccountDto

    @Column(name = "txt_description")
    var description: String? = null

    @Column(name = "num_amount", nullable = false)
    lateinit var amount: BigDecimal

    @Column(name = "txt_currency")
    var currency: String? = null
}