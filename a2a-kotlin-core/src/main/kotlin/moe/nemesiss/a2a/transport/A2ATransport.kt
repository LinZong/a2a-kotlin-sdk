package moe.nemesiss.a2a.transport


interface A2ATransport {

    /**
     * Start the transport. After started, this transport can be used for sending/handling message.
     */
    fun start()

    /**
     * Stop the transport.
     */
    fun stop()
}
