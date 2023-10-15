"use client";

import Loader from "@/components/loading/Loader";
import { useGetPortfolioByPortfolioId } from "@/api/portfolio";

export default function PortfolioPage({ params }) {
  const { data, isLoading, isError, error } = useGetPortfolioByPortfolioId(
    params.id
  );
  console.log(data);

  if (isLoading) {
    return <Loader />;
  }

  if (isError) {
    return (
      <div className="flex min-h-screen items-center justify-between p-24">
        <p>Error: {error}</p>
      </div>
    );
  }

  return (
    <div>
      <div className="container mx-auto p-4 dark:bg-gray-800 text-white">
        <h2 className="text-xl sm:text-2xl font-semibold">{data.name}</h2>
        <span className="text-m sm:text-l font-light">{data.description}</span>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 mt-5">
          <div className="bg-gray-700 p-4 rounded shadow flex flex-col pb-10">
            <h2 className="text-xl sm:text-2xl font-semibold mb-4">Your Portfolio Financials:</h2>
            <div className="flex flex-col sm:flex-row justify-between">
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Total Assets:</span>
                <span className="text-lg sm:text-2xl">$25,000</span>
              </div>
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Total Securities Value:</span>
                <span className="text-lg sm:text-2xl">$20,000</span>
              </div>
              <div className="flex flex-col">
                <span className="text-sm sm:text-md text-gray-400">Total Cash Balance:</span>
                <span className="text-lg sm:text-2xl">$5,000</span>
              </div>
            </div>
          </div>
          <div className="bg-gray-700 p-4 rounded shadow flex flex-col pb-10">
            <h2 className="text-xl sm:text-2xl font-semibold mb-4">Your Portfolio Analysis:</h2>
            <div className="flex flex-col sm:flex-row justify-between">
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Daily P&amp;L:</span>
                <span className="text-lg sm:text-2xl">-$9.25</span>
                <div className="badge badge-error">-1.60%</div>
              </div>
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Total P&amp;L:</span>
                <span className="text-lg sm:text-2xl">$2,769.25</span>
                <div className="badge badge-success">+5.75%</div>
              </div>
              <div className="flex flex-col">
                <span className="text-sm sm:text-md text-gray-400">Annualized Rate of Return:</span>
                <span className="text-lg sm:text-2xl">20.50%</span>
                <div className="text-sm">
                  <span className="text-gray-500">DJIA: </span>
                  <span className="text-green-400">10.10%</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
