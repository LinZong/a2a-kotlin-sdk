package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.LocalTransportEndpoint
import moe.nemesiss.a2a.host.LocalA2AHost

class DefaultLocalA2AServerTransport(host: LocalA2AHost, listenEndpoint: LocalTransportEndpoint) :
        AbstractLocalA2AServerTransport(host, listenEndpoint)