package moe.nemesiss.a2a.host

import moe.nemesiss.a2a.transport.A2ATransport
import moe.nemesiss.a2a.domain.TransportEndpoint

class LocalA2AHost {


    private val routes = mutableMapOf<String, A2ATransport>()

    fun registerTransport(endpoint: TransportEndpoint, transport: A2ATransport) {
        routes[endpoint.getUri()] = transport
    }

    fun resolveTransport(endpoint: TransportEndpoint): A2ATransport? {
        return routes[endpoint.getUri()]
    }

    fun removeTransport(endpoint: TransportEndpoint) {
        routes.remove(endpoint.getUri())
    }

}