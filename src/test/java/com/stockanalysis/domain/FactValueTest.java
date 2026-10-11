package com.stockanalysis.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class FactValueTest {
    @Test void unavailableFactHasNoValue() {
        var fact = FactValue.<String>unavailable("provider", "not configured");
        assertNull(fact.value()); assertEquals(FactStatus.UNAVAILABLE, fact.status());
    }
    @Test void unavailableFactRejectsFabricatedValue() {
        assertThrows(IllegalArgumentException.class, () -> new FactValue<>("invented", "provider", Instant.now(), FactStatus.UNAVAILABLE, "test"));
    }
    @Test void availableFactRequiresTruthMetadata() {
        assertThrows(NullPointerException.class, () -> new FactValue<>("value", null, Instant.now(), FactStatus.DELAYED, "test"));
    }
}
