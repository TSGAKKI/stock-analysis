package com.stockanalysis.service;

import com.stockanalysis.domain.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnavailableStockFactService {
    private static final String SOURCE = "NO_PROVIDER";
    private static final String PROVENANCE = "Provider integration has not been configured";
    private static <T> FactValue<T> unavailable() { return FactValue.unavailable(SOURCE, PROVENANCE); }
    public Instrument instrument(String symbol) { return new Instrument(symbol, unavailable(), unavailable(), unavailable()); }
    public Quote quote(String symbol) { return new Quote(symbol, unavailable(), unavailable(), unavailable(), unavailable()); }
    public List<Bar> bars() { return List.of(); }
    public List<FinancialFact> financials() { return List.of(new FinancialFact("financials", unavailable(), unavailable())); }
    public List<FinancialFact> valuation() { return List.of(new FinancialFact("valuation", unavailable(), unavailable())); }
    public List<FinancialFact> statistics() { return List.of(new FinancialFact("statistics", unavailable(), unavailable())); }
    public List<FinancialFact> technicals() { return List.of(new FinancialFact("technicals", unavailable(), unavailable())); }
    public List<StockEvent> events() { return List.of(new StockEvent("earnings", unavailable(), unavailable())); }
}
