package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.serialization.JSONRPCMessageSerializer

abstract class AbstractA2ATransport<E : TransportEndpoint> : A2ATransport<E> {

    private val messageHandlers = mutableMapOf<String, HandlerMapping<JSONRPCMessage, JSONRPCResponse>>()

    private val streamMessageHandlers = mutableMapOf<String, StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>>()


    @Suppress("UNCHECKED_CAST")
    override fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>) {
        messageHandlers[hm.method] = hm as HandlerMapping<JSONRPCMessage, JSONRPCResponse>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>) {
        streamMessageHandlers[hm.method] = hm as StreamHandlerMapping<JSONRPCMessage, JSONRPCResponse>
    }


    override fun handleMessage(message: String): String {
        val req = JSONRPCMessageSerializer.deserialize(message, JSONRPCRequest::class.java)
        val hm =
            messageHandlers[req.method] ?: return JSONRPCMessageSerializer.serialize(JSONRPCResponse(id = req.id,
                                                                                                     error = UnsupportedOperationError(
                                                                                                         message = "No handler for method: ${req.method}")))


        val requestMessage = JSONRPCMessageSerializer.deserialize(message, hm.requestType)
        val responseMessage = hm.handler.handle(requestMessage)
        return JSONRPCMessageSerializer.serialize(responseMessage)
    }

    override fun handleStreamingMessage(message: String, callback: StreamResponseCallback<String>) {
        val req = JSONRPCMessageSerializer.deserialize(message, JSONRPCRequest::class.java)
        val hm =
            streamMessageHandlers[req.method] ?: return run {
                callback.onComplete(JSONRPCMessageSerializer.serialize(JSONRPCResponse(id = req.id,
                                                                                       error = UnsupportedOperationError(
                                                                                           message = "No handler for method: ${req.method}"))))
            }

        val requestMessage = JSONRPCMessageSerializer.deserialize(message, hm.requestType)
        hm.handler.handle(requestMessage, object : StreamResponseCallback<JSONRPCResponse> {
            override fun onNext(value: JSONRPCResponse) {
                callback.onNext(JSONRPCMessageSerializer.serialize(value))
            }

            override fun onComplete(value: JSONRPCResponse) {
                callback.onComplete(JSONRPCMessageSerializer.serialize(value))
            }

            override fun onError(t: Throwable) {
                callback.onError(t)
            }
        })
    }
}