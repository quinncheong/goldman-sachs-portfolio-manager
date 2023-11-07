import React from "react";
import PortfolioMarketChart from "./PortfolioMarketChart";
import PortfolioSectorChart from "./PortfolioSectorChart";
import MonetaryAnalysis from "./MonetaryAnalysis";

export default function PortfolioAnalysis({
  stockData,
  stockDetails,
  analysisData,
  portfolioData,
}) {
  const renderAnalysis = () => {
    if (analysisData === undefined)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            Loading Error
          </h1>
        </div>
      );
    return <MonetaryAnalysis analysisData={analysisData} portfolioData={portfolioData} />;
  };

  function renderSectorChart(type) {
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
    return <PortfolioSectorChart stockData={stockData} type={type} />;
  }

  function renderTotal() {
    if (analysisData === undefined)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            Loading Error
          </h1>
        </div>
      );
    if (Object.keys(analysisData).length == 0)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            No stocks added!
          </h1>
        </div>
      );
    const securitiesValue = new Intl.NumberFormat("en-US", {
      style: "decimal",
    }).format(analysisData.value);
    const cashBalance = new Intl.NumberFormat("en-US", {
      style: "decimal",
    }).format(portfolioData.initialValue);

    return { total: securitiesValue, cash: cashBalance };
  }

  return (
    <div className="grid gap-5 p-5 grid-cols-12">
      <div className="col-span-12 p-5 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Time Series Data</h1>
        <PortfolioMarketChart />
      </div>

      <div className="col-span-4 conatainer p-5 bg-white rounded-md">
        <h1 className="mb-1 text-xl text-black font-semibold">
          Total Asset Value
        </h1>
        <span className="text-sm sm:text-md text-gray-400">
          Securities Value:
        </span>
        <span className="text-xl text-black">&emsp;${renderTotal().total}</span>
        <br></br>
        <span className="text-sm sm:text-md text-gray-400">Cash Balance:</span>
        <span className="text-xl text-black">&emsp;${renderTotal().cash}</span>
      </div>

      <div className="col-span-8 p-5 bg-white rounded-md">
        <h1 className="mb-1 text-black text-xl font-semibold">
          Portfolio Analysis
        </h1>
        {renderAnalysis()}
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
