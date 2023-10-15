"use client"

import { useState } from "react"
import Loader from "@/components/loading/Loader";
import { useGetPortfolios } from "@/api/portfolio";

export default function AddStock() {
    const { data, isLoading, isError, error } = useGetPortfolios();
    const [selectedPortfolio, setSelectedPortfolio] = useState(null)
    const [selectedStock, setSelectedStock] = useState(null)

    const handlePortfolioChange = (e) => {
        const portfolioId = parseInt(e.target.value)
        const selectedPortfolioObject = data.find((portfolio) => portfolio.id === portfolioId)
        setSelectedStock(null)
        setSelectedPortfolio(selectedPortfolioObject)
    }

    const handleStockChange = (e) => {
        setSelectedStock(e.target.value)
    }
    if (isLoading) {
        return <Loader />;
    }

    if (isError) {
        return (
            <div className="flex min-h-screen items-center justify-between p-24">
                <p>Error: {error}</p>
            </div>
        );
    }

    return (
        <div>
            <div className="min-w-full">
                <div className="container mx-auto p-4 text-white">
                    <div className="form-control w-full max-w-sm">
                        <label className="label">
                            <span className="label-text">Select Portfolio</span>
                        </label>
                        <select className="select select-bordered" onChange={handlePortfolioChange}>
                            <option disabled selected>Select Portfolio</option>
                            {data.map((portfolio) => (
                                <option key={portfolio.id} value={portfolio.id}>
                                    {portfolio.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-control w-full max-w-sm">
                        <label className="label">
                            <span className="label-text">Select Stock</span>
                        </label>
                        <select className="select select-bordered" onChange={handleStockChange}>
                            <option disabled selected={!selectedStock}>Select Stock</option>
                            {selectedPortfolio &&
                                selectedPortfolio.stocks.map((stock, index) => (
                                    <option key={index} value={stock.name}>
                                        {stock.name}
                                    </option>
                                ))}
                        </select>
                    </div>
                    <div className="form-control max-w-xs">
                        <label className="label">
                            <span className="label-text">Purchased Stock Price</span>
                        </label>
                        <input type="number" placeholder="Min Limit 0.01" className="input input-bordered [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none" />
                    </div>
                    <div className="form-control max-w-xs">
                        <label className="label">
                            <span className="label-text">Purchased Stock Quantity</span>
                        </label>
                        <input type="number" placeholder="Min Unit 1" className="input input-bordered [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none" />
                    </div>
                    <button className="btn btn-accent mt-5">Add to Portfolio</button>
                </div>
            </div>
        </div>
    )
}