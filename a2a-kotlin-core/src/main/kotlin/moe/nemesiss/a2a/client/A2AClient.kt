package moe.nemesiss.a2a.client

import moe.nemesiss.a2a.domain.*

interface A2AClient {

    fun sendTask(params: TaskSendParams): SendTaskResponse

    fun sendTaskStreaming(params: TaskSendParams, callback: StreamResponseCallback<SendTaskStreamingResponse>)

    fun getTask(params: TaskQueryParams): GetTaskResponse

    fun cancelTask(params: TaskIdParams): CancelTaskResponse

    fun setTaskPushNotification(params: TaskPushNotificationConfig): SetTaskPushNotificationResponse

    fun getTaskPushNotification(params: TaskIdParams): GetTaskPushNotificationResponse
}