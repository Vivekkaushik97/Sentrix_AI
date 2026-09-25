package com.sentrix.ai.context.dto;

import java.util.List;

public class SecurityContextGraphDto {
    private List<SecurityContextNodeDto> nodes;
    private List<SecurityContextEdgeDto> edges;

    public List<SecurityContextNodeDto> getNodes() { return nodes; }
    public void setNodes(List<SecurityContextNodeDto> nodes) { this.nodes = nodes; }
    public List<SecurityContextEdgeDto> getEdges() { return edges; }
    public void setEdges(List<SecurityContextEdgeDto> edges) { this.edges = edges; }
}
