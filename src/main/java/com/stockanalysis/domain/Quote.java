package com.stockanalysis.domain;

public record Quote(String symbol, FactValue<java.math.BigDecimal> lastPrice, FactValue<java.math.BigDecimal> change, FactValue<java.math.BigDecimal> changePercent, FactValue<Long> volume) {}
