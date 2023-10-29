export default function HoldingsCard() {
    return (
        <div className="w-full p-2 mb-2 bg-blue-100 rounded-md flex flex-row items-center h-16">
            <div className="w-2/12 font-semibold">
                <span>
                    Apple
                </span>
                <div className="flex flex-row items-center">
                    <div className="w-6 h-6 p-1 bg-blue-600 rounded-md text-white flex items-center justify-center text-xs">US</div>
                    <span className="ml-2 text-gray-500">APPL</span>
                </div>
            </div>
            <div className="w-1/12 font-semibold flex flex-col">
                <span>
                    50
                </span>
                <span className="text-gray-500 text-xs">
                    8779.00
                </span>
            </div>
            <div className="w-1/12 font-semibold flex flex-col">
                <span>
                    178.58
                </span>
                <span className="text-gray-500 text-xs">
                    130.23
                </span>
            </div>
            <div className="w-1/12 font-semibold text-green-600">
                <span>
                    2417.50
                </span>
            </div>
            <div className="w-1/12 font-semibold text-red-500">
                <span>
                    -1.50
                </span>
            </div>
            <div className="w-2/12 font-semibold">
                <span>
                    ???
                </span>
            </div>
            <div className="w-2/12 font-semibold">
                <span>
                    ???
                </span>
            </div>
            <div className="w-2/12 font-semibold">
                <span>
                    35.11%
                </span>
            </div>
        </div>
    )
}