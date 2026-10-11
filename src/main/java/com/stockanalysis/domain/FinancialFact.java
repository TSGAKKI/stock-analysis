package com.stockanalysis.domain;

import java.math.BigDecimal;

public record FinancialFact(String metric, FactValue<BigDecimal> amount, FactValue<String> period) {}
