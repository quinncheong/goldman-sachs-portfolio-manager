export default function HoldingsCard({ stock }) {
    console.log(stock)
    return (
        <div className="w-full p-2 mb-2 bg-blue-100 rounded-md flex flex-row items-center h-16">
            <div className="w-[25%] font-semibold">
                <span>
                    {stock.stockName}
                </span>
                <div className="flex flex-row items-center">
                    <div className="w-6 h-6 p-1 bg-blue-600 rounded-md text-white flex items-center justify-center text-xs">{stock.country}</div>
                    <span className="ml-2 text-gray-500">{stock.stockTicker}</span>
                </div>
            </div>
            <div className="w-[10%] font-semibold flex flex-col">
                <span>
                    {stock.position}
                </span>
                <span className="text-gray-500 text-xs">
                    {stock.market}
                </span>
            </div>
            <div className="w-[10%] font-semibold flex flex-col">
                <span>
                    {stock.last}
                </span>
                <span className="text-gray-500 text-xs">
                    {stock.cost}
                </span>
            </div>
            <div className={`w-[14%] font-semibold ${stock.pnla > 0 ? "text-green-600" : "text-red-500"}`}>
                <span>{stock.pnla}</span>
            </div>
            <div className={`w-[14%] font-semibold ${stock.dpnla > 0 ? "text-green-600" : "text-red-500"}`}>
                <span>{stock.dpnla}</span>
            </div>
            <div className="w-[10%] font-semibold">
                <span>
                    ???
                </span>
            </div>
            <div className="w-[10%] font-semibold">
                <span>
                    ???
                </span>
            </div>
            <div className={`w-[7%] font-semibold ${stock.positionsRatio > 0 ? "text-green-600" : "text-red-500"}`}>
                <span>
                    {stock.positionsRatio}%
                </span>
            </div>
        </div>
    )
}