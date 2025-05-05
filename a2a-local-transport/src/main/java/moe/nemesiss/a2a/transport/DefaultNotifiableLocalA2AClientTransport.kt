package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost

class DefaultNotifiableLocalA2AClientTransport(host: LocalA2AHost,
                                               serverEndpoint: LocalTransportEndpoint,
                                               clientNotifyEndpoint: LocalTransportEndpoint) :
        DefaultLocalA2AClientTransport(host, serverEndpoint) {

    private val serverTransport = DefaultLocalA2AServerTransport(host, clientNotifyEndpoint)

    private var callback: A2ATaskNotificationCallback? = null

    override fun start() {
        super.start()
        serverTransport.registerMessageHandler(HandlerMapping(A2AMethods.TASKS_PUSH_NOTIFICATION_SEND,
                                                              SendTaskPushNotificationRequest::class.java,
                                                              SendTaskPushNotificationResponse::class.java,
                                                              TaskNotificationHandler()))
        serverTransport.start()
    }

    override fun stop() {
        serverTransport.stop()
        super.stop()
    }

    fun setTaskNotificationCallback(callback: A2ATaskNotificationCallback) {
        this.callback = callback
    }

    fun removeTaskNotificationCallback(callback: A2ATaskNotificationCallback) {
        this.callback = null
    }


    inner class TaskNotificationHandler :
            MessageHandler<SendTaskPushNotificationRequest, SendTaskPushNotificationResponse> {
        override fun handle(request: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse {
            runCatching {
                callback?.onNotify(request.params)
            }
            return SendTaskPushNotificationResponse(id = request.id)
        }
    }
}