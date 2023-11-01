import HoldingsCard from "./HoldingsCard";
import MarketSector from "./MarketSector";

export default function StockHoldings() {
    return (
        <div className="grid gap-5 p-5 grid-cols-12">
            <div className="col-span-9 p-2 bg-white rounded-md">
                <div className="h-20">
                    <h1 className="m-3 text-xl font-semibold">Stock Holdings</h1>
                    <div className="w-full flex flex-row items-end min-h-8">
                        <div className="w-2/12 text-xs">
                            <span>Symbol | Name</span>
                        </div>
                        <div className="w-1/12 text-xs">
                            <span>Pos. | Mkt.</span>
                        </div>
                        <div className="w-1/12 text-xs">
                            <span>Last | Cost</span>
                        </div>
                        <div className="w-1/12 text-xs">
                            <span>P&L</span>
                        </div>
                        <div className="w-1/12 text-xs">
                            <span>Daily P&L</span>
                        </div>
                        <div className="w-2/12 text-xs">
                            <span>Asset Variance</span>
                        </div>
                        <div className="w-2/12 text-xs pr-3">
                            <span>Marginal Contribution to risk</span>
                        </div>
                        <div className="w-2/12 text-xs">
                            <span>Positions Ratio</span>
                        </div>
                    </div>
                </div>
                <HoldingsCard />
                <HoldingsCard />
                <HoldingsCard />
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