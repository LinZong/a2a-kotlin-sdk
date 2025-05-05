package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.domain.LocalTransportEndpoint
import moe.nemesiss.a2a.domain.SendTaskPushNotificationRequest
import moe.nemesiss.a2a.domain.SendTaskPushNotificationResponse
import moe.nemesiss.a2a.transport.LocalA2AServerTransport

class DefaultLocalA2AServer(transport: LocalA2AServerTransport, taskManager: TaskManager) :
        AbstractA2AServer(transport, taskManager) {

    override fun sendTaskPushNotification(url: String,
                                          request: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse {
        return (transport as LocalA2AServerTransport).sendMessage(LocalTransportEndpoint.parse(url),
                                                                  request,
                                                                  SendTaskPushNotificationResponse::class.java)
    }
}