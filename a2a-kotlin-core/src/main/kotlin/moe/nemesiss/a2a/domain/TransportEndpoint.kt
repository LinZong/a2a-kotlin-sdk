package moe.nemesiss.a2a.domain

import java.net.URI

open class TransportEndpoint(
    val scheme: String,
    val host: String,
    val port: Int,
    val path: String
) {
    fun getUri(): String {
        val uri = URI(scheme, null, host, port, path, null, null)
        return uri.toString()
    }

    override fun toString(): String {
        return "TransportInfo(scheme='$scheme', host='$host', port=$port, endpoint='$path')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TransportEndpoint) return false

        if (scheme != other.scheme) return false
        if (host != other.host) return false
        if (port != other.port) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scheme.hashCode()
        result = 31 * result + host.hashCode()
        result = 31 * result + port
        result = 31 * result + path.hashCode()
        return result
    }

    companion object {
        fun fromUri(uri: String): TransportEndpoint {
            val obj = URI.create(uri)
            return TransportEndpoint(obj.scheme, obj.host, obj.port, obj.path)
        }
    }
}


class LocalTransportEndpoint(host: String) :
        TransportEndpoint(scheme = "local", host = host, port = -1, path = "/")
