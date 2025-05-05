package moe.nemesiss.a2a.server

import moe.nemesiss.a2a.domain.*
import moe.nemesiss.a2a.transport.A2AServerTransport
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractA2AServer(protected val transport: A2AServerTransport<*>,
                                 protected val taskManager: TaskManager) : A2AServer {


    private val started = AtomicBoolean(false)

    override fun start() {
        taskManager.onServerAttached(this)
        transport.registerMessageHandler(HandlerMapping(A2AMethods.TASKS_GET,
                                                        GetTaskRequest::class.java,
                                                        GetTaskResponse::class.java,
                                                        this::onGetTask))

        transport.registerMessageHandler(HandlerMapping(A2AMethods.TASKS_CANCEL,
                                                        CancelTaskRequest::class.java,
                                                        CancelTaskResponse::class.java,
                                                        this::onCancelTask))

        transport.registerMessageHandler(HandlerMapping(A2AMethods.TASKS_SEND,
                                                        SendTaskRequest::class.java,
                                                        SendTaskResponse::class.java,
                                                        this::onSendTask))

        transport.registerStreamingMessageHandler(StreamHandlerMapping(A2AMethods.TASKS_SEND_SUBSCRIBE,
                                                                       SendTaskStreamingRequest::class.java,
                                                                       SendTaskStreamingResponse::class.java,
                                                                       this::onSendTaskSubscribe))


        transport.registerMessageHandler(HandlerMapping(A2AMethods.TASKS_PUSH_NOTIFICATION_GET,
                                                        GetTaskPushNotificationRequest::class.java,
                                                        GetTaskPushNotificationResponse::class.java,
                                                        this::onGetTaskPushNotification))


        transport.registerMessageHandler(HandlerMapping(A2AMethods.TASKS_PUSH_NOTIFICATION_SET,
                                                        SetTaskPushNotificationRequest::class.java,
                                                        SetTaskPushNotificationResponse::class.java,
                                                        this::onSetTaskPushNotification))


        transport.start()
        started.set(true)
    }

    override fun stop() {
        transport.stop()
        started.set(false)
    }

    override fun onGetTask(request: GetTaskRequest): GetTaskResponse {
        ensureInitialized()
        return taskManager.onGetTask(request)
    }

    override fun onCancelTask(request: CancelTaskRequest): CancelTaskResponse {
        ensureInitialized()
        return taskManager.onCancelTask(request)
    }

    override fun onSendTask(request: SendTaskRequest): SendTaskResponse {
        ensureInitialized()
        return taskManager.onSendTask(request)
    }

    override fun onSendTaskSubscribe(request: SendTaskStreamingRequest,
                                     callback: StreamResponseCallback<SendTaskStreamingResponse>) {
        ensureInitialized()
        return taskManager.onSendTaskSubscribe(request, callback)
    }

    override fun onSetTaskPushNotification(request: SetTaskPushNotificationRequest): SetTaskPushNotificationResponse {
        ensureInitialized()
        return taskManager.onSetTaskPushNotification(request)
    }

    override fun onGetTaskPushNotification(request: GetTaskPushNotificationRequest): GetTaskPushNotificationResponse {
        ensureInitialized()
        return taskManager.onGetTaskPushNotification(request)
    }

    protected fun ensureInitialized() {
        require(started.get()) { "Server hasn't been started. Please call 'start' method before using any server method." }
    }
}