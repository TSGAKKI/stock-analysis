import { CompanyProfile, FinancialHighlights, KeyStatistics, PriceChart, StockHeader, TechnicalIndicators, UpcomingEvents, ValuationPanel, VolumePane, Watchlist } from "../../../components/stock";

export default async function StockPage({ params }: { params: Promise<{ symbol: string }> }) {
  const { symbol } = await params;
  const normalized = symbol.toUpperCase();
  return <main><div className="layout"><Watchlist /><section><StockHeader symbol={normalized} /><div className="grid"><PriceChart symbol={normalized} /><VolumePane /><KeyStatistics /><TechnicalIndicators /><FinancialHighlights /><ValuationPanel /><UpcomingEvents /><CompanyProfile /></div></section></div></main>;
}
