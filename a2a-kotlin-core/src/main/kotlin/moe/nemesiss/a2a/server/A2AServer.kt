package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.domain.*

interface A2AServer {

    /**
     * Start the server. After started, the server will begin to handle incoming request.
     */
    fun start()

    /**
     * Stop the server.
     */
    fun stop()

    /**
     * Handle the request of retrieves the current state of a task.
     */
    fun onGetTask(request: GetTaskRequest): GetTaskResponse

    /**
     * Handle the request of cancelling a running task.
     */
    fun onCancelTask(request: CancelTaskRequest): CancelTaskResponse

    /**
     * Handle the request of initiate or continue a task.
     */
    fun onSendTask(request: SendTaskRequest): SendTaskResponse

    /**
     * Handle the request of running a task and producing streaming response.
     */
    fun onSendTaskSubscribe(request: SendTaskStreamingRequest,
                            callback: StreamResponseCallback<SendTaskStreamingResponse>)

    /**
     * Configures the push notification settings for a specific task.
     *
     * @param request the request object containing the task ID and the push notification configuration.
     * @return the response object containing the updated push notification configuration or an error if the operation fails.
     */
    fun onSetTaskPushNotification(request: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse

    /**
     * Handles the retrieval of push notification configuration for a specific task.
     *
     * @param request the request object containing the task ID for which the push notification configuration should be retrieved.
     * @return the response object containing the push notification configuration for the specified task, or an error if the operation fails.
     */
    fun onGetTaskPushNotification(request: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse

    /**
     * Sends a push notification for a specific task to the specified URL.
     *
     * @param url the destination URL to which the push notification will be sent.
     * @param request the request object containing the task details and other push notification parameters.
     * @return a response object indicating the success or failure of the operation along with relevant details.
     */
    fun sendTaskPushNotification(url: String,
                                 request: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse
}