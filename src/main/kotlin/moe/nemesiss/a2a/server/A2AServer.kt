package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.domain.*

interface A2AServer {

    fun start()

    fun stop()

    fun onGetTask(request: GetTaskRequest): GetTaskResponse

    fun onCancelTask(request: CancelTaskRequest): CancelTaskResponse

    fun onSendTask(request: SendTaskRequest): SendTaskResponse

    fun onSendTaskSubscribe(request: SendTaskStreamingRequest,
                            callback: StreamResponseCallback<SendTaskStreamingResponse>)

    fun onSetTaskPushNotification(request: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse

    fun onGetTaskPushNotification(request: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse

    fun sendTaskPushNotification(endpoint: TransportEndpoint,
                                 request: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse
}