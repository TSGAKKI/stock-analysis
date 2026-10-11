package com.stockanalysis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "providers.finnhub")
public record FinnhubProperties(String apiKey, String baseUrl) {
    public String resolvedBaseUrl() { return baseUrl == null || baseUrl.isBlank() ? "https://finnhub.io/api/v1" : baseUrl; }
    public boolean configured() { return apiKey != null && !apiKey.isBlank(); }
}
