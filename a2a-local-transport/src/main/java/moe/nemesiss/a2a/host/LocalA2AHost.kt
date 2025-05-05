package moe.nemesiss.a2a.host

import moe.nemesiss.a2a.domain.LocalA2ATransport
import moe.nemesiss.a2a.domain.LocalTransportEndpoint

class LocalA2AHost {


    private val routes = mutableMapOf<String, LocalA2ATransport>()

    fun registerTransport(endpoint: LocalTransportEndpoint, transport: LocalA2ATransport) {
        routes[endpoint.getUriString()] = transport
    }

    fun resolveTransport(endpoint: LocalTransportEndpoint): LocalA2ATransport? {
        return routes[endpoint.getUriString()]
    }

    fun removeTransport(endpoint: LocalTransportEndpoint) {
        routes.remove(endpoint.getUriString())
    }

}