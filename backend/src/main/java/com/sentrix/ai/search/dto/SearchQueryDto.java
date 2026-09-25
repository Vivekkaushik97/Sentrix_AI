package com.sentrix.ai.search.dto;

public class SearchQueryDto {
    private String query;
    private String typeFilter;
    private int limit = 20;

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getTypeFilter() { return typeFilter; }
    public void setTypeFilter(String typeFilter) { this.typeFilter = typeFilter; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { 
        this.limit = limit > 100 ? 100 : Math.max(limit, 1); 
    }
}
