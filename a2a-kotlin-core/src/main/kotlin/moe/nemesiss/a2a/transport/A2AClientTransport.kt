package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*

interface A2AClientTransport<in E : TransportEndpoint> : A2ATransport<E> {

    /**
     * Sends a JSON-RPC request message and retrieves the corresponding response of a specified type.
     * Unlike [A2ATransport.sendMessage], this method doesn't need the 'endpoint' parameter,
     * but send the message to the preconfigured server endpoint instead.
     *
     * @param message The JSON-RPC request to be sent.
     * @param responseType The class type of the expected JSON-RPC response.
     * @return The JSON-RPC response parsed as an instance of the specified type.
     */
    fun <T : JSONRPCResponse> sendMessage(message: JSONRPCRequest, responseType: Class<T>): T

    /**
     * Sends a streaming request to subscribe to real-time updates for a task.
     * Unlike [A2ATransport.sendStreamingRequest], this method doesn't need the 'endpoint' parameter,
     * but send the message to the preconfigured server endpoint instead.
     *
     * @param message The streaming request containing the method, parameters, and optional request ID.
     * @param callback The callback to handle streaming responses, including events, completion, or errors.
     */
    fun sendStreamingRequest(message: SendTaskStreamingRequest,
                             callback: StreamResponseCallback<SendTaskStreamingResponse>)
}