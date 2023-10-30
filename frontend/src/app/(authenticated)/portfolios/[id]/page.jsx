"use client";

import Loader from "@/components/loading/Loader";
import { useRouter } from "next/navigation";
import { useGetPortfolioByPortfolioId } from "@/api/portfolio";
import PortfolioFinancials from "./PortfolioFinancials";
import PortfolioAnalysis from "./PortfolioAnalysis";
import StockHoldings from "./StockHoldings";

export default function PortfolioPage({ params }) {
  const router = useRouter()
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

  const addStock = (e) => {
    e.preventDefault()
    router.push("/addstock")
  }

  return (
    <div className="container mx-auto p-4 text-black">
      <div className="flex flex-row justify-between items-center">
        <div className="containeer p-4 text-black">
          <h2 className="text-4xl font-semibold">{data.name}</h2>
          <p className="text-xl">{data.description}</p>
        </div>
        <button className="btn bg-primary-200 p-4 text-white border-0" onClick={addStock}>Add Stocks</button>
      </div>

      <div className="rounded-md p-4 text-white bg-secondary-100">
        <h2 className="text-2xl font-semibold">Portfolio Analysis</h2>
      </div>
      <PortfolioAnalysis />

      <div className="rounded-md p-4 text-white bg-secondary-100">
        <h2 className="text-2xl font-semibold">Holdings</h2>
      </div>
      {/* <PortfolioAnalysis /> */}
      <StockHoldings />
    </div>
  );
}
