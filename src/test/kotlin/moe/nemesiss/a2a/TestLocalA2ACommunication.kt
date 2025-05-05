package moe.nemesiss.a2a

import moe.nemesiss.a2a.client.DefaultLocalA2AClient
import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.host.LocalA2AHost
import moe.nemesiss.a2a.server.DefaultLocalA2AServer
import moe.nemesiss.a2a.transport.LocalA2AClientTransport
import moe.nemesiss.a2a.transport.LocalA2AServerTransport
import org.junit.jupiter.api.Test

class TestLocalA2ACommunication {


    @Test
    fun testSimpleSendTask() {
        val host = LocalA2AHost()
        val taskManager = DateTimeNowTaskManager()
        val serverEndpoint = LocalTransportEndpoint("dateTimeServer")
        val serverTransport = LocalA2AServerTransport(host, serverEndpoint)
        val server = DefaultLocalA2AServer(serverTransport, taskManager)
        server.start()


        val clientTransport = LocalA2AClientTransport(host, serverEndpoint)
        val client = DefaultLocalA2AClient(clientTransport)
        clientTransport.start()

        val response = client.sendTask(TaskSendParams(id = "1",
                                                      message = Message(Role.user,
                                                                        parts = listOf(TextPart(text = "What time is it now?")))))

        println(response)


        clientTransport.stop()
        server.stop()
    }
}