package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*

interface A2AClientTransport : A2ATransport {

    fun <T : JSONRPCResponse> sendMessage(message: JSONRPCRequest, responseType: Class<T>): T

    fun sendStreamingRequest(message: SendTaskStreamingRequest,
                             callback: StreamResponseCallback<SendTaskStreamingResponse>)
}