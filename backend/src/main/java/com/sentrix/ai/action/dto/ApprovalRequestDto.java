package com.sentrix.ai.action.dto;

import jakarta.validation.constraints.NotBlank;

public class ApprovalRequestDto {
    @NotBlank
    private String reason;

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
