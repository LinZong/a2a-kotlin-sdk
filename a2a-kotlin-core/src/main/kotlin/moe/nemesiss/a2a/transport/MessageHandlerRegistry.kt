package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import java.util.*

class MessageHandlerRegistry {

    private val _messageHandlers = mutableMapOf<String, HandlerMapping<JSONRPCMessage, JSONRPCResponse>>()

    private val _streamMessageHandlers = mutableMapOf<String, StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>>()


    val messageHandlers: Map<String, HandlerMapping<JSONRPCMessage, JSONRPCResponse>>
        get() = Collections.unmodifiableMap(_messageHandlers)
    val streamMessageHandlers: Map<String, StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>>
        get() = Collections.unmodifiableMap(_streamMessageHandlers)


    @Suppress("UNCHECKED_CAST")
    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>) {

        _messageHandlers[hm.method] = hm as HandlerMapping<JSONRPCMessage, JSONRPCResponse>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>) {
        _streamMessageHandlers[hm.method] = hm as StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>
    }

}