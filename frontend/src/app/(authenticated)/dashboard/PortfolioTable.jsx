"use client";
import "react-data-grid/lib/styles.css";
import { useGetManyAnalysis } from "@/api/portfolio";
import PortfolioTableRow from "./PortfolioTableRow";
import { useEffect, useState } from "react";
import Loader from "@/components/loading/Loader";

export default function PortfolioTable({ portfolios }) {
  const {
    data: portfolioData,
    isLoading,
    isError,
    error,
  } = useGetManyAnalysis(portfolios);

  if (!portfolios) {
    return <div>No Portfolios to display! Start creating </div>;
  }

  if (isLoading) {
    return <Loader />;
  }

  if (!portfolioData || portfolioData.includes(undefined)) {
    return <></>;
  }
  let appendedPortfolios = [];

  function renderRow() {
    for (let i = 0; i < portfolios.length; i++) {
      for (let j = 0; j < portfolioData.length; j++) {
        if (i == j) {
          appendedPortfolios.push(
            Object.assign(portfolios[i], portfolioData[j])
          );
        }
      }
    }
    const sortedPortfolios = appendedPortfolios.sort((a, b) => b.pnl - a.pnl);
    return sortedPortfolios
      .slice(0, 5)
      .map((portfolio, index) => (
        <PortfolioTableRow portfolio={portfolio} key={index} index={index} />
      ));
  }

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full border-collapse border border-gray-300">
        <thead>
          <tr>
            <th className="py-2 px-4 bg-gray-200 text-left">Portfolio Name</th>
            <th className="py-2 px-4 bg-gray-200 text-left">
              Portfolio Description
            </th>
            <th className="py-2 px-4 bg-gray-200 text-left">Total Cash</th>
            <th className="py-2 px-4 bg-gray-200 text-left">
              Total Securities
            </th>
            <th className="py-2 px-4 bg-gray-200 text-left">PNL (%)</th>
            <th className="py-2 px-4 bg-gray-200 text-left">View Portfolio</th>
          </tr>
        </thead>
        <tbody>{renderRow()}</tbody>
      </table>
    </div>
  );
}
