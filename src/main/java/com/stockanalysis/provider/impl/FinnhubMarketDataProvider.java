package com.stockanalysis.provider.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.stockanalysis.config.FinnhubProperties;
import com.stockanalysis.domain.Bar;
import com.stockanalysis.domain.FactStatus;
import com.stockanalysis.domain.FactValue;
import com.stockanalysis.domain.Instrument;
import com.stockanalysis.domain.Quote;
import com.stockanalysis.provider.MarketDataProvider;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class FinnhubMarketDataProvider implements MarketDataProvider {
    private static final String SOURCE = "FINNHUB";
    private final FinnhubProperties properties;
    private final RestClient client;

    public FinnhubMarketDataProvider(FinnhubProperties properties) {
        this.properties = properties;
        this.client = RestClient.builder().baseUrl(properties.resolvedBaseUrl()).build();
    }

    @Override public Instrument getInstrument(String symbol) {
        JsonNode body = get("/stock/profile2", symbol, null);
        if (body == null || body.isEmpty()) return new Instrument(symbol, unavailable("/stock/profile2"), unavailable("/stock/profile2"), unavailable("/stock/profile2"));
        Instant observedAt = Instant.now();
        return new Instrument(symbol, text(body, "name", observedAt, FactStatus.EOD, "/stock/profile2"), text(body, "exchange", observedAt, FactStatus.EOD, "/stock/profile2"), text(body, "currency", observedAt, FactStatus.EOD, "/stock/profile2"));
    }

    @Override public Quote getQuote(String symbol) {
        JsonNode body = get("/quote", symbol, null);
        if (body == null) return new Quote(symbol, unavailable("/quote"), unavailable("/quote"), unavailable("/quote"), unavailable("/quote"));
        Instant asOf = timestamp(body.path("t"), Instant.now());
        return new Quote(symbol, decimal(body, "c", asOf, FactStatus.DELAYED, "/quote"), decimal(body, "d", asOf, FactStatus.DELAYED, "/quote"), decimal(body, "dp", asOf, FactStatus.DELAYED, "/quote"), unavailable("/quote: volume not supplied by this endpoint"));
    }

    @Override public List<Bar> getBars(String symbol, String interval) {
        String resolution = resolution(interval);
        long to = Instant.now().getEpochSecond();
        long from = Instant.now().minus(Duration.ofDays(365)).getEpochSecond();
        JsonNode body = get("/stock/candle", symbol, "resolution=" + resolution + "&from=" + from + "&to=" + to);
        if (body == null || !"ok".equalsIgnoreCase(body.path("s").asText())) return List.of();
        JsonNode times = body.path("t"); JsonNode opens = body.path("o"); JsonNode highs = body.path("h"); JsonNode lows = body.path("l"); JsonNode closes = body.path("c"); JsonNode volumes = body.path("v");
        List<Bar> bars = new ArrayList<>();
        FactStatus status = "D".equals(resolution) ? FactStatus.EOD : FactStatus.DELAYED;
        for (int i = 0; i < times.size(); i++) {
            Instant barTime = timestamp(times.path(i), Instant.now());
            bars.add(new Bar(available(barTime, barTime, status, "/stock/candle?resolution=" + resolution), decimal(opens, i, barTime, status, resolution), decimal(highs, i, barTime, status, resolution), decimal(lows, i, barTime, status, resolution), decimal(closes, i, barTime, status, resolution), longValue(volumes, i, barTime, status, resolution)));
        }
        return bars;
    }

    private JsonNode get(String path, String symbol, String extraQuery) {
        if (!properties.configured()) return null;
        String query = "symbol=" + symbol + "&token=" + properties.apiKey() + (extraQuery == null ? "" : "&" + extraQuery);
        try { return client.get().uri(path + "?" + query).retrieve().body(JsonNode.class); }
        catch (RuntimeException ignored) { return null; }
    }
    private static String resolution(String interval) { return switch (interval) { case "1m" -> "1"; case "5m" -> "5"; case "1h" -> "60"; case "1w" -> "W"; default -> "D"; }; }
    private static Instant timestamp(JsonNode value, Instant fallback) { return value.canConvertToLong() && value.asLong() > 0 ? Instant.ofEpochSecond(value.asLong()) : fallback; }
    private static <T> FactValue<T> available(T value, Instant asOf, FactStatus status, String provenance) { return new FactValue<>(value, SOURCE, asOf, status, provenance); }
    private static <T> FactValue<T> unavailable(String provenance) { return FactValue.unavailable(SOURCE, provenance); }
    private static FactValue<String> text(JsonNode body, String field, Instant asOf, FactStatus status, String provenance) { return body.path(field).isTextual() && !body.path(field).asText().isBlank() ? available(body.path(field).asText(), asOf, status, provenance) : unavailable(provenance); }
    private static FactValue<BigDecimal> decimal(JsonNode body, String field, Instant asOf, FactStatus status, String provenance) { return body.path(field).isNumber() ? available(body.path(field).decimalValue(), asOf, status, provenance) : unavailable(provenance); }
    private static FactValue<BigDecimal> decimal(JsonNode values, int index, Instant asOf, FactStatus status, String resolution) { return values.path(index).isNumber() ? available(values.path(index).decimalValue(), asOf, status, "/stock/candle?resolution=" + resolution) : unavailable("/stock/candle?resolution=" + resolution); }
    private static FactValue<Long> longValue(JsonNode values, int index, Instant asOf, FactStatus status, String resolution) { return values.path(index).isNumber() ? available(values.path(index).asLong(), asOf, status, "/stock/candle?resolution=" + resolution) : unavailable("/stock/candle?resolution=" + resolution); }
}
