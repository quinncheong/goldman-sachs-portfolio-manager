import React from "react";
import PortfolioMarketChart from "./PortfolioMarketChart";
import PortfolioSectorChart from "./PortfolioSectorChart";

export default function PortfolioAnalysis() {
  const portfolioMonetaryAnalysis = () => {
    return (
      <div className="bg-gray-700 p-4">
        <h2 className="text-xl sm:text-2xl font-semibold mb-4">
          Your Portfolio Analysis:
        </h2>
        <div className="flex flex-col sm:flex-row justify-between">
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
      </div>
    );
  };

  return (
    <div className="flex flex-wrap gap-5 p-5 justify-items items-center">
      <PortfolioMarketChart />
      <PortfolioSectorChart />
      {portfolioMonetaryAnalysis()}
    </div>
  );
}
