"use client";
import { useGetPortfoliosOfUser } from "@api/portfolio.js";
import PortfolioCard from "../../../components/PortfolioCard";
import { useRouter } from "next/navigation";
import withAuth from "@/middleware/authentication";

function Portfolio({}) {
  const router = useRouter();
  const {
    data: portfolioData,
    isLoading,
    isError,
    error,
  } = useGetPortfoliosOfUser();

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
          Your Portfolios:
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
    if (!portfolioData) return <div>Portfolios Loading</div>;
    return portfolioData.map((portfolio, index) => {
      return <PortfolioCard key={index} portfolio={portfolio} />;
    });
  }
}

export default withAuth(Portfolio);
