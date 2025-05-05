package moe.nemesiss.a2a.domain

import moe.nemesiss.a2a.transport.A2AServerTransport

interface LocalA2AServerTransport : A2AServerTransport {
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


    /**
     * Sends a task notification to the specified local transport endpoint using the provided message.
     *
     * @param endpoint the local transport endpoint to which the notification will be sent
     * @param message the request object containing the details of the notification to be sent
     * @return the response object that contains the result of the notification operation
     */
    fun sendNotification(endpoint: LocalTransportEndpoint,
                         message: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse
}


