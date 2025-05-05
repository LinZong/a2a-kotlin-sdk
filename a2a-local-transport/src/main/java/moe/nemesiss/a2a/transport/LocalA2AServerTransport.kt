package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.LocalTransportEndpoint
import moe.nemesiss.a2a.host.LocalA2AHost

class LocalA2AServerTransport(host: LocalA2AHost,
                              val listenEndpoint: LocalTransportEndpoint) : AbstractLocalA2ATransport(host),
                                                                            A2AServerTransport {
    override fun start() {
        host.registerTransport(listenEndpoint, this)
    }

    override fun stop() {
        host.removeTransport(listenEndpoint)
    }

}