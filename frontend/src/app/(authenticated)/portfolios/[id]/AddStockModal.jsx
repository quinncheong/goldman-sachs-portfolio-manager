"use client";

import { useUpdatePortfolio } from "@/api/portfolio";
import { useGetListOfStocks } from "@/api/stock";
import { useState } from "react";

export default function AddStockModal({ portfolio, closeModal, openModal }) {
  const { data, isLoading, isError, error } = useGetListOfStocks();
  const {
    isCreating,
    isSuccessCreating,
    isErrorCreating,
    error: updateError,
    mutate: updatePortfolio,
  } = useUpdatePortfolio();

  const [selectedStock, setSelectedStock] = useState(null);
  const [date, setDate] = useState("");
  const [quantity, setQuantity] = useState(0);

  const handleStockChange = (e) => {
    setSelectedStock(e.target.value);
  };

  const handleDateChange = (e) => {
    setDate(e.target.value);
  };

  const handleQuantityChange = (e) => {
    setQuantity(e.target.value);
  };

  const addStock = (e) => {
    console.log(selectedStock, date, quantity);
    closeModal();
  };

  return (
    <>
      <dialog id="add-stock-modal" className="modal text-white bg-opacity-75">
        <div className="modal-box text-white w-3/4 h-3/6 max-w-5xl">
          <h3 className="font-bold text-lg">Adding Stock</h3>
          <p className="py-4">
            Get started with adding a stock to your portfolio
          </p>
          {modalBody()}
          <div className="mt-auto modal-action">
            <form method="dialog">
              {/* if there is a button, it will close the modal */}
              <button onClick={addStock} className="btn">
                Add
              </button>
            </form>
          </div>
        </div>
      </dialog>
    </>
  );

  function modalBody() {
    return (
      <>
        {formDetails()}
        <h3 className="font-bold text-lg mt-10">Portfolio Specific Details</h3>
        {calculatedOutputs()}
      </>
    );
  }

  function formDetails() {
    return (
      <div className="form-control w-full">
        <div className="flex justify-between w-full">
          <div className="flex flex-col justify-center w-1/2">
            <label className="label">
              <span className="label-text">Select Stock</span>
            </label>
            <select className="select select-bordered max-w-xs">
              <option disabled selected hidden>
                Select a stock
              </option>
              {renderOptions()}
            </select>

            <label className="label mt-3">
              <span className="label-text">Purchased Date</span>
            </label>
            <input
              type="date"
              placeholder="Type here"
              className="input input-bordered w-full max-w-xs"
              onChange={handleDateChange}
            />
          </div>

          <div className="flex flex-col ml-auto w-1/2">
            <label className="label">
              <span className="label-text">select Quantity</span>
            </label>
            <input
              type="number"
              placeholder="Type here"
              className="input input-bordered w-full max-w-xs"
              max={1000}
              min={1}
            />

            <label className="label mt-3">
              <span className="label-text">Single Stock price (EOD Close)</span>
            </label>
            <input
              type="text"
              placeholder="Select a stock to retrieve price"
              className="input input-bordered w-full max-w-xs"
              value={0}
              disabled
            />
          </div>
        </div>
      </div>
    );
  }

  function calculatedOutputs() {
    return (
      <div className="flex justify-between gap-5 w-3/4">
        <div>
          <label className="label mt-3">
            <span className="label-text">Total Stock Price</span>
          </label>
          <input
            type="number"
            placeholder="Type here"
            className="input input-bordered w-full max-w-xs"
            disabled
          />
        </div>

        <div>
          <label className="label mt-3">
            <span className="label-text">Current Cash Balance</span>
          </label>
          <input
            type="number"
            placeholder="Select a stock"
            className="input input-bordered w-full max-w-xs"
            disabled
          />
        </div>

        <div>
          <label className="label mt-3">
            <span className="label-text">Cash Balance after Purchase</span>
          </label>
          <input
            type="number"
            placeholder="Select a stock"
            className="input input-bordered w-full max-w-xs"
            disabled
          />
        </div>
      </div>
    );
  }

  function renderOptions() {
    if (!data) {
      return [];
    }
    return data.map((stock) => (
      <option>{stock.Symbol + " " + stock.Name}</option>
    ));
  }
}
