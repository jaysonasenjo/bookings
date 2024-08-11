package space.mjadev.accountor.bookings

import space.mjadev.accountor.bookings.db.AccountDto
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException
import space.mjadev.accountor.bookings.models.Account

interface AccountService {

    fun add(request: InsertAccountRequest): Account
    fun get(accountId: Long): Account

    class InsertAccountRequest private constructor(
        val name: String,
        private val userId: String,
        private val description: String?) {

        companion object {
            fun create(name: String,
                       userId: String,
                       description: String? = null): InsertAccountRequest {
                if (name.isBlank() || userId.isBlank()) throw InvalidArgumentException()
                return InsertAccountRequest(name, userId, description)
            }
        }

        fun toDto(): AccountDto = AccountDto(name = name, user = userId, description = description)
    }
}