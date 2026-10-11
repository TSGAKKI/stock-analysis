package com.stockanalysis.api;

import com.stockanalysis.domain.*;
import com.stockanalysis.provider.MarketDataProvider;
import com.stockanalysis.service.UnavailableStockFactService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stocks/{symbol}")
public class StockController {
    private final MarketDataProvider marketData;
    private final UnavailableStockFactService unavailableFacts;
    public StockController(MarketDataProvider marketData, UnavailableStockFactService unavailableFacts) { this.marketData = marketData; this.unavailableFacts = unavailableFacts; }
    @GetMapping public Instrument stock(@PathVariable("symbol") String symbol) { return marketData.getInstrument(symbol); }
    @GetMapping("/quote") public Quote quote(@PathVariable("symbol") String symbol) { return marketData.getQuote(symbol); }
    @GetMapping("/bars") public List<Bar> bars(@PathVariable("symbol") String symbol, @RequestParam(name = "interval", defaultValue = "1d") String interval) { return marketData.getBars(symbol, interval); }
    @GetMapping("/technicals") public List<FinancialFact> technicals(@PathVariable("symbol") String symbol) { return unavailableFacts.technicals(); }
    @GetMapping("/statistics") public List<FinancialFact> statistics(@PathVariable("symbol") String symbol) { return unavailableFacts.statistics(); }
    @GetMapping("/financials") public List<FinancialFact> financials(@PathVariable("symbol") String symbol) { return unavailableFacts.financials(); }
    @GetMapping("/valuation") public List<FinancialFact> valuation(@PathVariable("symbol") String symbol) { return unavailableFacts.valuation(); }
    @GetMapping("/events") public List<StockEvent> events(@PathVariable("symbol") String symbol) { return unavailableFacts.events(); }
}
