export default function MonetaryAnalysis({ analysisData }) {
    return (
        <div className="flex sm:flex-row justify-between">
            <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">
                    Daily P&amp;L:
                </span>
                <span className="text-lg sm:text-2xl">{analysisData.dpnla > 0 ? "+" : ""}{analysisData.dpnla.toFixed(2)}</span>
                <div className={`badge text-white font-bold ${analysisData.dpnl > 0 ? "badge-success" : "badge-error"}`}>{analysisData.dpnl > 0 ? "+" : ""}{analysisData.dpnl.toFixed(2)}</div>
            </div>
            <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">
                    Total P&amp;L:
                </span>
                <span className="text-lg sm:text-2xl">{analysisData.pnla > 0 ? "+" : ""}{analysisData.pnla.toFixed(2)}</span>
                <div className={`badge text-white font-bold ${analysisData.pnl > 0 ? "badge-success" : "badge-error"}`}>{analysisData.pnl > 0 ? "+" : ""}{analysisData.pnl.toFixed(2)}</div>
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