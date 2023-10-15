import Image from 'next/image'
import Portfolio from '../portfolios/page'

export default function Dashboard() {
  return (
    <div className="min-w-full">
      <div className="container mx-auto p-4 text-white">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div className="bg-gray-700 p-4 rounded shadow flex flex-col pb-10">
            <h2 className="text-xl sm:text-2xl font-semibold mb-4">Your Account Financials:</h2>
            <div className="flex flex-col sm:flex-row justify-between">
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Total Assets:</span>
                <span className="text-lg sm:text-2xl">$50,000</span>
              </div>
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Total Securities Value:</span>
                <span className="text-lg sm:text-2xl">$40,000</span>
              </div>
              <div className="flex flex-col">
                <span className="text-sm sm:text-md text-gray-400">Total Cash Balance:</span>
                <span className="text-lg sm:text-2xl">$10,000</span>
              </div>
            </div>
          </div>
          <div className="bg-gray-700 p-4 rounded shadow flex flex-col pb-10">
            <h2 className="text-xl sm:text-2xl font-semibold mb-4">Your Account Analysis:</h2>
            <div className="flex flex-col sm:flex-row justify-between">
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Daily P&amp;L:</span>
                <span className="text-lg sm:text-2xl">-$800</span>
                <div className="badge badge-error">-1.6%</div>
              </div>
              <div className="flex flex-col mb-4 sm:mb-0">
                <span className="text-sm sm:text-md text-gray-400">Total P&amp;L:</span>
                <span className="text-lg sm:text-2xl">$4,000</span>
                <div className="badge badge-success">+5.75%</div>
              </div>
              <div className="flex flex-col">
                <span className="text-sm sm:text-md text-gray-400">Annualized Rate of Return:</span>
                <span className="text-lg sm:text-2xl">20.50%</span>
                <div className="text-sm">
                  <span className="text-gray-500">DJIA: </span>
                  <span className="text-green-400">10.10%</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Portfolio />
    </div>
  )
}
