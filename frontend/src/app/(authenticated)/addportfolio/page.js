"use client";

import { useCreatePortfolio } from "@/api/portfolio";
import { jwtDecode } from "jwt-decode";
import { useState } from "react";

export default function AddPortfolio() {
  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
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

  const handleSubmit = (e) => {
    e.preventDefault();
    let portfolioData = {
      userId: "testuser",
      name: name,
      description: desc,
      initialValue: 0,
      allocatedStocks: stocks,
    };

    let token = localStorage.getItem("token");
    let decoded = jwtDecode(token);
    console.log(decoded);

    createNewPortfolio(portfolioData);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div className="min-w-full">
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
