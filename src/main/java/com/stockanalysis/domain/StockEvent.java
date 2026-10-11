package com.stockanalysis.domain;

import java.time.Instant;

public record StockEvent(String type, FactValue<Instant> scheduledAt, FactValue<String> description) {}
