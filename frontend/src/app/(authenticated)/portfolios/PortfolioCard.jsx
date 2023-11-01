import { useRouter } from "next/navigation";

export default function PortfolioCard({ portfolio }) {
  const router = useRouter();

  const viewPortfolioDetails = () => {
    router.push(`/portfolios/${portfolio.id}`);
  };

  return (
    <div className="card w-[49%] mb-5 bg-white shadow-xl">
      <div className="card-body text-black">
        <div className="flex flex-row justify-between">
          <div>
            <h2 className="card-title">{portfolio.name}</h2>
            <p>
              <span className="text-gray-400">Total Assets: </span>
              <span className="font-bold text-gray-700">$25,000</span>
            </p>
            <p className="pt-2">{portfolio.description}</p>
          </div>
          <div>
            <div className="card-actions flex flex-col items-end">
              <div>
                <span>Daily P&L: </span>
                <div className="badge badge-error text-white font-bold">
                  -0.50%
                </div>
              </div>
              <div>
                <span>Total P&L: </span>
                <div className="badge badge-success text-white font-bold">
                  +11.07%
                </div>
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
