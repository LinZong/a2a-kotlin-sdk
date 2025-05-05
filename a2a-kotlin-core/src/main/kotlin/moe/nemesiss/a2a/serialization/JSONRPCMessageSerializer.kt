package moe.nemesiss.a2a.serialization

import moe.nemesiss.a2a.domain.JSONRPCMessage

object JSONRPCMessageSerializer {

    fun serialize(message: JSONRPCMessage): String {
        return a2aObjectMapper.writeValueAsString(message)
    }

    fun <T : JSONRPCMessage> deserialize(message: String, messageType: Class<T>): T {
        return a2aObjectMapper.readValue(message, messageType)
    }
}