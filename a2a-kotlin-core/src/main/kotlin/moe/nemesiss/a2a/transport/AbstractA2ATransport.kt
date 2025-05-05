package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.serialization.JSONRPCMessageSerializer

abstract class AbstractA2ATransport<in E : TransportEndpoint> : A2ATransport<E> {

    protected val messageHandlers = mutableMapOf<String, HandlerMapping<JSONRPCMessage, JSONRPCResponse>>()

    protected val streamMessageHandlers = mutableMapOf<String, StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>>()


    @Suppress("UNCHECKED_CAST")
    override fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>) {
        messageHandlers[hm.method] = hm as HandlerMapping<JSONRPCMessage, JSONRPCResponse>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>) {
        streamMessageHandlers[hm.method] = hm as StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>
    }


}