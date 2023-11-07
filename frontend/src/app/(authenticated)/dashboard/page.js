"use client";
import { useState, useEffect } from "react";

import Loader from "@/components/loading/Loader";

import {
  useGetPortfoliosOfUser,
  useGetPublicPortfolios,
} from "@api/portfolio.js";
import { useGetAccountData } from "@api/user.js";

import PortfolioTable from "./PortfolioTable";
import PortfolioCarousel from "./PortfolioCarousel";
import { withAuth } from "@/middleware/authentication";

function Dashboard() {
  const { data, isLoading, isError, error } = useGetPortfoliosOfUser();
  const {
    data: accData,
    isLoading: isAccDataLoading,
    isError: isAccDataError,
    error: AccDataError,
  } = useGetAccountData();
  const {
    data: publicPortfolios,
    isLoading: isPublicPorfoliosLoading,
    isError: isPublicPorfoliosError,
    error: publicPortfoliosError,
  } = useGetPublicPortfolios();
  console.log(accData);

  const [financials, setFinancials] = useState([]);
  const [analysis, setAnalysis] = useState([]);

  useEffect(() => {
    if (!accData) {
      return;
    }

    const dpnlStatus = badgeColor(accData.dpnlp);
    const pnlStatus = badgeColor(accData.pnlp);

    let tmpFinancials = [
      {
        label: "Total Assets",
        value:
          "$" +
          new Intl.NumberFormat("en-US", { style: "decimal" }).format(
            accData.totalAssets
          ),
      },
      {
        label: "Total Securities Value",
        value:
          "$" +
          new Intl.NumberFormat("en-US", { style: "decimal" }).format(
            accData.totalSecurities
          ),
      },
      {
        label: "Total Cash Balance",
        value:
          "$" +
          new Intl.NumberFormat("en-US", { style: "decimal" }).format(
            accData.totalCash
          ),
      },
    ];

    let tmpAnalysis = [
      {
        label: "Daily P&L",
        value:
          dpnlStatus[0] +
          "$" +
          new Intl.NumberFormat("en-US", { style: "decimal" }).format(
            Math.abs(accData.dpnla)
          ),
        badge: {
          text:
            dpnlStatus[0] +
            new Intl.NumberFormat("en-US", { style: "decimal" }).format(
              Math.abs(accData.dpnlp)
            ) +
            "%",
          color: dpnlStatus[1],
        },
      },
      {
        label: "Total P&L",
        value:
          pnlStatus[0] +
          "$" +
          new Intl.NumberFormat("en-US", { style: "decimal" }).format(
            Math.abs(accData.pnla)
          ),
        badge: {
          text:
            pnlStatus[0] +
            new Intl.NumberFormat("en-US", { style: "decimal" }).format(
              Math.abs(accData.pnlp)
            ) +
            "%",
          color: pnlStatus[1],
        },
      },
      {
        label: "Annualized Rate of Return",
        value: "20.50%",
      },
    ];

    setFinancials(tmpFinancials);
    setAnalysis(tmpAnalysis);
  }, [accData]);

  if (isAccDataLoading || isPublicPorfoliosLoading) {
    return <Loader />;
  }

  return (
    <div style={{ height: "1600px" }} className="min-w-full">
      <div className="container flex flex-col gap-5 mx-auto p-4 text-black">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <FinancialInfo title="Your Account Financials:" items={financials} />
          <FinancialInfo title="Your Account Analysis:" items={analysis} />
        </div>
        <div className="rounded-md p-4 text-white bg-secondary-100">
          <h2 className="text-2xl font-semibold">Your Latest Portfolios</h2>
        </div>
        <div className="h-86">
          <PortfolioTable portfolios={data} />
        </div>
        <div className="rounded-md p-4 text-white bg-secondary-100">
          <h2 className="text-2xl font-semibold">
            See Portfolios from other Analysts
          </h2>
        </div>
        <div className="h-96">
          <PortfolioCarousel publicPortfolios={publicPortfolios} />
        </div>
      </div>
    </div>
  );
}

export default withAuth(Dashboard);

function FinancialInfo({ title, items, badge }) {
  return (
    <div className="bg-white p-4 rounded shadow flex flex-col pb-10">
      <h2 className="text-xl sm:text-2xl font-semibold mb-4">{title}</h2>
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        {items.map((item, index) => (
          <InfoItem
            key={index}
            label={item.label}
            value={item.value}
            badge={item.badge}
          />
        ))}
      </div>
      {badge && <Badge text={badge.text} color={badge.color} />}
    </div>
  );
}

function InfoItem({ label, value, badge }) {
  return (
    <div className="flex flex-col mb-4 sm:mb-0">
      <span className="text-sm sm:text-md text-gray-400">{label}:</span>
      <span className="text-lg sm:text-2xl">{value}</span>
      {badge && <Badge text={badge.text} color={badge.color} />}
    </div>
  );
}

const Badge = ({ text, color }) => (
  <div className={`badge badge-${color} text-white font-bold`}>{text}</div>
);

function badgeColor(value) {
  if (value >= 0) {
    return ["+", "success"];
  } else {
    return ["-", "error"];
  }
}
