package com.stockanalysis.domain;

import java.time.Instant;

public record Bar(FactValue<Instant> timestamp, FactValue<java.math.BigDecimal> open, FactValue<java.math.BigDecimal> high, FactValue<java.math.BigDecimal> low, FactValue<java.math.BigDecimal> close, FactValue<Long> volume) {}
