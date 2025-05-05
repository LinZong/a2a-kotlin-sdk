package moe.nemesiss.a2a.domain

import java.net.URI


sealed class TransportEndpoint

open class URITransportEndpoint : TransportEndpoint {

    val uri: URI

    constructor(scheme: String, host: String, port: Int, path: String) : super() {
        this.uri = URI(scheme, null, host, port, path, null, null)
    }

    constructor(uri: URI) {
        this.uri = uri
    }

    fun getUriString(): String {
        return this.uri.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is URITransportEndpoint) return false

        if (uri != other.uri) return false

        return true
    }

    override fun hashCode(): Int {
        return uri.hashCode()
    }

    override fun toString(): String {
        return "URITransportEndpoint(uri=$uri)"
    }
}
