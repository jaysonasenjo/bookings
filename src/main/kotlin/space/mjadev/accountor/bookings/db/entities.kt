package space.mjadev.accountor.bookings.db

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import space.mjadev.accountor.bookings.Account
import java.math.BigDecimal

@Entity
class AccountDto: PanacheEntityBase {

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
}

fun AccountDto.toModel() = Account(
    accountId = this.accountId,
    name = this.name,
    userId = this.user,
    description = this.description
)

@Entity
class BookingDto: PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "id_booking", nullable = false, updatable = false)
    var bookingId: Long? = null

    @Column(name = "txt_name", nullable = false)
    lateinit var name: String

    @Column(name = "txt_description")
    var description: String? = null

    @Column(name = "num_amount", nullable = false)
    lateinit var amount: BigDecimal

    @Column(name = "txt_currency")
    var currency: String? = null
}