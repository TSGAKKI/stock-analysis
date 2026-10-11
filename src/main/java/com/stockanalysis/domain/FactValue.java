package com.stockanalysis.domain;

import java.time.Instant;
import java.util.Objects;

/** A provider-supplied fact. Values are absent only when status is UNAVAILABLE. */
public record FactValue<T>(T value, String source, Instant asOfTime, FactStatus status, String provenance) {
    public FactValue {
        Objects.requireNonNull(source, "source is required");
        Objects.requireNonNull(asOfTime, "asOfTime is required");
        Objects.requireNonNull(status, "status is required");
        Objects.requireNonNull(provenance, "provenance is required");
        if (status == FactStatus.UNAVAILABLE && value != null) throw new IllegalArgumentException("UNAVAILABLE facts cannot contain a value");
        if (status != FactStatus.UNAVAILABLE && value == null) throw new IllegalArgumentException("available facts require a value");
    }
    public static <T> FactValue<T> unavailable(String source, String provenance) {
        return new FactValue<>(null, source, Instant.now(), FactStatus.UNAVAILABLE, provenance);
    }
}
