package com.sentrix.ai.windowsevent.entity;

import com.sentrix.ai.common.enums.Severity;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "windows_event_correlations")
public class WindowsEventCorrelation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private WindowsEventAnalysis analysis;

    @Column(name = "correlation_key", nullable = false)
    private String correlationKey;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @ManyToMany
    @JoinTable(
        name = "windows_event_correlation_events",
        joinColumns = @JoinColumn(name = "correlation_id"),
        inverseJoinColumns = @JoinColumn(name = "windows_event_id")
    )
    private List<WindowsEvent> events = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public WindowsEventAnalysis getAnalysis() { return analysis; }
    public void setAnalysis(WindowsEventAnalysis analysis) { this.analysis = analysis; }
    public String getCorrelationKey() { return correlationKey; }
    public void setCorrelationKey(String correlationKey) { this.correlationKey = correlationKey; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public List<WindowsEvent> getEvents() { return events; }
    public void setEvents(List<WindowsEvent> events) { this.events = events; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
