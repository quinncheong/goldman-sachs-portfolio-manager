import { useRemoveStock } from "@/api/portfolio";
import { getMapping } from "./countryMappings.js";

export default function HoldingsCard({ stock, portfolioData }) {
  const {
    isRemoving,
    isSuccessRemoving,
    isErrorRemoving,
    removeError,
    remStock,
  } = useRemoveStock();

  const handleRemoveStock = (e) => {
    e.preventDefault();
    remStock({ portfolioId: portfolioData.id, stockTicker: stock.stockTicker });
  };
  return (
    <div className="w-full p-2 mb-2 bg-blue-100 rounded-md flex flex-row items-center h-16">
      <div className="w-[22%] font-semibold">
        <span>{stock.stockName}</span>
        <div className="flex flex-row items-center">
          <div className="w-6 h-6 p-1 bg-blue-600 rounded-md text-white flex items-center justify-center text-xs">
            {getMapping(stock.country)}
          </div>
          <span className="ml-2 text-gray-500">{stock.stockTicker}</span>
        </div>
      </div>
      <div className="w-[10%] font-semibold flex flex-col">
        <span>{stock.position}</span>
        <span className="text-gray-500 text-xs">{stock.market.toFixed(2)}</span>
      </div>
      <div className="w-[10%] font-semibold flex flex-col">
        <span>{stock.last.toFixed(2)}</span>
        <span className="text-gray-500 text-xs">{stock.cost.toFixed(2)}</span>
      </div>
      <div className="w-[14%] font-semibold flex flex-col">
        <span>{stock.pnla.toFixed(2)}</span>
        <span
          className={`text-xs ${
            stock.pnlp > 0 ? "text-green-600" : "text-red-500"
          }`}
        >
          {stock.pnlp > 0 ? "+" : ""}
          {stock.pnlp.toFixed(2)}%
        </span>
      </div>
      <div className="w-[14%] font-semibold flex flex-col">
        <span>{stock.dpnla.toFixed(2)}</span>
        <span
          className={`text-xs ${
            stock.dpnlp > 0 ? "text-green-600" : "text-red-500"
          }`}
        >
          {stock.dpnlp > 0 ? "+" : ""}
          {stock.dpnlp.toFixed(2)}%
        </span>
      </div>
      <div className="w-[10%] font-semibold">
        <span>???</span>
      </div>
      <div className="w-[10%] font-semibold">
        <span>???</span>
      </div>
      <div className="w-[7%] font-semibold">
        <span>{stock.positionsRatio.toFixed(2)}%</span>
      </div>
      <div className="w-[8%] font-semibold text-right">
        <span>
          <button
            className="btn bg-error p-4 text-white border-0"
            onClick={handleRemoveStock}
          >
            x
          </button>
        </span>
      </div>
    </div>
  );
}
