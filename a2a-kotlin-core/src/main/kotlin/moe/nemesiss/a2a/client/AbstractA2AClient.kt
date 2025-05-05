package moe.nemesiss.a2a.client

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.transport.A2AClientTransport

abstract class AbstractA2AClient(protected val transport: A2AClientTransport) : A2AClient {


    override fun sendTask(params: SendTaskRequest): SendTaskResponse {
        return transport.sendMessage(params, SendTaskResponse::class.java)
    }

    override fun sendTaskStreaming(params: SendTaskStreamingRequest,
                                   callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        transport.sendStreamingRequest(params, callback)
    }

    override fun getTask(params: GetTaskRequest): GetTaskResponse {
        return transport.sendMessage(params, GetTaskResponse::class.java)
    }

    override fun cancelTask(params: CancelTaskRequest): CancelTaskResponse {
        return transport.sendMessage(params, CancelTaskResponse::class.java)
    }

    override fun setTaskPushNotification(params: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse {
        return transport.sendMessage(params,
                                     SetTaskPushNotificationResponse::class.java)
    }

    override fun getTaskPushNotification(params: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse {
        return transport.sendMessage(params,
                                     GetTaskPushNotificationResponse::class.java)

    }
}