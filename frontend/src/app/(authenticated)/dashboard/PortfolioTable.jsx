"use client";
import "react-data-grid/lib/styles.css";

import { useState, useEffect } from "react";
import DataGrid from "react-data-grid";
import Link from "next/link";

const headers = [];

const columns = [
  { key: "id", name: "Id" },
  { key: "name", name: "Portfolio Name" },
  { key: "description", name: "Portfolio Description" },
  { key: "initialValue", name: "Initial Value" },
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
      p.PNL = 100;
    }
  }, [portfolios]);

  if (!portfolios) {
    return <div>No Portfolios to display! Start creating </div>;
  }

  const dynamicHeight = Math.min(portfolios.length * 6 + 10, 50) + "vh";

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
