package moe.nemesiss.a2a.domain

fun interface StreamMessageHandler<T : JSONRPCMessage, R : JSONRPCResponse> {

    fun handle(request: T, callback: StreamResponseCallback<R>)
}