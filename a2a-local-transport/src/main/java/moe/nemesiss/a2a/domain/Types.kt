package moe.nemesiss.a2a.domain

import moe.nemesiss.a2a.transport.A2ATransport

interface LocalA2ATransport : A2ATransport<LocalTransportEndpoint> {
    /**
     * Expose method to accept JSON-RPC messages from local calls.
     */
    fun handleMessage(message: String): String

    /**
     * Expose method to accept JSON-RPC messages from local calls.
     *
     */
    fun handleStreamingMessage(message: String,
                               callback: StreamResponseCallback<String>)
}


