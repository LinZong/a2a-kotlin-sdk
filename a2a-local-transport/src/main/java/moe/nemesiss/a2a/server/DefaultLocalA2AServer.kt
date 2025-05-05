package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.transport.LocalA2AServerTransport

class DefaultLocalA2AServer(transport: LocalA2AServerTransport, taskManager: TaskManager) :
        DefaultA2AServer(transport, taskManager)