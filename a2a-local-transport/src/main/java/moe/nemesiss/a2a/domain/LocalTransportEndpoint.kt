package moe.nemesiss.a2a.domain

import java.net.URI

class LocalTransportEndpoint(host: String) :
        URITransportEndpoint(scheme = "local", host = host, port = -1, path = "/") {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LocalTransportEndpoint) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "LocalTransportEndpoint() ${super.toString()}"
    }


    companion object {
        fun parse(url: String): LocalTransportEndpoint {
            val uri = URI.create(url)
            require(uri.scheme == "local") { "Invalid scheme, should be: 'local', current: '${uri.scheme}'" }
            return LocalTransportEndpoint(uri.host)
        }
    }
}
