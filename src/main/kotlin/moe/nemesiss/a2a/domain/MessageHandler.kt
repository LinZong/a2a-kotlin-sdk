package moe.nemesiss.a2a.domain

fun interface MessageHandler<T : JSONRPCMessage, R : JSONRPCResponse> {

    fun handle(request: T): R
}