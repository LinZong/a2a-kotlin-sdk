package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost
import moe.nemesiss.a2a.serialization.JSONRPCMessageSerializer

abstract class AbstractLocalA2ATransport(protected val host: LocalA2AHost) : AbstractA2ATransport() {

    override fun <T : JSONRPCResponse> sendMessage(endpoint: TransportEndpoint,
                                                   message: JSONRPCRequest,
                                                   responseType: Class<T>): T {
        val peer =
            host.resolveTransport(endpoint) ?: throw RuntimeException("Cannot locate transport from endpoint: $endpoint")
        val responseJson = peer.handleMessage(JSONRPCMessageSerializer.serialize(message))
        return JSONRPCMessageSerializer.deserialize(responseJson, responseType)
    }

    override fun sendStreamingRequest(endpoint: TransportEndpoint,
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

}