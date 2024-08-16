package space.mjadev.accountor.bookings.db
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

import java.math.BigDecimal

@Entity
class BookingDto(
    @Id
    @GeneratedValue
    @Column(name = "id_booking", nullable = false, updatable = false)
    var bookingId: Long? = null,

    @Column(name = "txt_name", nullable = false)
    var name: String,

    @JoinColumn(name = "id_account", nullable = false)
    @ManyToOne
    var account: AccountDto? = null,

    @Column(name = "txt_description")
    var description: String? = null,

    @Column(name = "num_amount", nullable = false)
    var amount: BigDecimal,

    @Column(name = "txt_currency")
    var currency: String? = null
)