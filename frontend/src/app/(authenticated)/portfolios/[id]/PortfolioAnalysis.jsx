import React, { useState } from "react";
import PortfolioMarketChart from "./PortfolioMarketChart";
import PortfolioSectorChart from "./PortfolioSectorChart";
import MonetaryAnalysis from "./MonetaryAnalysis";

export default function PortfolioAnalysis({
  stockData,
  stockDetails,
  analysisData,
  portfolioData,
}) {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [render, setRender] = useState(false);

  const renderAnalysis = () => {
    if (analysisData === undefined)
      return (
        <div>
          <h1 className="m-3 text-xl font-semibold text-red-600">
            Loading Error
          </h1>
        </div>
      );
    return (
      <MonetaryAnalysis
        analysisData={analysisData}
        portfolioData={portfolioData}
      />
    );
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

  function useRenderMarketChart() {
    portfolioData.start = startDate;
    portfolioData.end = endDate;
    setRender(true);
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
      style: "currency",
      currency: "USD",
    }).format(analysisData.value);
    const cashBalance = new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(portfolioData.initialValue);

    return { total: securitiesValue, cash: cashBalance };
  }

  return (
    <div className="grid gap-5 p-5 grid-cols-12">
      <div className="col-span-12 p-5 bg-white rounded-md">
        <h1 className="m-3 text-xl font-semibold">Time Series Data</h1>
        <div className="flex items-center">
          <div className="relative">
            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <svg
                className="w-4 h-4 text-gray-500 dark:text-gray-400"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
              </svg>
            </div>
            <input
              value={startDate}
              onChange={(e) => {
                setStartDate(e.target.value)
                setRender(false)
              }}
              name="start"
              type="date"
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Select date start"
            />
          </div>
          <span className="mx-4 text-gray-500">to</span>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <svg
                className="w-4 h-4 text-gray-500 dark:text-gray-400"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
              </svg>
            </div>
            <input
              value={endDate}
              onChange={(e) => {
                setEndDate(e.target.value)
                setRender(false)
              }}
              name="end"
              type="date"
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Select date end"
            />
          </div>
          <button type="button" onClick={useRenderMarketChart}>
              Go
            </button>
        </div>
        {render && <PortfolioMarketChart portfolioData={portfolioData} />}
      </div>

      <div className="col-span-4 conatainer p-5 bg-white rounded-md">
        <h1 className="mb-1 text-xl text-black font-semibold">
          Total Asset Value
        </h1>
        <span className="text-sm sm:text-md text-gray-400">
          Securities Value:
        </span>
        <span className="text-xl text-black"> {renderTotal().total}</span>
        <br></br>
        <span className="text-sm sm:text-md text-gray-400">Cash Balance:</span>
        <span className="text-xl text-black"> {renderTotal().cash}</span>
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
