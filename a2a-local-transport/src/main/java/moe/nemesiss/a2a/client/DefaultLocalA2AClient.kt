package moe.nemesiss.a2a.client

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.transport.LocalA2AClientTransport

class DefaultLocalA2AClient(transport: LocalA2AClientTransport) : DefaultA2AClient(transport) {


    private var taskNotificationCallback: A2ATaskNotificationCallback? = null

    init {
        transport.registerMessageHandler(HandlerMapping(
            A2AMethods.TASKS_PUSH_NOTIFICATION_SEND,
            SendTaskPushNotificationRequest::class.java,
            SendTaskPushNotificationResponse::class.java,
            TaskNotificationMessageHandler()))
    }

    override fun setTaskPushNotification(params: TaskPushNotificationConfig): SetTaskPushNotificationResponse {
        val finalParams = correctNotificationUrlToClientNotifyEndpoint(params)
        return transport.sendMessage(SetTaskPushNotificationRequest(params = finalParams),
                                     SetTaskPushNotificationResponse::class.java)
    }

    override fun getTaskPushNotification(params: TaskIdParams): GetTaskPushNotificationResponse {
        return transport.sendMessage(GetTaskPushNotificationRequest(params = params),
                                     GetTaskPushNotificationResponse::class.java)

    }


    fun setTaskNotificationCallback(callback: A2ATaskNotificationCallback) {
        this.taskNotificationCallback
    }

    fun removeTaskNotificationCallback() {
        this.taskNotificationCallback = null
    }

    private fun correctNotificationUrlToClientNotifyEndpoint(params: TaskPushNotificationConfig): TaskPushNotificationConfig {
        val clientTransport = transport as LocalA2AClientTransport
        val clientNotifyEndpoint = clientTransport.clientNotifyEndpoint
        require(clientNotifyEndpoint != null) { "clientNotifyEndpoint in LocalA2AClientTransport should not be null." }
        val finalParams =
            params.copy(pushNotificationConfig = params.pushNotificationConfig.copy(url = clientNotifyEndpoint.getUriString()))
        return finalParams
    }


    inner class TaskNotificationMessageHandler :
            MessageHandler<SendTaskPushNotificationRequest, SendTaskPushNotificationResponse> {
        override fun handle(request: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse {
            taskNotificationCallback?.onNotify(request.params)
            return SendTaskPushNotificationResponse(id = request.id)
        }
    }
}