package com.stockanalysis.provider;

import com.stockanalysis.domain.FinancialFact;
import java.util.List;

public interface FinancialDataProvider { List<FinancialFact> getFinancials(String symbol); List<FinancialFact> getValuation(String symbol); List<FinancialFact> getStatistics(String symbol); }
