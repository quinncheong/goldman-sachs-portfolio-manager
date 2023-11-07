import { useGetROROfPortfolio } from "@/api/portfolio";
import React from "react";
import { Dna } from "react-loader-spinner";

export default function MonetaryAnalysis({ analysisData, portfolioData }) {
  const {
    data: ror,
    isLoading: rorLoading,
    isError: isRorError,
    error: rorError,
  } = useGetROROfPortfolio(portfolioData.id);

  return (
    <div className="flex sm:flex-row justify-between">
      <div className="flex flex-col mb-4 sm:mb-0">
        <span className="text-sm sm:text-md text-gray-400">Daily P&amp;L:</span>
        <span className="text-lg sm:text-2xl">
          {analysisData.dpnla >= 0 ? "+" : "-"}
          {new Intl.NumberFormat("en-US", {
            style: "currency",
            currency: "USD",
          }).format(Math.abs(analysisData.dpnla).toFixed(2))}
        </span>
        <div
          className={`badge text-white font-bold ${
            analysisData.dpnl >= 0 ? "badge-success" : "badge-error"
          }`}
        >
          {analysisData.dpnl >= 0 ? "+" : "-"}
          {Math.abs(analysisData.dpnl).toFixed(2)}%
        </div>
      </div>
      <div className="flex flex-col mb-4 sm:mb-0">
        <span className="text-sm sm:text-md text-gray-400">Total P&amp;L:</span>
        <span className="text-lg sm:text-2xl">
          {analysisData.pnla >= 0 ? "+$" : "-$"}
          {new Intl.NumberFormat("en-US", { style: "decimal" }).format(
            Math.abs(analysisData.pnla).toFixed(2)
          )}
        </span>
        <div
          className={`badge text-white font-bold ${
            analysisData.pnl >= 0 ? "badge-success" : "badge-error"
          }`}
        >
          {analysisData.pnl >= 0 ? "+" : "-"}
          {Math.abs(analysisData.pnl).toFixed(2)}%
        </div>
      </div>
      <div className="flex flex-col">
        <span className="text-sm sm:text-md text-gray-400">
          Annualized Rate of Return:
        </span>
        <ROR ror={ror} isLoading={rorLoading} />
      </div>
    </div>
  );
}

function ROR({ ror, isLoading }) {
  if (isLoading)
    return (
      <div className="">
        <Dna
          visible={true}
          height="100"
          width="200"
          ariaLabel="dna-loading"
          wrapperStyle={{}}
          wrapperClass="dna-wrapper"
        />
        <div>Calculating Annualized ROR...</div>
      </div>
    );
  else
    return (
      <span
        className={`sm:text-2xl ${ror >= 0 ? "text-success" : "text-error"}`}
      >
        {ror >= 0 ? "+" : "-"}
        {Math.abs(ror).toFixed(2)}%
      </span>
    );
}
