"use client";
import { useEffect, useState } from "react";

import { useGetPortfoliosByUserId } from "@api/portfolio.js";
import PortfolioCard from "./PortfolioCard";
import { useRouter } from "next/navigation";
import { jwtDecode } from "jwt-decode";
import secureLocalStorage from "react-secure-storage";

export default function Portfolio(props) {
  const { data, isLoading, isError, error } = useGetPortfoliosByUserId(
    jwtDecode(secureLocalStorage.getItem("token")).userId
  );
  const router = useRouter();

  const addPortfolio = (e) => {
    e.preventDefault();
    router.push("/addportfolio");
  };

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error: {error.message}</div>;

  return (
    <div className="container flex flex-col m-auto p-5 h-screen">
      <div className="rounded-md p-4 text-white bg-secondary-100 mb-5 items-center flex flex-row justify-between">
        <h2 className="text-xl sm:text-2xl font-semibold text-white">
          Your Portfolio:
        </h2>
        <button
          className="btn bg-primary-200 text-white border-0"
          onClick={addPortfolio}
        >
          Add Portfolio
        </button>
      </div>
      <div className="flex flex-wrap justify-between">{renderPortfolios()}</div>
    </div>
  );

  function renderPortfolios() {
    if (!data) return <div>Portfolios Loading</div>;
    return data.map((portfolio, index) => {
      return <PortfolioCard key={index} portfolio={portfolio} />;
    });
  }
}
