package moe.nemesiss.a2a.host

import moe.nemesiss.a2a.domain.LocalA2ATransport
import moe.nemesiss.a2a.domain.LocalTransportEndpoint

class LocalA2AHost {


    private val routes = mutableMapOf<String, LocalA2ATransport>()

    /**
     * Register a [LocalA2ATransport] with corresponding [LocalTransportEndpoint].
     */
    fun registerTransport(endpoint: LocalTransportEndpoint, transport: LocalA2ATransport) {
        routes[endpoint.getUriString()] = transport
    }

    /**
     * Resolve [LocalA2ATransport] from specific [LocalTransportEndpoint].
     */
    fun resolveTransport(endpoint: LocalTransportEndpoint): LocalA2ATransport? {
        return routes[endpoint.getUriString()]
    }

    /**
     * Remove [LocalA2ATransport] from host corresponding to the presented [LocalTransportEndpoint].
     */
    fun removeTransport(endpoint: LocalTransportEndpoint): LocalA2ATransport? {
        return routes.remove(endpoint.getUriString())
    }

}