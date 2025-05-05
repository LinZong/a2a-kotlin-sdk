package moe.nemesiss.a2a

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.server.A2AServer
import moe.nemesiss.a2a.server.TaskManager

class DateTimeNowTaskManager : TaskManager {

    private lateinit var server: A2AServer
    override fun onServerAttached(server: A2AServer) {
        this.server = server
    }

    override fun onGetTask(request: GetTaskRequest): GetTaskResponse {
        TODO("Not yet implemented")
    }

    override fun onCancelTask(request: CancelTaskRequest): CancelTaskResponse {
        TODO("Not yet implemented")
    }

    override fun onSendTask(request: SendTaskRequest): SendTaskResponse {
        return SendTaskResponse(id = request.id,
                                result = Task(id = request.params.id,
                                              sessionId = request.params.sessionId,
                                              status = TaskStatus(TaskState.COMPLETED),
                                              history = listOf(request.params.message),
                                              artifacts = listOf(Artifact(index = 0,
                                                                          append = false,
                                                                          lastChunk = true,
                                                                          parts = listOf(TextPart(text = "Current time is: ${System.currentTimeMillis()}"))))))
    }

    override fun onSendTaskSubscribe(request: SendTaskStreamingRequest,
                                     callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        TODO("Not yet implemented")
    }

    override fun onSetTaskPushNotification(request: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse {
        TODO("Not yet implemented")
    }

    override fun onGetTaskPushNotification(request: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse {
        TODO("Not yet implemented")
    }
}