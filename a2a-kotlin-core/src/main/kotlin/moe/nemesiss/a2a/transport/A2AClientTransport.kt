package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*

interface A2AClientTransport<E : TransportEndpoint> : A2ATransport<E> {

    fun <T : JSONRPCResponse> sendMessage(message: JSONRPCRequest, responseType: Class<T>): T

    fun sendStreamingRequest(message: SendTaskStreamingRequest,
                             callback: StreamResponseCallback<SendTaskStreamingResponse>)
}