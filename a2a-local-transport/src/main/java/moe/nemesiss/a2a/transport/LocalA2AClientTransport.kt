package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost

class LocalA2AClientTransport(host: LocalA2AHost,
                              val serverEndpoint: LocalTransportEndpoint,
                              val clientNotifyEndpoint: LocalTransportEndpoint? = null) :
        AbstractLocalA2ATransport(host), A2AClientTransport<LocalTransportEndpoint> {
    override fun start() {
        clientNotifyEndpoint?.let { host.registerTransport(it, this) }
    }

    override fun stop() {
        clientNotifyEndpoint?.let { host.removeTransport(it) }
    }


    override fun <T : JSONRPCResponse> sendMessage(message: JSONRPCRequest, responseType: Class<T>): T {
        return sendMessage(serverEndpoint, message, responseType)
    }

    override fun sendStreamingRequest(message: SendTaskStreamingRequest,
                                      callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        sendStreamingRequest(serverEndpoint, message, callback)
    }
}