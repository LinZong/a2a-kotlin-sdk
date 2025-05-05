package moe.nemesiss.a2a.client

import moe.nemesiss.a2a.domain.*

interface A2AClient {

    fun sendTask(params: SendTaskRequest): SendTaskResponse

    fun sendTaskStreaming(params: SendTaskStreamingRequest, callback: StreamResponseCallback<SendTaskStreamingResponse>)

    fun getTask(params: GetTaskRequest): GetTaskResponse

    fun cancelTask(params: CancelTaskRequest): CancelTaskResponse

    fun setTaskPushNotification(params: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse

    fun getTaskPushNotification(params: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse

}