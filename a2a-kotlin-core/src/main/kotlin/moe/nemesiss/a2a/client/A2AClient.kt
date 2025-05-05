package moe.nemesiss.a2a.client

import moe.nemesiss.a2a.domain.*

interface A2AClient {

    /**
     * Sends a message to initiate or continue a task.
     */
    fun sendTask(params: SendTaskRequest): SendTaskResponse

    /**
     * Sends a message and subscribes to real-time updates via SSE.
     */
    fun sendTaskStreaming(params: SendTaskStreamingRequest, callback: StreamResponseCallback<SendTaskStreamingResponse>)

    /**
     * Retrieves the current state of a task.
     */
    fun getTask(params: GetTaskRequest): GetTaskResponse

    /**
     * Requests cancellation of a running task.
     */
    fun cancelTask(params: CancelTaskRequest): CancelTaskResponse

    /**
     * Sets or updates the push notification configuration for a task.
     */
    fun setTaskPushNotification(params: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse

    /**
     * Retrieves the current push notification configuration for a task.
     */
    fun getTaskPushNotification(params: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse

}