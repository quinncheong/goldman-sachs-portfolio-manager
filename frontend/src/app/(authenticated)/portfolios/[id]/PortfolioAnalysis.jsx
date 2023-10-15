import React from "react";
import PortfolioMarketChart from "./PortfolioMarketChart";
import PortfolioSectorChart from "./PortfolioSectorChart";

export default function PortfolioAnalysis() {
  const portfolioMonetaryAnalysis = () => {
    return (
      <div className="flex sm:flex-row justify-between">
        <div className="flex flex-col mb-4 sm:mb-0">
          <span className="text-sm sm:text-md text-gray-400">
            Daily P&amp;L:
          </span>
          <span className="text-lg sm:text-2xl">-$9.25</span>
          <div className="badge badge-error">-1.60%</div>
        </div>
        <div className="flex flex-col mb-4 sm:mb-0">
          <span className="text-sm sm:text-md text-gray-400">
            Total P&amp;L:
          </span>
          <span className="text-lg sm:text-2xl">$2,769.25</span>
          <div className="badge badge-success">+5.75%</div>
        </div>
        <div className="flex flex-col">
          <span className="text-sm sm:text-md text-gray-400">
            Annualized Rate of Return:
          </span>
          <span className="text-lg sm:text-2xl">20.50%</span>
          <div className="text-sm">
            <span className="text-gray-500">DJIA: </span>
            <span className="text-green-400">10.10%</span>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="grid gap-5 p-5">
      <div className="col-span-2 p-5 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Time Series Data</h1>
        <PortfolioMarketChart />
      </div>

      <div className="col-span-1 p-5 bg-gray-700 rounded-md">
        <h1 className="mb-1 text-white text-xl font-semibold">
          Portfolio Analysis
        </h1>
        {portfolioMonetaryAnalysis()}
      </div>

      <div className="col-span-1 conatainer p-5 bg-gray-700 rounded-md">
        <h1 className="mb-1 text-xl text-white font-semibold">
          Portfolio Analysis
        </h1>
        {portfolioMonetaryAnalysis()}
      </div>

      <div className="col-span-1 p-5 h-96 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Sector Market Data</h1>
        <PortfolioSectorChart />
      </div>
      <div className="col-span-1 p-5 h-96 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Country Segregated Data</h1>
        <PortfolioSectorChart />
      </div>
    </div>
  );
}
