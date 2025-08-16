package org.xhy.application.agent.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhy.application.agent.assembler.AgentWidgetAssembler;
import org.xhy.application.agent.dto.AgentWidgetDTO;
import org.xhy.application.llm.assembler.ModelAssembler;
import org.xhy.application.llm.assembler.ProviderAssembler;
import org.xhy.application.llm.dto.ModelDTO;
import org.xhy.application.llm.dto.ProviderDTO;
import org.xhy.domain.agent.model.AgentEntity;
import org.xhy.domain.agent.model.AgentWidgetEntity;
import org.xhy.domain.agent.repository.AgentRepository;
import org.xhy.domain.agent.service.AgentWidgetDomainService;
import org.xhy.domain.llm.model.ModelEntity;
import org.xhy.domain.llm.model.ProviderEntity;
import org.xhy.domain.llm.service.LLMDomainService;
import org.xhy.infrastructure.exception.BusinessException;
import org.xhy.interfaces.dto.agent.request.CreateWidgetRequest;
import org.xhy.interfaces.dto.agent.request.UpdateWidgetRequest;

import java.util.ArrayList;
import java.util.List;

/** Agent小组件配置应用服务 */
@Service
public class AgentWidgetAppService {

    private final AgentWidgetDomainService agentWidgetDomainService;
    private final AgentRepository agentRepository;
    private final LLMDomainService llmDomainService;
    private final AgentWidgetAssembler agentWidgetAssembler;

    public AgentWidgetAppService(AgentWidgetDomainService agentWidgetDomainService,
                              AgentRepository agentRepository,
                              LLMDomainService llmDomainService,
                              AgentWidgetAssembler agentWidgetAssembler) {
        this.agentWidgetDomainService = agentWidgetDomainService;
        this.agentRepository = agentRepository;
        this.llmDomainService = llmDomainService;
        this.agentWidgetAssembler = agentWidgetAssembler;
    }

    /** 创建小组件配置
     *
     * @param agentId Agent ID
     * @param request 创建请求
     * @param userId 用户ID
     * @return 创建的小组件配置DTO */
    @Transactional
    public AgentWidgetDTO createWidget(String agentId, CreateWidgetRequest request, String userId) {
        // 1. 验证Agent权限
        validateAgentOwnership(agentId, userId);

        // 2. 检查是否可以创建更多小组件配置（可选限制）
        // if (!agentWidgetDomainService.canCreateMoreWidgets(userId, 10)) {
        //     throw new BusinessException("已达到最大小组件配置数量限制");
        // }

        // 3. 创建小组件配置实体
        AgentWidgetEntity widget = AgentWidgetAssembler.toEntity(request, agentId, userId);
        
        // 4. 保存到数据库
        AgentWidgetEntity savedWidget = agentWidgetDomainService.createWidget(widget);

        // 5. 获取关联的模型和服务商信息
        ModelEntity model = llmDomainService.getModelById(widget.getModelId());
        ProviderEntity provider = llmDomainService.getProvider(model.getProviderId());

        // 6. 转换为DTO并返回
        return agentWidgetAssembler.toDTOWithEmbedCode(savedWidget, ModelAssembler.toDTO(model), ProviderAssembler.toDTO(provider));
    }

    /** 获取Agent的所有小组件配置
     *
     * @param agentId Agent ID
     * @param userId 用户ID
     * @return 小组件配置列表 */
    public List<AgentWidgetDTO> getWidgetsByAgent(String agentId, String userId) {
        // 1. 验证Agent权限
        validateAgentOwnership(agentId, userId);

        // 2. 获取小组件配置列表
        List<AgentWidgetEntity> widgets = agentWidgetDomainService.getWidgetsByAgent(agentId, userId);
        
        if (widgets.isEmpty()) {
            return List.of();
        }

        // 3. 批量获取模型和服务商信息
        List<ModelDTO> models = new ArrayList<>();
        List<ProviderDTO> providers = new ArrayList<>();

        // 4. 转换为DTO列表
        return AgentWidgetAssembler.toDTOsWithEmbedCode(widgets, models, providers, 
                agentWidgetAssembler.frontendBaseUrl);
    }

    /** 获取用户的所有小组件配置
     *
     * @param userId 用户ID
     * @return 小组件配置列表 */
    public List<AgentWidgetDTO> getWidgetsByUser(String userId) {
        List<AgentWidgetEntity> widgets = agentWidgetDomainService.getWidgetsByUser(userId);
        
        if (widgets.isEmpty()) {
            return List.of();
        }

        List<ModelDTO> models = new ArrayList<>();
        List<ProviderDTO> providers = new ArrayList<>();

        return AgentWidgetAssembler.toDTOsWithEmbedCode(widgets, models, providers,
                agentWidgetAssembler.frontendBaseUrl);
    }

    /** 更新小组件配置
     *
     * @param widgetId 小组件配置ID
     * @param request 更新请求
     * @param userId 用户ID
     * @return 更新后的小组件配置DTO */
    @Transactional
    public AgentWidgetDTO updateWidget(String widgetId, UpdateWidgetRequest request, String userId) {
        AgentWidgetEntity widget = agentWidgetDomainService.getWidgetById(widgetId, userId);

        AgentWidgetAssembler.updateEntity(widget, request);

        AgentWidgetEntity updatedWidget = agentWidgetDomainService.updateWidget(widget, userId);

        ModelEntity model = llmDomainService.getModelById(widget.getModelId());
        ProviderEntity provider = llmDomainService.getProvider(model.getProviderId());

        return agentWidgetAssembler.toDTOWithEmbedCode(updatedWidget, ModelAssembler.toDTO(model), ProviderAssembler.toDTO(provider));
    }

    /** 切换小组件配置启用状态
     *
     * @param widgetId 小组件配置ID
     * @param userId 用户ID
     * @return 更新后的小组件配置DTO */
    @Transactional
    public AgentWidgetDTO toggleWidgetStatus(String widgetId, String userId) {
        AgentWidgetEntity widget = agentWidgetDomainService.toggleWidgetStatus(widgetId, userId);
        ModelEntity model = llmDomainService.getModelById(widget.getModelId());
        ProviderEntity provider = llmDomainService.getProvider(model.getProviderId());

        return agentWidgetAssembler.toDTOWithEmbedCode(widget, ModelAssembler.toDTO(model), ProviderAssembler.toDTO(provider));
    }

    /** 删除小组件配置
     *
     * @param widgetId 小组件配置ID
     * @param userId 用户ID */
    @Transactional
    public void deleteWidget(String widgetId, String userId) {
        agentWidgetDomainService.deleteWidget(widgetId, userId);
    }

    /** 获取小组件配置详情
     *
     * @param widgetId 小组件配置ID
     * @param userId 用户ID
     * @return 小组件配置DTO */
    public AgentWidgetDTO getWidgetDetail(String widgetId, String userId) {
        AgentWidgetEntity widget = agentWidgetDomainService.getWidgetById(widgetId, userId);

        ModelEntity model = llmDomainService.getModelById(widget.getModelId());
        ProviderEntity provider = llmDomainService.getProvider(model.getProviderId());

        return agentWidgetAssembler.toDTOWithEmbedCode(widget, ModelAssembler.toDTO(model), ProviderAssembler.toDTO(provider));
    }

    /** 根据公开ID获取小组件配置（用于公开访问）
     *
     * @param publicId 公开访问ID
     * @return 小组件配置实体 */
    public AgentWidgetEntity getWidgetForPublicAccess(String publicId) {
        return agentWidgetDomainService.getEnabledWidgetByPublicId(publicId);
    }

    /** 验证域名访问权限
     *
     * @param publicId 公开访问ID
     * @param domain 访问域名
     * @return 是否允许访问 */
    public boolean validateDomainAccess(String publicId, String domain) {
        return agentWidgetDomainService.validateDomainAccess(publicId, domain);
    }

    // 私有辅助方法

    /** 验证Agent所有权 */
    private void validateAgentOwnership(String agentId, String userId) {
        AgentEntity agent = agentRepository.selectById(agentId);
        if (agent == null || agent.getDeletedAt() != null) {
            throw new BusinessException("Agent不存在");
        }

        if (!agent.getUserId().equals(userId)) {
            throw new BusinessException("无权限操作此Agent");
        }
    }
}