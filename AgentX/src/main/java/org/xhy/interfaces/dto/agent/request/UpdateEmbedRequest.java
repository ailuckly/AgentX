package org.xhy.interfaces.dto.agent.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/** 更新嵌入配置请求 */
public class UpdateEmbedRequest {

    /** 嵌入名称 */
    @NotBlank(message = "嵌入名称不能为空")
    @Size(max = 100, message = "嵌入名称长度不能超过100字符")
    private String embedName;

    /** 嵌入描述 */
    @Size(max = 500, message = "嵌入描述长度不能超过500字符")
    private String embedDescription;

    /** 指定使用的模型ID */
    @NotBlank(message = "请选择模型")
    private String modelId;

    /** 可选：指定服务商ID */
    private String providerId;

    /** 允许的域名列表 */
    private List<String> allowedDomains;

    /** 每日调用限制（-1为无限制） */
    @NotNull(message = "每日限制不能为空")
    private Integer dailyLimit;

    /** 是否启用 */
    @NotNull(message = "启用状态不能为空")
    private Boolean enabled;

    // Getter和Setter方法
    public String getEmbedName() {
        return embedName;
    }

    public void setEmbedName(String embedName) {
        this.embedName = embedName;
    }

    public String getEmbedDescription() {
        return embedDescription;
    }

    public void setEmbedDescription(String embedDescription) {
        this.embedDescription = embedDescription;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public List<String> getAllowedDomains() {
        return allowedDomains;
    }

    public void setAllowedDomains(List<String> allowedDomains) {
        this.allowedDomains = allowedDomains;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}