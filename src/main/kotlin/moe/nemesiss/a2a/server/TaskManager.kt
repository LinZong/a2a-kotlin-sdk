package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.domain.*


interface TaskManager {

    fun onServerAttached(server: A2AServer)

    fun onGetTask(request: GetTaskRequest): GetTaskResponse

    fun onCancelTask(request: CancelTaskRequest): CancelTaskResponse

    fun onSendTask(request: SendTaskRequest): SendTaskResponse

    fun onSendTaskSubscribe(request: SendTaskStreamingRequest,
                            callback: StreamResponseCallback<SendTaskStreamingResponse>)

    fun onSetTaskPushNotification(request: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse

    fun onGetTaskPushNotification(request: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse
}

