"use client";

import Loader from "@/components/loading/Loader";
import { useGetPortfolioByPortfolioId } from "@/api/portfolio";
import PortfolioFinancials from "./PortfolioFinancials";
import PortfolioAnalysis from "./PortfolioAnalysis";

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
    <div className="container mx-auto p-4 text-black">
      <div className="containeer mx-auto p-4 text-black">
        <h2 className="text-4xl font-semibold">{data.name}</h2>
        <p className="text-xl">{data.description}</p>
      </div>

      <div className="rounded-md p-4 text-white bg-gray-500">
        <h2 className="text-2xl font-semibold">
          Portfolio Health and Financials
        </h2>
      </div>
      <PortfolioFinancials />

      <div className="rounded-md p-4 text-white bg-gray-500">
        <h2 className="text-2xl font-semibold">Portfolio Analysis</h2>
      </div>
      <PortfolioAnalysis />

      <div className="rounded-md p-4 text-white bg-gray-500">
        <h2 className="text-2xl font-semibold">Holdings</h2>
      </div>
      <PortfolioAnalysis />
    </div>
  );
}
