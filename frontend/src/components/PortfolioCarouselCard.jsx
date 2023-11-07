import { useGetAccountIdData, useGetUser } from "@/api/user";
import { useGetROROfPortfolio } from "@/api/portfolio";
import { useRouter } from "next/navigation";
import Loader from "@/components/loading/Loader";
import { jwtDecode } from "jwt-decode";
import { getCookie } from "cookies-next";

export default function PortfolioCarouselCard({ portfolio }) {
  const router = useRouter();
  let totalAssets;
  const viewPortfolioDetails = () => {
    router.push(`/portfolios/${portfolio.id}`);
  };
  const {
    data: userData,
    isLoading,
    isError,
    error,
  } = useGetAccountIdData(portfolio.userId);
  const {
    data: userObjData,
    userisLoading,
    userisError,
    userError,
  } = useGetUser(portfolio.userId);

  // const { data: ror, isLoading: rorLoading, isError: isRorError, error: rorError} = useGetROROfPortfolio(portfolio.id);
  const ror = 30;

  if (portfolio.userId == jwtDecode(getCookie("token")).userId) {
    return <></>
  }

  if (isLoading) {
    return <Loader />;
  }

  if (userisLoading) {
    return <Loader />;
  }

  if (userData) {
    totalAssets = new Intl.NumberFormat("en-US", { style: "decimal" }).format(
      userData.totalAssets.toFixed(2)
    );
  }

  return (
    <div className="card w-100 glass flex flex-col items-center">
      <CardAvatar />
      <div className="card-body items-center">
        <h2 className="card-title">
          Portfolio Owner:{" "}
          <span className="font-normal">{userObjData.name}</span>
        </h2>
        <h2 className="card-title">
          Title: <span className="font-normal">{portfolio.name}</span>
        </h2>
        <h2 className="card-title">
          Description:{" "}
          <span className="font-normal">{portfolio.description}</span>
        </h2>
        <h2 className="card-title mt-3">Portfolio Statistics</h2>
        <div className="container flex border-double border-4 border-black rounded p-3">
          <div className="card-actions flex flex-col items-end">
            <div>
              <p>
                <span className="text-gray-400">Total Assets: </span>
                <span className="font-bold text-gray-700">${totalAssets}</span>
              </p>
            </div>
            <div>
              <span>Daily P&L: </span>
              <div
                className={`badge text-white font-bold ${
                  userData.dpnlp > 0 ? "badge-success" : "badge-error"
                }`}
              >
                {userData.dpnlp > 0 ? "+" : ""}
                {userData.dpnlp.toFixed(2)}%
              </div>
            </div>
            <div>
              <span>Total P&L: </span>
              <div
                className={`badge text-white font-bold ${
                  userData.pnlp > 0 ? "badge-success" : "badge-error"
                }`}
              >
                {userData.pnlp > 0 ? "+" : ""}
                {userData.pnlp.toFixed(2)}%
              </div>
            </div>
            <div>
              <span>RoR: </span>
              <div className={`badge text-white font-bold ${ror > 0 ? "badge-success" : "badge-error"}`}>{ror > 0 ? "+" : ""}{ror.toFixed(2)}%</div>
            </div>
          </div>
        </div>
        <div className="card-actions mt-3 justify-end">
          <button
            onClick={viewPortfolioDetails}
            className="btn bg-secondary-100 text-white"
          >
            View Portfolio!
          </button>
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
        <div className="flex flex-row justify-between"></div>
        <button className="btn bg-secondary-100 text-white">
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
    <div className="avatar offline mt-5">
      <div className="w-24 rounded-full">
        <img src={imageSrc} />
      </div>
    </div>
  ) : (
    <div className="avatar online mt-5">
      <div className="w-24 rounded-full">
        <img src={imageSrc} />
      </div>
    </div>
  );
}
