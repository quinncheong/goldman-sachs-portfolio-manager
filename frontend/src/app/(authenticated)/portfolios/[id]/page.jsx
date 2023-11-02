"use client";

import { useState } from "react";
import Loader from "@/components/loading/Loader";
import { useRouter } from "next/navigation";
import {
  useDeletePortfolio,
  useGetPortfolioByPortfolioId,
} from "@/api/portfolio";
import PortfolioFinancials from "./PortfolioFinancials";
import PortfolioAnalysis from "./PortfolioAnalysis";
import StockHoldings from "./StockHoldings";
import AddStockModal from "./AddStockModal";

export default function PortfolioPage({ params }) {
  const router = useRouter();
  const { data, isLoading, isError, error } = useGetPortfolioByPortfolioId(
    params.id
  );

  function openModal() {
    document.getElementById("add-stock-modal").showModal();
  }

  function closeModal() {
    document.getElementById("add-stock-modal").close();
  }
  const {
    isDeleteing,
    isSuccessDeleting,
    isErrorDeleting,
    deleteError,
    delPortfolio,
  } = useDeletePortfolio();
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

  const editPorfolio = (e) => {
    e.preventDefault();
  };

  const handleDeletePortfolio = (e) => {
    e.preventDefault();
    delPortfolio(params.id);
    router.push("/portfolios");
  };

  const addStock = (e) => {
    e.preventDefault();
    router.push("/addstock");
  };

  return (
    <div className="container mx-auto p-4 text-black">
      <div className="flex flex-row justify-between items-center">
        <div className="containeer p-4 text-black">
          <h2 className="text-4xl font-semibold">{data.name}</h2>
          <p className="text-xl">{data.description}</p>
        </div>
        <button
          className="btn btn-error p-4 text-white border-0"
          onClick={handleDeletePortfolio}
        >
          Delete Portfolio
        </button>
        <button
          className="btn btn-info p-4 text-white border-0"
          onClick={editPorfolio}
        >
          Edit Portfolio
        </button>
        <button
          className="btn bg-primary-200 p-4 text-white border-0"
          onClick={openModal}
        >
          Add Stocks
        </button>
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

      <AddStockModal
        portfolio={data}
        openModal={openModal}
        closeModal={closeModal}
      />
    </div>
  );
}
