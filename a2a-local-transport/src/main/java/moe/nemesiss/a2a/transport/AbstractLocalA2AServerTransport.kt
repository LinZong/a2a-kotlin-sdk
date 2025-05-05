package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost
import moe.nemesiss.a2a.serialization.JSONRPCMessageSerializer

abstract class AbstractLocalA2AServerTransport(protected val host: LocalA2AHost,
                                               protected val listenEndpoint: LocalTransportEndpoint) :
        AbstractA2AServerTransport(), LocalA2AServerTransport {


    override fun start() {
        host.registerTransport(listenEndpoint, this)
    }

    override fun stop() {
        host.removeTransport(listenEndpoint)
    }


    override fun handleMessage(message: String): String {
        val req = JSONRPCMessageSerializer.deserialize(message, JSONRPCRequest::class.java)
        val hm =
            mhr.messageHandlers[req.method] ?: return JSONRPCMessageSerializer.serialize(JSONRPCResponse(id = req.id,
                                                                                                         error = UnsupportedOperationError(
                                                                                                             message = "No handler for method: ${req.method}")))


        val requestMessage = JSONRPCMessageSerializer.deserialize(message, hm.requestType)
        val responseMessage = hm.handler.handle(requestMessage)
        return JSONRPCMessageSerializer.serialize(responseMessage)
    }

    override fun handleStreamingMessage(message: String, callback: StreamResponseCallback<String>) {
        val req = JSONRPCMessageSerializer.deserialize(message, JSONRPCRequest::class.java)
        val hm =
            mhr.streamMessageHandlers[req.method] ?: return run {
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

    override fun sendNotification(endpoint: LocalTransportEndpoint,
                                  message: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse {
        val transport =
            host.resolveTransport(endpoint) ?: return SendTaskPushNotificationResponse(error = PushNotificationNotSupportedError())

        val response = transport.handleMessage(JSONRPCMessageSerializer.serialize(message))
        return JSONRPCMessageSerializer.deserialize(response, SendTaskPushNotificationResponse::class.java)
    }
}