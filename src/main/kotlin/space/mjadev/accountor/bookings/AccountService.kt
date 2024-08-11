package space.mjadev.accountor.bookings

import space.mjadev.accountor.bookings.db.AccountDto
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException
import space.mjadev.accountor.bookings.models.Account

interface AccountService {

    fun add(request: InsertAccountRequest): Account

    class InsertAccountRequest private constructor(
        val name: String,
        val userId: String,
        val description: String?) {

        companion object {
            fun create(name: String,
                       userId: String,
                       description: String? = null): InsertAccountRequest {
                if (name.isBlank() || userId.isBlank()) throw InvalidArgumentException()
                return InsertAccountRequest(name, userId, description)
            }
        }

        fun toDto(): AccountDto {
            val dto = AccountDto()
            dto.name = name
            dto.user = userId
            dto.description = description
            return dto
        }
    }
}