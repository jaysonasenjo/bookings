package space.mjadev.accountor.bookings

import io.grpc.ForwardingServerCallListener
import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.grpc.Status
import jakarta.enterprise.context.ApplicationScoped
import space.mjadev.accountor.bookings.exceptions.http.HttpException
import space.mjadev.accountor.bookings.exceptions.http.InvalidArgumentException

/**
 * BUG Exception not caught when thrown
 */
@ApplicationScoped
class GrpcExceptionHandler: ServerInterceptor {

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        serverCall: ServerCall<ReqT, RespT>?,
        metadata: Metadata?,
        serverCallHandler: ServerCallHandler<ReqT, RespT>?
    ): ServerCall.Listener<ReqT> {
        val delegate = serverCallHandler?.startCall(serverCall, metadata)
        return ExceptionHandler<ReqT, RespT>(delegate, metadata, serverCall)
    }

    private class ExceptionHandler<ReqT: Any?, RespT: Any?>(
        delegate: ServerCall.Listener<ReqT>?,
        val metadata: Metadata?,
        val serverCall: ServerCall<ReqT, RespT>?
    )
        : ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {

        override fun onHalfClose() {
            try {
                println("trying half close")
                super.onHalfClose()
            } catch (exception: RuntimeException) {
                handleException(exception)
                throw exception
            }
        }

        override fun onCancel() {
            try {
                println("trying cancel")
                super.onCancel()
            } catch (exception: RuntimeException) {
                handleException(exception)
                throw exception
            }
        }

        override fun onComplete() {
            try {
                println("trying complete")
                super.onComplete()
            } catch (exception: RuntimeException) {
                handleException(exception)
                throw exception
            }
        }

        override fun onReady() {
            try {
                println("trying ready")
                super.onReady()
            } catch (exception: RuntimeException) {
                handleException(exception)
                throw exception
            }
        }

        private fun handleException(exception: RuntimeException) {
            println("find status for: $exception")

            val status: Status = when(exception) {
                is InvalidArgumentException -> Status.INVALID_ARGUMENT
                is HttpException -> {
                    println("try to parse code ${exception.getErrorCode().getGrpcCode()}")
                    try {
                        Status.fromCodeValue(exception.getErrorCode().getGrpcCode())
                    } catch (err: Exception) {
                        println("failed to parse")
                        err.printStackTrace()
                        Status.UNKNOWN
                    }
                }
                else -> Status.UNKNOWN
            }

            println("status created: $status")

            serverCall?.close(status, metadata)
        }
    }
}