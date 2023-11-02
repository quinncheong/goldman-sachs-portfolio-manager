import IsPublicBadge from "@/components/IsPublicBadge";
import { useRouter } from "next/navigation";

export default function PortfolioCarouselCard({ portfolio }) {
  const router = useRouter();

  const viewPortfolioDetails = () => {
    router.push(`/portfolios/${portfolio.id}`);
  };

  return (
    <div className="card w-96 glass flex flex-col items-center">
      <CardAvatar />
      <div className="card-body">
        <h2 className="card-title">Life hack</h2>
        <p>How to park your car at your garage?</p>
        <div className="card-actions justify-end">
          <button className="btn btn-primary">Learn now!</button>
        </div>
      </div>
    </div>
  );

  return (
    <div className="card glass bg-secondary-100 flex flex-col justify-items-center">
      <figure>
        <img src="/images/face1.jpg" alt="car!" />
      </figure>
      <div className="card-title">This Porfolio is owned by</div>
      <div className="card-body text-black">
        <div className="flex flex-row justify-between">
          <div>
            <h2 className="card-title">{portfolio.name}</h2>
            <p>
              <span className="text-gray-400">Total Assets: </span>
              <span className="font-bold text-gray-700">$25,000</span>
            </p>
            <p className="pt-2">{portfolio.description}</p>
            <p className="text-xl mt-2">
              This Porfolio is:{" "}
              <IsPublicBadge isPublic={portfolio.isPublic || false} />
            </p>
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

function CardAvatar() {
  const imageSrc =
    "/images/face" + (Math.floor(Math.random() * 4) + 1) + ".jpg";
  const number = Math.floor(Math.random() * 10);

  return number < 5 ? (
    <div className="avatar offline">
      <div className="w-24 rounded-full">
        <img src={imageSrc} />
      </div>
    </div>
  ) : (
    <div className="avatar online">
      <div className="w-24 rounded-full">
        <img src={imageSrc} />
      </div>
    </div>
  );
}
