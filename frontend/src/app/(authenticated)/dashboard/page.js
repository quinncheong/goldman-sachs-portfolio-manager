"use client";
import {
  useGetPortfoliosOfUser,
  useGetPublicPortfolios,
} from "@api/portfolio.js";
import PortfolioTable from "./PortfolioTable";
import PortfolioCarousel from "./PortfolioCarousel";
import { withAuth } from "@/middleware/authentication";

function Dashboard() {
  const { data, isLoading, isError, error } = useGetPortfoliosOfUser();
  const {
    data: publicPortfolios,
    isLoading: isPublicPorfoliosLoading,
    isError: isPublicPorfoliosError,
    error: publicPortfoliosError,
  } = useGetPublicPortfolios();

  const financials = [
    {
      label: "Total Assets",
      value: "$50,000",
    },
    {
      label: "Total Securities Value",
      value: "$40,000",
    },
    {
      label: "Total Cash Balance",
      value: "$10,000",
    },
  ];

  const analysis = [
    {
      label: "Daily P&L",
      value: "-$800",
      badge: { text: "-1.6%", color: "error" },
    },
    {
      label: "Total P&L",
      value: "$4,000",
      badge: { text: "+5.75%", color: "success" },
    },
    {
      label: "Annualized Rate of Return",
      value: "20.50%",
    },
  ];

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

const FinancialInfo = ({ title, items, badge }) => (
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

const InfoItem = ({ label, value, badge }) => (
  <div className="flex flex-col mb-4 sm:mb-0">
    <span className="text-sm sm:text-md text-gray-400">{label}:</span>
    <span className="text-lg sm:text-2xl">{value}</span>
    {badge && <Badge text={badge.text} color={badge.color} />}
  </div>
);

const Badge = ({ text, color }) => (
  <div className={`badge badge-${color} text-white font-bold`}>{text}</div>
);
