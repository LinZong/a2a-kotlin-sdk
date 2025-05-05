package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*


interface A2ATransport<in E : TransportEndpoint> {

    /**
     * Start the transport. After started, this transport can be used for sending/handling message.
     */
    fun start()

    /**
     * Stop the transport.
     */
    fun stop()

    /**
     * Send s [message] to [endpoint].
     * @param endpoint Endpoint which will receive the [message].
     * @param message A concrete [JSONRPCRequest] message instance.
     * @param responseType Type of response message. Must be corresponding to the [message].
     */
    fun <T : JSONRPCResponse> sendMessage(endpoint: E,
                                          message: JSONRPCRequest,
                                          responseType: Class<T>): T

    /**
     * Send a [message] to endpoint and expect for a streaming response observed through [callback].
     * @param endpoint Endpoint which will receive the [message].
     * @param message A concrete [JSONRPCRequest] message instance. Currently, it must be [SendTaskStreamingRequest].
     * @param callback A callback to receive streaming response.
     */
    fun sendStreamingRequest(endpoint: E,
                             message: SendTaskStreamingRequest,
                             callback: StreamResponseCallback<SendTaskStreamingResponse>)


    /**
     * Register handler for specific [JSONRPCRequest].
     */
    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerMessageHandler(hm: HandlerMapping<T, R>)

    /**
     * Register handler for specific [JSONRPCRequest] with a streaming response.
     */
    fun <T : JSONRPCRequest, R : JSONRPCResponse> registerStreamingMessageHandler(hm: StreamHandlerMapping<T, R>)

}
