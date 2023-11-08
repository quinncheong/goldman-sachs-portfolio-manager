"use client";

import { useState } from "react";
import { useCreatePortfolio } from "@/api/portfolio";
import { redirect } from "next/navigation";

export default function AddPortfolio() {
  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [initialValue, setInitialValue] = useState("");
  const [stocks, setStocks] = useState([]);

  const {
    isCreating,
    isSuccessCreating,
    isErrorCreating,
    error,
    createNewPortfolio,
  } = useCreatePortfolio();

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleDescChange = (e) => {
    setDesc(e.target.value);
  };

  const handleInitialValueChange = (e) => {
    setInitialValue(parseFloat(e.target.value));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let portfolioData = {
      name: name,
      description: desc,
      initialValue: initialValue,
      allocatedStocks: stocks,
    };

    createNewPortfolio(portfolioData);
  };

  if (isSuccessCreating) {
    redirect("/portfolios");
  }

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div className="min-w-full h-[100vh]">
          <div className="container mx-auto p-4">
            <div className="form-control w-full max-w-xs">
              <label className="label">
                <span className="label-text text-black">Portfolio Name</span>
              </label>
              <input
                type="text"
                placeholder="e.g. Portfolio 1"
                className="input text-black bg-white input-bordered input-md w-full max-w-xs"
                onChange={handleNameChange}
              />
            </div>
            <div className="form-control w-full max-w-xs">
              <label className="label">
                <span className="label-text text-black">
                  Portfolio Description
                </span>
              </label>
              <textarea
                placeholder="Description"
                className="textarea text-black bg-white textarea-bordered textarea-md w-full max-w-xs"
                onChange={handleDescChange}
              ></textarea>
            </div>
            <div className="form-control w-full max-w-xs">
              <label className="label">
                <span className="label-text text-black">
                  Initial Value (USD$)
                </span>
              </label>
              <input
                type="text"
                placeholder="e.g. 100000"
                className="input text-black bg-white input-bordered input-md w-full max-w-xs"
                onChange={handleInitialValueChange}
              />
            </div>
            <button
              type="submit"
              className="btn bg-primary-200 border-0 text-white mt-5"
            >
              Create Portfolio
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
