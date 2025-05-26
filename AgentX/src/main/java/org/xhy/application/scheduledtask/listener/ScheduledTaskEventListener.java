package org.xhy.application.scheduledtask.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.xhy.application.conversation.dto.ChatRequest;
import org.xhy.application.conversation.service.ConversationAppService;
import org.xhy.domain.scheduledtask.event.ScheduledTaskExecuteEvent;
import org.xhy.domain.scheduledtask.service.ScheduledTaskDomainService;

/** 定时任务事件监听器 监听Domain层发布的任务执行事件，调用ConversationAppService执行对话 */
@Component
public class ScheduledTaskEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskEventListener.class);

    private final ConversationAppService conversationAppService;
    private final ScheduledTaskDomainService scheduledTaskDomainService;

    public ScheduledTaskEventListener(ConversationAppService conversationAppService,
            ScheduledTaskDomainService scheduledTaskDomainService) {
        this.conversationAppService = conversationAppService;
        this.scheduledTaskDomainService = scheduledTaskDomainService;
    }

    /** 处理定时任务执行事件
     * @param event 任务执行事件 */
    @EventListener
    @Async
    public void handleTaskExecuteEvent(ScheduledTaskExecuteEvent event) {
        try {
            logger.info("接收到定时任务执行事件: taskId={}, userId={}, sessionId={}", event.getTaskId(), event.getUserId(),
                    event.getSessionId());

            // 创建聊天请求
            ChatRequest chatRequest = new ChatRequest();
            chatRequest.setMessage(event.getContent());
            chatRequest.setSessionId(event.getSessionId());

            // 调用对话服务
            conversationAppService.chat(chatRequest, event.getUserId());

            logger.info("定时任务消息发送成功: taskId={}", event.getTaskId());

            // 直接调用Domain服务记录执行成功（如果需要的话）
            // scheduledTaskDomainService.recordExecutionSuccess(event.getTaskId());

        } catch (Exception e) {
            logger.error("处理定时任务执行事件失败: taskId={}, error={}", event.getTaskId(), e.getMessage(), e);

            // 直接调用Domain服务记录执行失败（如果需要的话）
            // scheduledTaskDomainService.recordExecutionFailure(event.getTaskId(), e.getMessage());
        }
    }
}