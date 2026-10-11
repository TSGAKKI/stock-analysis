package com.stockanalysis.provider;

import com.stockanalysis.domain.StockEvent;
import java.util.List;

public interface EventDataProvider { List<StockEvent> getEvents(String symbol); }
