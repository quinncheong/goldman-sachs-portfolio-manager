export default function MonetaryAnalysis({analysisData}) {
    console.log(analysisData)
    return (
        <div className="flex sm:flex-row justify-between">
          <div className="flex flex-col mb-4 sm:mb-0">
            <span className="text-sm sm:text-md text-gray-400">
              Daily P&amp;L:
            </span>
            <span className="text-lg sm:text-2xl">{analysisData.dpnla.toFixed(2)}</span>
            <div className={`badge text-white font-bold ${analysisData.dpnla > 0 ? "badge-success" : "badge-error"}`}>{analysisData.dpnl.toFixed(2)}</div>
          </div>
          <div className="flex flex-col mb-4 sm:mb-0">
            <span className="text-sm sm:text-md text-gray-400">
              Total P&amp;L:
            </span>
            <span className="text-lg sm:text-2xl">{analysisData.pnla.toFixed(2)}</span>
            <div className={`badge text-white font-bold ${analysisData.pnla > 0 ? "badge-success" : "badge-error"}`}>{analysisData.pnl.toFixed(2)}</div>
          </div>
          <div className="flex flex-col">
            <span className="text-sm sm:text-md text-gray-400">
              Annualized Rate of Return:
            </span>
            <span className="text-lg sm:text-2xl">20.50%</span>
            <div className="text-sm">
              <span className="text-gray-500">DJIA: </span>
              <span className="text-green-400 font-bold">10.10%</span>
            </div>
          </div>
        </div>
      );
}