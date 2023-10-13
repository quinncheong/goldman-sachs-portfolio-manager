import { useRouter } from "next/navigation";

export default function PortfolioCard({ portfolio }) {
  const router = useRouter();

  const viewPortfolioDetails = () => {
    console.log("Viewing portfolio details");
    router.push(`/portfolios/${portfolio.id}`);
  };

  return (
    <div className="card w-96 bg-base-100 shadow-xl">
      <div className="card-body">
        <h2 className="card-title">
          {portfolio.name}
          <div className="badge badge-secondary">{"Profit"}</div>
        </h2>
        <p>{portfolio.description}</p>
        <div className="card-actions justify-end">
          <div className="badge badge-outline">DJIA</div>
          <div className="badge badge-outline badge-success">PNL</div>
        </div>
        <button className="btn btn-accent" onClick={viewPortfolioDetails}>
          View Portfolio Details
        </button>
      </div>
    </div>
  );
}
