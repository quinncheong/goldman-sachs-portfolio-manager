import { useGetAnalysis } from "@/api/portfolio";
import IsPublicBadge from "@/components/IsPublicBadge";
import { useRouter } from "next/navigation";

export default function PortfolioCard({ portfolio }) {
  const {
    data: analysisData,
    isLoading: analysisIsLoading,
    isError: analysisIsError,
    error: analysisError
  } = useGetAnalysis(portfolio.id);

  const router = useRouter();

  const viewPortfolioDetails = () => {
    router.push(`/portfolios/${portfolio.id}`);
  };

  if (!analysisData)
    return <div>Portfolio Loading</div>

  const totalAssets = new Intl.NumberFormat('en-US', {
    style: 'decimal',
  }).format(analysisData.value);

  return (
    <div className="card w-[49%] mb-5 bg-white shadow-xl">
      <div className="card-body text-black">
        <div className="flex flex-row justify-between">
          <div>
            <h2 className="card-title">{portfolio.name}</h2>
            <p>
              <span className="text-gray-400">Total Assets: </span>
              <span className="font-bold text-gray-700">${totalAssets}</span>
            </p>
            <p className="pt-2">{portfolio.description}</p>
            <p className="text-xl mt-2">
              This Porfolio is:{" "}
              <IsPublicBadge isPublic={portfolio.publiclyAccessible || false} />
            </p>
          </div>
          <div>
            <div className="card-actions flex flex-col items-end">
              <div>
                <span>Daily P&L: </span>
                <div className={`badge text-white font-bold ${analysisData.dpnl > 0 ? "badge-success" : "badge-error"}`}>{analysisData.dpnl > 0 ? "+" : ""}{analysisData.dpnl.toFixed(2)}%</div>
              </div>
              <div>
                <span>Total P&L: </span>
                <div className={`badge text-white font-bold ${analysisData.pnl > 0 ? "badge-success" : "badge-error"}`}>{analysisData.pnl > 0 ? "+" : ""}{analysisData.pnl.toFixed(2)}%</div>
              </div>
              <div>
                <span>RoR: </span>
                <div className="badge badge-success text-white font-bold">
                  +22.14%
                </div>
              </div>
            </div>
          </div>
        </div>
        <button
          className="btn bg-secondary-100 text-white"
          onClick={viewPortfolioDetails}
        >
          View Portfolio Details
        </button>
      </div>
    </div>
  );
}
