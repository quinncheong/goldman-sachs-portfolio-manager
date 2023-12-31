import HoldingsCard from "./HoldingsCard";
import MarketSector from "./MarketSector";
import { jwtDecode } from "jwt-decode";
import { getCookie } from "cookies-next";

export default function StockHoldings({
  stockDetails,
  stockData,
  portfolioData,
}) {
  let userId = jwtDecode(getCookie("token")).userId;
  function renderStocks() {
    if (stockDetails === undefined)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            Loading Error
          </h1>
        </div>
      );
    if (Object.keys(stockDetails).length == 0)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            No stocks added!
          </h1>
        </div>
      );
    const keys = Object.keys(stockDetails);
    return keys.map((key) => {
      const stock = stockDetails[key];
      return (
        <HoldingsCard key={key} stock={stock} portfolioData={portfolioData} />
      );
    });
  }

  function renderMarketSector() {
    if (stockData === undefined)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            Loading Error
          </h1>
        </div>
      );
    if (Object.keys(stockData.sector).length == 0)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            No stocks added!
          </h1>
        </div>
      );
    const keys = Object.keys(stockData.sector);
    return keys.map((key) => {
      const data = stockData.sector[key];
      return <MarketSector key={key} sectorKey={key} stockData={data} />;
    });
  }

  return (
    <div className="grid gap-5 p-5 grid-cols-12">
      <div className="col-span-9 p-2 bg-white rounded-md">
        <div className="h-20">
          <h1 className="m-3 text-xl font-semibold">Stock Holdings</h1>
          <div className="w-full flex flex-row items-end min-h-8">
            <div className="w-[30%] text-xs">
              <span>Symbol | Name</span>
            </div>
            <div className="w-[13%] text-xs">
              <span>Pos. | Mkt.</span>
            </div>
            <div className="w-[13%] text-xs">
              <span>Last | Cost</span>
            </div>
            <div className="w-[12.5%] text-xs">
              <span>P&L</span>
            </div>
            <div className="w-[13%] text-xs">
              <span>Daily P&L</span>
            </div>
            <div className="w-[12.5%] text-xs">
              <span>Pos. Ratio</span>
            </div>
            {userId === portfolioData.userId && (
              <div className="w-[6%] text-xs">
                <span>Remove</span>
              </div>
            )}
          </div>
        </div>
        {renderStocks()}
      </div>
      <div className="col-span-3 p-2 bg-white rounded-md">
        <div className="h-20">
          <h1 className="m-3 text-xl font-semibold">Mkt. Sector Breakdown</h1>
          <div className="w-full flex flex-row items-end min-h-8">
            <div className="w-8/12 text-xs">
              <span>Mkt Sector</span>
            </div>
            <div className="w-4/12 text-xs">
              <span>Sector Ratio</span>
            </div>
          </div>
        </div>
        {renderMarketSector()}
      </div>
    </div>
  );
}
