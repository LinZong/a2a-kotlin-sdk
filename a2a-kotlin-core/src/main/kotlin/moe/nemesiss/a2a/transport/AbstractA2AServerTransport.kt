package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.HandlerMapping
import moe.nemesiss.a2a.domain.JSONRPCRequest
import moe.nemesiss.a2a.domain.JSONRPCResponse
import moe.nemesiss.a2a.domain.StreamHandlerMapping

abstract class AbstractA2AServerTransport : A2AServerTransport {
    protected val mhr = MessageHandlerRegistry()


    override fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>) {
        mhr.registerMessageHandler(hm)
    }

    override fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>) {
        mhr.registerStreamingMessageHandler(hm)
    }
}