package moe.nemesiss.a2a.domain

data class HandlerMapping<T : JSONRPCMessage, R : JSONRPCResponse>(val method: String,
                                                                   val requestType: Class<T>,
                                                                   val responseType: Class<R>,
                                                                   val handler: MessageHandler<T, R>)

data class StreamHandlerMapping<T : JSONRPCMessage, R : JSONRPCResponse>(val method: String,
                                                                         val requestType: Class<T>,
                                                                         val responseType: Class<R>,
                                                                         val handler: StreamMessageHandler<T, R>)
