package moe.nemesiss.a2a.domain

import com.fasterxml.jackson.annotation.*
import java.util.*

// Enums
enum class TaskState(@JsonValue val value: String) {
    SUBMITTED("submitted"),
    WORKING("working"),
    INPUT_REQUIRED("input-required"),
    COMPLETED("completed"),
    CANCELED("canceled"),
    FAILED("failed"),
    UNKNOWN("unknown")
}

enum class Role {
    user, agent
}

// Parts
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = TextPart::class, name = "text"),
    JsonSubTypes.Type(value = FilePart::class, name = "file"),
    JsonSubTypes.Type(value = DataPart::class, name = "data")
)
sealed class Part {
    abstract val metadata: Map<String, Any>?
}

data class TextPart(
    val type: String = "text",
    val text: String,
    @get:JsonProperty("metadata") override val metadata: Map<String, Any>? = null
) : Part()

data class FileContent(
    val name: String? = null,
    val mimeType: String? = null,
    val bytes: String? = null,
    val uri: String? = null
) {
    init {
        if (bytes == null && uri == null) {
            throw IllegalArgumentException("Either 'bytes' or 'uri' must be present")
        }
        if (bytes != null && uri != null) {
            throw IllegalArgumentException("Only one of 'bytes' or 'uri' can be present")
        }
    }
}

data class FilePart(
    val type: String = "file",
    val file: FileContent,
    @get:JsonProperty("metadata") override val metadata: Map<String, Any>? = null
) : Part()

data class DataPart(
    val type: String = "data",
    val data: Map<String, Any>,
    @get:JsonProperty("metadata") override val metadata: Map<String, Any>? = null
) : Part()

// Core Models
data class Message(
    val role: Role,
    val parts: List<Part>,
    val metadata: Map<String, Any>? = null
)

data class TaskStatus(
    val state: TaskState,
    val message: Message? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class Artifact(
    val name: String? = null,
    val description: String? = null,
    val parts: List<Part>,
    val metadata: Map<String, Any>? = null,
    val index: Int = 0,
    val append: Boolean? = null,
    val lastChunk: Boolean? = null
)

data class Task(
    val id: String,
    val sessionId: String? = null,
    val status: TaskStatus,
    val artifacts: List<Artifact>? = null,
    val history: List<Message>? = null,
    val metadata: Map<String, Any>? = null
)


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = TaskStatusUpdateEvent::class, name = "status"),
    JsonSubTypes.Type(value = TaskArtifactUpdateEvent::class, name = "artifact")
)
sealed interface StreamingResult

// Events
data class TaskStatusUpdateEvent(
    val id: String,
    val status: TaskStatus,
    val final: Boolean = false,
    val metadata: Map<String, Any>? = null
) : StreamingResult

data class TaskArtifactUpdateEvent(
    val id: String,
    val artifact: Artifact,
    val metadata: Map<String, Any>? = null
) : StreamingResult

// Authentication/Config
@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthenticationInfo(
    val schemes: List<String>,
    val credentials: String? = null
)

data class PushNotificationConfig(
    val url: String,
    val token: String? = null,
    val authentication: AuthenticationInfo? = null
)

// Params
data class TaskIdParams(
    val id: String,
    val metadata: Map<String, Any>? = null
)

data class TaskQueryParams(
    val id: String,
    val metadata: Map<String, Any>? = null,
    val historyLength: Int? = null
)

data class TaskSendParams(
    val id: String,
    val sessionId: String = UUID.randomUUID().toString(),
    val message: Message,
    val acceptedOutputModes: List<String>? = null,
    val pushNotification: PushNotificationConfig? = null,
    val historyLength: Int? = null,
    val metadata: Map<String, Any>? = null
)

data class TaskPushNotificationConfig(
    val id: String,
    val pushNotificationConfig: PushNotificationConfig
)

// RPC Messages
sealed class JSONRPCMessage(
    open val jsonrpc: String = "2.0",
    open val id: String? = null
)


object A2AMethods {
    const val TASKS_SEND = "tasks/send"
    const val TASKS_SEND_SUBSCRIBE = "tasks/sendSubscribe"
    const val TASKS_GET = "tasks/get"
    const val TASKS_CANCEL = "tasks/cancel"
    const val TASKS_PUSH_NOTIFICATION_GET = "tasks/pushNotification/get"
    const val TASKS_PUSH_NOTIFICATION_SET = "tasks/pushNotification/set"
    const val TASKS_PUSH_NOTIFICATION_SEND = "tasks/pushNotification/send"
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "method"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = SendTaskRequest::class, name = A2AMethods.TASKS_SEND),
    JsonSubTypes.Type(value = GetTaskRequest::class, name = A2AMethods.TASKS_GET),
    JsonSubTypes.Type(value = CancelTaskRequest::class, name = A2AMethods.TASKS_CANCEL),
    JsonSubTypes.Type(value = GetTaskPushNotificationRequest::class, name = A2AMethods.TASKS_PUSH_NOTIFICATION_GET),
    JsonSubTypes.Type(value = SetTaskPushNotificationRequest::class, name = A2AMethods.TASKS_PUSH_NOTIFICATION_SET),
    JsonSubTypes.Type(value = SendTaskPushNotificationRequest::class, name = A2AMethods.TASKS_PUSH_NOTIFICATION_SEND),
    JsonSubTypes.Type(value = SendTaskStreamingRequest::class, name = A2AMethods.TASKS_SEND_SUBSCRIBE)
)
sealed class JSONRPCRequest : JSONRPCMessage() {
    abstract val method: String
    abstract val params: Any?
}

data class SendTaskRequest(
    override val method: String = A2AMethods.TASKS_SEND,
    override val params: TaskSendParams,
    override val id: String? = null
) : JSONRPCRequest()

data class GetTaskRequest(
    override val method: String = A2AMethods.TASKS_GET,
    override val params: TaskQueryParams,
    override val id: String? = null
) : JSONRPCRequest()

data class CancelTaskRequest(
    override val method: String = A2AMethods.TASKS_CANCEL,
    override val params: TaskIdParams,
    override val id: String? = null
) : JSONRPCRequest()


data class SendTaskPushNotificationRequest(
    override val method: String = A2AMethods.TASKS_PUSH_NOTIFICATION_SEND,
    override val params: Task,
    override val id: String? = null
) : JSONRPCRequest()

// ... Other RPC Request classes following the same pattern

// ... [Previous classes remain the same] ...

// RPC Requests (complete implementations)
data class SetTaskPushNotificationRequest(
    override val method: String = A2AMethods.TASKS_PUSH_NOTIFICATION_SET,
    override val params: TaskPushNotificationConfig,
    override val id: String? = null
) : JSONRPCRequest()

data class GetTaskPushNotificationRequest(
    override val method: String = A2AMethods.TASKS_PUSH_NOTIFICATION_GET,
    override val params: TaskIdParams,
    override val id: String? = null
) : JSONRPCRequest()

//data class TaskResubscriptionRequest(
//    override val method: String = "tasks/resubscribe",
//    override val params: TaskIdParams,
//
//
//    ) : JSONRPCRequest()

data class SendTaskStreamingRequest(
    override val method: String = A2AMethods.TASKS_SEND_SUBSCRIBE,
    override val params: TaskSendParams,
    override val id: String? = null
) : JSONRPCRequest()

// RPC Responses (complete implementations)
data class GetTaskResponse(
    override val result: Task? = null,
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

data class CancelTaskResponse(
    override val result: Task? = null,
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

data class SetTaskPushNotificationResponse(
    override val result: TaskPushNotificationConfig? = null,
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

data class GetTaskPushNotificationResponse(
    override val result: TaskPushNotificationConfig? = null,
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

data class SendTaskStreamingResponse(
    override val result: StreamingResult? = null,  // TaskStatusUpdateEvent or TaskArtifactUpdateEvent
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

data class SendTaskPushNotificationResponse(
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

// Error Classes (complete implementations)
data class TaskNotFoundError(
    override val code: Int = -32001,
    override val message: String = "Task not found",
    override val data: Any? = null
) : JSONRPCError(code, message, data)

data class TaskNotCancelableError(
    override val code: Int = -32002,
    override val message: String = "Task cannot be canceled",
    override val data: Any? = null
) : JSONRPCError(code, message, data)

data class PushNotificationNotSupportedError(
    override val code: Int = -32003,
    override val message: String = "Push Notification is not supported",
    override val data: Any? = null
) : JSONRPCError(code, message, data)

data class UnsupportedOperationError(
    override val code: Int = -32004,
    override val message: String = "This operation is not supported",
    override val data: Any? = null
) : JSONRPCError(code, message, data)

data class ContentTypeNotSupportedError(
    override val code: Int = -32005,
    override val message: String = "Incompatible content types",
    override val data: Any? = null
) : JSONRPCError(code, message, data)

// Authentication and Agent Classes
data class AgentAuthentication(
    val schemes: List<String>,
    val credentials: String? = null
)

// Exceptions (complete implementations)
class A2AClientJSONError(message: String) : A2AClientError("JSON Error: $message")


// RPC Responses
open class JSONRPCResponse(
    open val result: Any? = null,
    open val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCMessage()

data class SendTaskResponse(
    override val result: Task? = null,
    override val error: JSONRPCError? = null,
    override val id: String? = null
) : JSONRPCResponse()

// ... Other RPC Response classes

// Errors
open class JSONRPCError(
    open val code: Int,
    open val message: String,
    open val data: Any? = null
)

data class JSONParseError(
    override val code: Int = -32700,
    override val message: String = "Invalid JSON payload",
    override val data: Any? = null
) : JSONRPCError(code, message, data)

// ... Other error classes

// Agent Models
data class AgentProvider(
    val organization: String,
    val url: String? = null
)

data class AgentCapabilities(
    val streaming: Boolean = false,
    val pushNotifications: Boolean = false,
    val stateTransitionHistory: Boolean = false
)

data class AgentSkill(
    val id: String,
    val name: String,
    val description: String? = null,
    val tags: List<String>? = null,
    val examples: List<String>? = null,
    val inputModes: List<String>? = null,
    val outputModes: List<String>? = null
)

data class AgentCard(
    val name: String,
    val description: String? = null,
    val url: String,
    val provider: AgentProvider? = null,
    val version: String,
    val documentationUrl: String? = null,
    val capabilities: AgentCapabilities,
    val authentication: AuthenticationInfo? = null,
    val defaultInputModes: List<String> = listOf("text"),
    val defaultOutputModes: List<String> = listOf("text"),
    val skills: List<AgentSkill>
)

// Exceptions
open class A2AClientError(message: String) : Exception(message)
class A2AClientHTTPError(statusCode: Int, message: String) :
        A2AClientError("HTTP Error $statusCode: $message")

class MissingAPIKeyError : A2AClientError("Missing API key")
