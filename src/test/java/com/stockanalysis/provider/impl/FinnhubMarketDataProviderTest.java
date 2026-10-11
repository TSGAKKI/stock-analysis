package com.stockanalysis.provider.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.stockanalysis.config.FinnhubProperties;
import com.stockanalysis.domain.FactStatus;
import org.junit.jupiter.api.Test;

class FinnhubMarketDataProviderTest {
    private final FinnhubMarketDataProvider provider = new FinnhubMarketDataProvider(new FinnhubProperties("", "http://localhost"));

    @Test void quoteIsExplicitlyUnavailableWhenNoApiKeyIsConfigured() {
        var quote = provider.getQuote("NVDA");
        assertNull(quote.lastPrice().value());
        assertEquals("FINNHUB", quote.lastPrice().source());
        assertEquals(FactStatus.UNAVAILABLE, quote.lastPrice().status());
        assertEquals(FactStatus.UNAVAILABLE, quote.volume().status());
    }
}
