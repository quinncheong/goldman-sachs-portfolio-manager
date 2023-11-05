import HoldingsCard from "./HoldingsCard";
import MarketSector from "./MarketSector";

export default function StockHoldings({stockDetails}) {

    function renderStocks() {
        if (!stockDetails) return <div>Stocks Loading</div>;
        const keys = Object.keys(stockDetails)
        return keys.map((key) => {
            const stock = stockDetails[key]
            return <HoldingsCard key={key} stock={stock} />;
        });
    }
    return (
        <div className="grid gap-5 p-5 grid-cols-12">
            <div className="col-span-9 p-2 bg-white rounded-md">
                <div className="h-20">
                    <h1 className="m-3 text-xl font-semibold">Stock Holdings</h1>
                    <div className="w-full flex flex-row items-end min-h-8">
                        <div className="w-[25%] text-xs">
                            <span>Symbol | Name</span>
                        </div>
                        <div className="w-[10%] text-xs">
                            <span>Pos. | Mkt.</span>
                        </div>
                        <div className="w-[10%] text-xs">
                            <span>Last | Cost</span>
                        </div>
                        <div className="w-[14%] text-xs">
                            <span>P&L</span>
                        </div>
                        <div className="w-[14%] text-xs">
                            <span>Daily P&L</span>
                        </div>
                        <div className="w-[10%] text-xs">
                            <span>Asset Var.</span>
                        </div>
                        <div className="w-[10%] text-xs">
                            <span>MC to Risk</span>
                        </div>
                        <div className="w-[7%] text-xs">
                            <span>Positions Ratio</span>
                        </div>
                    </div>
                </div>
                {renderStocks()}
            </div>
            <div className="col-span-3 p-2 bg-white rounded-md">
                <div className="h-20">
                    <h1 className="m-3 text-xl font-semibold">Mkt. Sector Breakdown</h1>
                    <div className="w-full flex flex-row items-end min-h-8">
                        <div className="w-8/12 text-xs">
                            <span>Mkt Sector</span>
                        </div>
                        <div className="w-4/12 text-xs">
                            <span>Sector Ratio</span>
                        </div>
                    </div>
                </div>
                <MarketSector />
                <MarketSector />
            </div>
        </div>
    )
}