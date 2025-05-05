package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.domain.LocalA2AServerTransport
import moe.nemesiss.a2a.domain.LocalTransportEndpoint
import moe.nemesiss.a2a.domain.SendTaskPushNotificationRequest
import moe.nemesiss.a2a.domain.SendTaskPushNotificationResponse

class DefaultLocalA2AServer(transport: LocalA2AServerTransport, taskManager: TaskManager) :
        AbstractA2AServer(transport, taskManager) {

    override fun sendTaskPushNotification(url: String,
                                          request: SendTaskPushNotificationRequest): SendTaskPushNotificationResponse {
        return (transport as LocalA2AServerTransport).sendNotification(LocalTransportEndpoint.parse(url), request)
    }
}