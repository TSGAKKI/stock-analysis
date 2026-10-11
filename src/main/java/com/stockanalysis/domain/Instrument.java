package com.stockanalysis.domain;

import java.util.Objects;

public record Instrument(String symbol, FactValue<String> name, FactValue<String> exchange, FactValue<String> currency) {
    public Instrument { Objects.requireNonNull(symbol, "symbol is required"); }
}
