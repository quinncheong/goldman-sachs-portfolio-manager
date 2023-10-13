"use client";

import { useGetPortfolios } from "@api/portfolio.js";

import PortfolioCard from "./PortfolioCard";

export default function Portfolio() {
  const { data, isLoading, isError, error } = useGetPortfolios();

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error: {error.message}</div>;

  return (
    <div className="container flex flex-col m-auto p-5 h-screen">
      <div className="flex flex-wrap gap-5">{renderPortfolios()}</div>
    </div>
  );

  function renderPortfolios() {
    if (!data) return <div>Portfolios Loading</div>;
    return data.map((portfolio, index) => {
      return <PortfolioCard key={index} portfolio={portfolio} />;
    });
  }
}
