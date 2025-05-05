package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.HandlerMapping
import moe.nemesiss.a2a.domain.JSONRPCRequest
import moe.nemesiss.a2a.domain.JSONRPCResponse
import moe.nemesiss.a2a.domain.StreamHandlerMapping

interface A2AServerTransport : A2ATransport {

    /**
     * Register handler for specific [JSONRPCRequest].
     */
    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>)

    /**
     * Register handler for specific [JSONRPCRequest] with a streaming response.
     */
    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>)

}