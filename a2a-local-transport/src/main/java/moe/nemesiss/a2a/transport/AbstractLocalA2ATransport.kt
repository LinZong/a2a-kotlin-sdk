package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost
import moe.nemesiss.a2a.serialization.JSONRPCMessageSerializer

abstract class AbstractLocalA2ATransport(protected val host: LocalA2AHost) :
        AbstractA2ATransport<LocalTransportEndpoint>(), LocalA2ATransport {

    override fun <T : JSONRPCResponse> sendMessage(endpoint: LocalTransportEndpoint,
                                                   message: JSONRPCRequest,
                                                   responseType: Class<T>): T {
        val peer =
            host.resolveTransport(endpoint) ?: throw RuntimeException("Cannot locate transport from endpoint: $endpoint")
        val responseJson = peer.handleMessage(JSONRPCMessageSerializer.serialize(message))
        return JSONRPCMessageSerializer.deserialize(responseJson, responseType)
    }

    override fun sendStreamingRequest(endpoint: LocalTransportEndpoint,
                                      message: SendTaskStreamingRequest,
                                      callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        val peer =
            host.resolveTransport(endpoint) ?: throw RuntimeException("Cannot locate transport from endpoint: $endpoint")
        peer.handleStreamingMessage(JSONRPCMessageSerializer.serialize(message),
                                    object : StreamResponseCallback<String> {
                                        override fun onNext(value: String) {
                                            callback.onNext(JSONRPCMessageSerializer.deserialize(value,
                                                                                                 SendTaskStreamingResponse::class.java))
                                        }

                                        override fun onComplete(value: String) {
                                            callback.onComplete(JSONRPCMessageSerializer.deserialize(value,
                                                                                                     SendTaskStreamingResponse::class.java))
                                        }

                                        override fun onError(t: Throwable) {
                                            callback.onError(t)
                                        }
                                    })
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