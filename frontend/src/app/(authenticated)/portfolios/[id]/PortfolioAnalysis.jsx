import React from "react";
import PortfolioMarketChart from "./PortfolioMarketChart";
import PortfolioSectorChart from "./PortfolioSectorChart";

export default function PortfolioAnalysis({ stockData,stockDetails }) {
  const portfolioMonetaryAnalysis = () => {
    return (
      <div className="flex sm:flex-row justify-between">
        <div className="flex flex-col mb-4 sm:mb-0">
          <span className="text-sm sm:text-md text-gray-400">
            Daily P&amp;L:
          </span>
          <span className="text-lg sm:text-2xl">-$9.25</span>
          <div className="badge badge-error text-white font-bold">-1.60%</div>
        </div>
        <div className="flex flex-col mb-4 sm:mb-0">
          <span className="text-sm sm:text-md text-gray-400">
            Total P&amp;L:
          </span>
          <span className="text-lg sm:text-2xl">$2,769.25</span>
          <div className="badge badge-success text-white font-bold">+5.75%</div>
        </div>
        <div className="flex flex-col">
          <span className="text-sm sm:text-md text-gray-400">
            Annualized Rate of Return:
          </span>
          <span className="text-lg sm:text-2xl">20.50%</span>
          <div className="text-sm">
            <span className="text-gray-500">DJIA: </span>
            <span className="text-green-400 font-bold">10.10%</span>
          </div>
        </div>
      </div>
    );
  };

  function renderSectorChart(type) {
    if (stockData===undefined) return <div><h1 className="m-3 text-xl font-semibold text-red-600">Loading Error</h1></div>;
    if (Object.keys(stockData.sector).length == 0) return <div><h1 className="m-3 text-xl font-semibold text-red-600">No stocks added!</h1></div>;
    return <PortfolioSectorChart stockData={stockData} type={type} />;
  }

  function renderTotal() {
    if (stockDetails===undefined) return <div><h1 className="m-3 text-xl font-semibold text-red-600">Loading Error</h1></div>;
    if (Object.keys(stockDetails).length == 0) return <div><h1 className="m-3 text-xl font-semibold text-red-600">No stocks added!</h1></div>;
    let total = 0;
    const keys = Object.keys(stockDetails)
    
    keys.map((key) => {
        const amount = stockDetails[key].market;
      
        total+=amount;
    });
    return total;
  }
  
  return (
    <div className="grid gap-5 p-5 grid-cols-12">
      <div className="col-span-12 p-5 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Time Series Data</h1>
        <PortfolioMarketChart />
      </div>

      <div className="col-span-4 conatainer p-5 bg-white rounded-md">
        <h1 className="mb-1 text-xl text-black font-semibold">
          Total Securities Value
        </h1>
        <span className="text-xl text-black">${renderTotal()}</span>
      </div>

      <div className="col-span-8 p-5 bg-white rounded-md">
        <h1 className="mb-1 text-black text-xl font-semibold">
          Portfolio Analysis
        </h1>
        {portfolioMonetaryAnalysis()}
      </div>

      <div className="col-span-6 p-5 h-96 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Sector Market Data</h1>
        {renderSectorChart("sector")}
      </div>

      <div className="col-span-6 p-5 h-96 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Country Segregated Data</h1>
        {renderSectorChart("country")}
      </div>
    </div>
  );
}
