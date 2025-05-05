package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*

interface A2ATransport {

    fun start()

    fun stop()

    fun <T : JSONRPCResponse> sendMessage(endpoint: TransportEndpoint,
                                          message: JSONRPCRequest,
                                          responseType: Class<T>): T

    fun sendStreamingRequest(endpoint: TransportEndpoint,
                             message: SendTaskStreamingRequest,
                             callback: StreamResponseCallback<SendTaskStreamingResponse>)


    fun handleMessage(message: String): String

    fun handleStreamingMessage(message: String,
                               callback: StreamResponseCallback<String>)


    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>)

    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>)

}
