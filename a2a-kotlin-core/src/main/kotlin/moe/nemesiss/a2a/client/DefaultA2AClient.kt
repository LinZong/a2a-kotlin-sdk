package moe.nemesiss.a2a.client

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.transport.A2AClientTransport

open class DefaultA2AClient(protected val transport: A2AClientTransport) : A2AClient {


    override fun sendTask(params: TaskSendParams): SendTaskResponse {
        return transport.sendMessage(SendTaskRequest(params = params), SendTaskResponse::class.java)
    }

    override fun sendTaskStreaming(params: TaskSendParams,
                                   callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        transport.sendStreamingRequest(SendTaskStreamingRequest(params = params), callback)
    }

    override fun getTask(params: TaskQueryParams): GetTaskResponse {
        return transport.sendMessage(GetTaskRequest(params = params), GetTaskResponse::class.java)
    }

    override fun cancelTask(params: TaskIdParams): CancelTaskResponse {
        return transport.sendMessage(CancelTaskRequest(params = params), CancelTaskResponse::class.java)
    }

    override fun setTaskPushNotification(params: TaskPushNotificationConfig): SetTaskPushNotificationResponse {
        return transport.sendMessage(SetTaskPushNotificationRequest(params = params),
                                     SetTaskPushNotificationResponse::class.java)
    }

    override fun getTaskPushNotification(params: TaskIdParams): GetTaskPushNotificationResponse {
        return transport.sendMessage(GetTaskPushNotificationRequest(params = params),
                                     GetTaskPushNotificationResponse::class.java)

    }
}