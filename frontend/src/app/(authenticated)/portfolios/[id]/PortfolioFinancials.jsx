export default function PortfolioFinancials() {
  return (
    <div className="flex gap-5 p-5 flex-grow">
      <div className="flex flex-col p-4 bg-white ">
        <h2 className="text-xl sm:text-2xl font-semibold mb-4">
          Your Portfolio Financials:
        </h2>
        <div className="flex flex-col sm:flex-row justify-between">
          <div className="flex flex-col mb-4 sm:mb-0">
            <span className="text-sm sm:text-md text-gray-400">
              Total Assets:
            </span>
            <span className="text-lg sm:text-2xl">$25,000</span>
          </div>
          <div className="flex flex-col mb-4 sm:mb-0">
            <span className="text-sm sm:text-md text-gray-400">
              Total Securities Value:
            </span>
            <span className="text-lg sm:text-2xl">$20,000</span>
          </div>
          <div className="flex flex-col">
            <span className="text-sm sm:text-md text-gray-400">
              Total Cash Balance:
            </span>
            <span className="text-lg sm:text-2xl">$5,000</span>
          </div>
        </div>
      </div>
    </div>
  );
}
