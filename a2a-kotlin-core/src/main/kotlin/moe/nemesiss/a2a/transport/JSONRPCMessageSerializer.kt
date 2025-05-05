package moe.nemesiss.a2a.transport

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import moe.nemesiss.a2a.domain.JSONRPCMessage

object JSONRPCMessageSerializer {

    private val objectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
    }


    fun serialize(message: JSONRPCMessage): String {
        return objectMapper.writeValueAsString(message)
    }

    fun <T : JSONRPCMessage> deserialize(message: String, messageType: Class<T>): T {
        return objectMapper.readValue(message, messageType)
    }


}