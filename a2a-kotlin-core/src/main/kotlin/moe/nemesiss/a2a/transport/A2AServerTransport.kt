package moe.nemesiss.a2a.transport

import moe.nemesiss.a2a.domain.TransportEndpoint

interface A2AServerTransport<E : TransportEndpoint> : A2ATransport<E>