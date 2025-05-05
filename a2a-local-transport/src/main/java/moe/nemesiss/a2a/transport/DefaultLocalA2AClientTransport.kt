package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost
import moe.nemesiss.a2a.serialization.JSONRPCMessageSerializer

open class DefaultLocalA2AClientTransport(protected val host: LocalA2AHost,
                                          protected val serverEndpoint: LocalTransportEndpoint) : A2AClientTransport {


    private val serverTransport
        get() =
            host.resolveTransport(serverEndpoint) ?: throw RuntimeException("Cannot locate transport from endpoint: $serverEndpoint")

    override fun <T : JSONRPCResponse> sendMessage(message: JSONRPCRequest, responseType: Class<T>): T {
        val response = serverTransport.handleMessage(JSONRPCMessageSerializer.serialize(message))
        return JSONRPCMessageSerializer.deserialize(response, responseType)
    }


    override fun sendStreamingRequest(message: SendTaskStreamingRequest,
                                      callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        serverTransport.handleStreamingMessage(JSONRPCMessageSerializer.serialize(message),
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

    override fun start() {

    }

    override fun stop() {

    }
}

