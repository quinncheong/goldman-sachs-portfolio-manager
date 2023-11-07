"use client";
import "react-data-grid/lib/styles.css";

import { useState, useEffect } from "react";
import DataGrid from "react-data-grid";
import Link from "next/link";
import { useGetAnalysisArray } from "@/api/portfolio";
import Loader from "@/components/loading/Loader";

const headers = [];

const columns = [
  { key: "name", name: "Portfolio Name" },
  { key: "description", name: "Portfolio Description" },
  { key: "initialValue", name: "Total Cash" },
  { key: "value", name: "Total Securities" },
  { key: "PNL", name: "PNL" },
  { key: "view", name: "View Portfolio" },
];



export default function PortfolioTable({ portfolios }) {

  useEffect(() => {
    if (!portfolios) {
      return;
    }
    for (let p of portfolios) {
      

      p.view = <ViewMore portfolioId={p.id} />;
      p.PNL = 100
      
    }
  }, [portfolios]);

  if (!portfolios) {
    return <div>No Portfolios to display! Start creating </div>;
  }

  const dynamicHeight = Math.min(portfolios.length * 6 + 30, 40) + "vh";

  return (
    <DataGrid
      style={{ height: dynamicHeight }}
      columns={columns}
      rows={portfolios.slice(0, 5)}
      rowHeight={85}
      sot
    />
  );
}

function ViewMore({ portfolioId }) {
  return (
    <Link href={"/portfolios/" + portfolioId}>
      <button className="btn bg-background text-primary-200">
        View Portfolio
      </button>
    </Link>
  );
}
