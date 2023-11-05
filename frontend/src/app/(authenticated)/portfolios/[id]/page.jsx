"use client";

import { useRouter } from "next/navigation";
import {
  useDeletePortfolio,
  useGetAnalysis,
  useGetPortfolioByPortfolioId,
  useGetStockData,
  useGetStockDetails,
} from "@/api/portfolio";

import Loader from "@/components/loading/Loader";
import IsPublicBadge from "@/components/IsPublicBadge";
import PortfolioFinancials from "./PortfolioFinancials";
import PortfolioAnalysis from "./PortfolioAnalysis";
import StockHoldings from "./StockHoldings";
import AddStockModal from "./AddStockModal";
import UpdatePortfolioModal from "./UpdatePortfolioModal";
import { getCookie } from "cookies-next";
import { jwtDecode } from "jwt-decode";
import withAuth from "@/middleware/authentication";

function PortfolioPage({ params }) {
  const router = useRouter();
  const {
    data: portfolio,
    isLoading,
    isError,
    error,
  } = useGetPortfolioByPortfolioId(params.id);

  const {
    data: stockDetails,
    isLoading: stockDetailsLoading,
    isError: stockDetailsIsError,
    error: stockDetailsError,
  } = useGetStockDetails(params.id);

  const {
    data: stockData,
    isLoading: stockDataIsLoading,
    isError: stockDataIsError,
    error: stockDataError
  } = useGetStockData(params.id);

  const {
    data: analysisData,
    isLoading: analysisIsLoading,
    isError: analysisIsError,
    error: analysisError
  } = useGetAnalysis(params.id);


  function openModal() {
    document.getElementById("add-stock-modal").showModal()
  }

  function closeModal() {
    document.getElementById("add-stock-modal").close()
  }

  function openUpdateModal() {
    document.getElementById("update-portfolio-modal").showModal()
  }

  function closeUpdateModal() {
    document.getElementById("update-portfolio-modal").close()
  }

  const {
    isDeleteing,
    isSuccessDeleting,
    isErrorDeleting,
    deleteError,
    delPortfolio,
  } = useDeletePortfolio();



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

  const handleDeletePortfolio = (e) => {
    e.preventDefault()
    delPortfolio(params.id)
    router.push("/portfolios")
  }

  return (
    <div className="container mx-auto p-4 text-black">
      <div className="flex flex-row justify-between items-center">
        <div className="container p-4 text-black">
          <h2 className="text-4xl font-semibold border-none outline-none w-fit">
            {portfolio.name}
          </h2>
          <p className="text-xl border-none outline-none w-fit">{portfolio.description}</p>
          <p className="text-xl mt-2">
            This Porfolio is:{" "}
            <IsPublicBadge isPublic={portfolio.publiclyAccessible || false} />
          </p>
        </div>
        <RenderButtonsWithAccessControl portfolioUserId={portfolio.userId} />
      </div>

      <div className="rounded-md p-4 text-white bg-secondary-100">
        <h2 className="text-2xl font-semibold">Portfolio Analysis</h2>
      </div>
      <PortfolioAnalysis
        stockData={stockData}
        stockDetails={stockDetails}
        analysisData={analysisData}
      />

      <div className="rounded-md p-4 text-white bg-secondary-100">
        <h2 className="text-2xl font-semibold">Holdings</h2>
      </div>
      <StockHoldings 
        stockDetails={stockDetails}
        stockData={stockData}
      />

      <AddStockModal
        portfolio={portfolio}
        openModal={openModal}
        closeModal={closeModal}
      />

      <UpdatePortfolioModal
        portfolio={portfolio}
        openModal={openUpdateModal}
        closeModal={closeUpdateModal}
      />
    </div>
  );


  function RenderButtonsWithAccessControl({ portfolioUserId }) {
    let userId = jwtDecode(getCookie("token")).userId;
    if (!(userId === portfolioUserId)) {
      return;
    }

    return (
      <>
        <button
          className="btn btn-error p-4 text-white border-0"
          onClick={handleDeletePortfolio}
        >
          Delete Portfolio
        </button>
        <button
          className="btn btn-info p-4 text-white border-0"
          onClick={openUpdateModal}
        >
          Edit Portfolio
        </button>
        <button
          className="btn bg-primary-200 p-4 text-white border-0"
          onClick={openModal}
        >
          Add Stocks
        </button>
      </>
    );
  }
}

export default withAuth(PortfolioPage);
