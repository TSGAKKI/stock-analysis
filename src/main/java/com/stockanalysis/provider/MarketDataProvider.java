package com.stockanalysis.provider;

import com.stockanalysis.domain.Bar;
import com.stockanalysis.domain.Instrument;
import com.stockanalysis.domain.Quote;
import java.util.List;

public interface MarketDataProvider { Instrument getInstrument(String symbol); Quote getQuote(String symbol); List<Bar> getBars(String symbol, String interval); }
