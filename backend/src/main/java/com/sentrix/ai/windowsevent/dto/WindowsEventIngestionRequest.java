package com.sentrix.ai.windowsevent.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public class WindowsEventIngestionRequest {

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Computer name is required")
    private String computerName;

    @NotEmpty(message = "Events list cannot be empty")
    @Size(max = 1000, message = "Maximum 1000 events per ingestion batch")
    @Valid
    private List<RawWindowsEventDto> events;

    // Getters and Setters
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getComputerName() { return computerName; }
    public void setComputerName(String computerName) { this.computerName = computerName; }
    public List<RawWindowsEventDto> getEvents() { return events; }
    public void setEvents(List<RawWindowsEventDto> events) { this.events = events; }
}
