"use client"

import { useState } from "react"

export default function AddPortfolio() {
    const initialBalance = 10000 //dynamically retrieved from api
    const [inputValue, setInputValue] = useState("")
    const [balance, setBalance] = useState(initialBalance)
    const [showWarning, setShowWarning] = useState(false)

    const handleInputChange = (e) => {
        let value = e.target.value
        const decimalParts = value.split(".")

        if (decimalParts.length > 1) {
            const integerPart = decimalParts[0];
            const decimalPart = decimalParts[1].slice(0, 2);
            value = `${integerPart}.${decimalPart}`;
        }
        
        if (value.trim() === "") {
            setBalance(initialBalance)
            setShowWarning(false)
        } else {
            const numericValue = parseFloat(value)
            if (numericValue <= initialBalance && numericValue >= 0) {
                setInputValue(value)
                setBalance(initialBalance - numericValue)
                setShowWarning(false)
            } else {
                setBalance(initialBalance)
                setShowWarning(true)
            }
        }
        setInputValue(value)
    }

    return (
        <div>
            <div className="min-w-full">
                <div className="container mx-auto p-4 text-white">
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text">Portfolio Name</span>
                        </label>
                        <input type="text" placeholder="e.g. Portfolio 1" className="input input-bordered input-md w-full max-w-xs" />
                    </div>
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text">Portfolio Description</span>
                        </label>
                        <textarea placeholder="Description" className="textarea textarea-bordered textarea-md w-full max-w-xs" ></textarea>
                    </div>
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text">Enter Capital</span>
                            <span className="label-text-alt">Balance: ${balance}</span>
                        </label>
                        <label className="input-group">
                            <input type="number" placeholder="0.00" className="input input-bordered [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none" value={inputValue} onChange={handleInputChange} />
                            <span>USD</span>
                        </label>
                        {showWarning && (
                            <label className="label">
                                <span className="label-text-alt text-error">Amount of capital cannot exceed cash balance</span>
                            </label>
                        )}
                    </div>
                    <button className="btn btn-accent mt-5">Create Portfolio</button>
                </div>
            </div>
        </div>
    )
}