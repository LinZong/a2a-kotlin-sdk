package moe.nemesiss.a2a.host

import moe.nemesiss.a2a.domain.LocalA2AServerTransport
import moe.nemesiss.a2a.domain.LocalTransportEndpoint

class LocalA2AHost {


    private val routes = mutableMapOf<String, LocalA2AServerTransport>()

    /**
     * Register a [LocalA2AServerTransport] with corresponding [LocalTransportEndpoint].
     */
    fun registerTransport(endpoint: LocalTransportEndpoint, transport: LocalA2AServerTransport) {
        routes[endpoint.getUriString()] = transport
    }

    /**
     * Resolve [LocalA2AServerTransport] from specific [LocalTransportEndpoint].
     */
    fun resolveTransport(endpoint: LocalTransportEndpoint): LocalA2AServerTransport? {
        return routes[endpoint.getUriString()]
    }

    /**
     * Remove [LocalA2AServerTransport] from host corresponding to the presented [LocalTransportEndpoint].
     */
    fun removeTransport(endpoint: LocalTransportEndpoint): LocalA2AServerTransport? {
        return routes.remove(endpoint.getUriString())
    }

}