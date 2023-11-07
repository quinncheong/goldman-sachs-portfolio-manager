"use client";

import { useState, useEffect } from "react";
import { useUpdatePortfolio } from "@/api/portfolio";
import { useGetListOfStocks } from "@/api/stock";
import { useGetHistoricalStockPrice } from "@/api/stockPrice";
import { toast } from "react-toastify";

export default function AddStockModal({ portfolio, closeModal, openModal }) {
  const [selectedStockSymbol, setSelectedStockSymbol] = useState("default");
  const [date, setDate] = useState("");
  const [quantity, setQuantity] = useState(0);
  const [totalPrice, setTotalPrice] = useState(0);
  const [cashBalance, setCashBalance] = useState();

  const { data, isLoading, isError, error } = useGetListOfStocks();
  const {
    data: historicalStockPrice,
    isLoading: stockPriceLoading,
    isError: stockPriceIsError,
    error: stockPriceError,
    refetch: refetchStockPrice,
  } = useGetHistoricalStockPrice(selectedStockSymbol, date);

  const {
    isCreating,
    isSuccessCreating,
    isErrorCreating,
    error: updateError,
    mutate: updatePortfolio,
  } = useUpdatePortfolio();

  useEffect(() => {
    handleGetHistoricalStockPrice();
  }, [selectedStockSymbol, date]);

  const handleStockChange = (e) => {
    setSelectedStockSymbol(e.target.value);
  };

  const handleDateChange = (e) => {
    setDate(e.target.value);
  };

  const handleQuantityChange = (e) => {
    setQuantity(e.target.value);
    if (e.target.value && historicalStockPrice !== null) {
      let tmpPrice = (
        e.target.value * historicalStockPrice?.adjustedClose
      ).toFixed(2);

      setTotalPrice(tmpPrice);
      if (portfolio.initialValue < 1000) {
        portfolio.initialValue = 1000;
      }
      setCashBalance((portfolio.initialValue - tmpPrice).toFixed(2));
    }
  };

  const handleGetHistoricalStockPrice = () => {
    if (!selectedStockSymbol || !date) {
      return;
    }
    refetchStockPrice();
  };

  const addStock = (e) => {
    e.preventDefault();
    if (cashBalance < 0 || totalPrice > portfolio.initialValue) {
      toast.warn("You cannot have a negative portfolio balance");
    } else if (quantity < 1) {
      toast.warn("You must select at least 1 stock");
    } else {
      let newAllocatedStock = {
        stockTicker: selectedStockSymbol,
        stockQuantity: quantity,
        stockBuyPrice: historicalStockPrice.adjustedClose,
        stockBuyDate: date,
      };
      portfolio.allocatedStocks.push(newAllocatedStock);
      const updated = { ...portfolio, initialValue: cashBalance };
      updatePortfolio(updated);
      closeModal();
    }
  };

  return (
    <>
      <dialog id="add-stock-modal" className="modal text-white bg-opacity-75">
        <div className="modal-box text-white w-3/4 h-3/6 max-w-5xl">
          <h3 className="font-bold text-lg">Adding Stock</h3>
          <form method="dialog">
            {/* if there is a button in form, it will close the modal */}
            <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">
              ✕
            </button>
          </form>
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
            <select
              onChange={handleStockChange}
              onBlur={handleGetHistoricalStockPrice}
              value={selectedStockSymbol}
              className="select select-bordered max-w-xs"
            >
              <option value={"default"} disabled hidden>
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
              onBlur={handleGetHistoricalStockPrice}
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
              onChange={handleQuantityChange}
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
              value={
                historicalStockPrice?.adjustedClose?.toFixed(2) ||
                historicalStockPrice
              }
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
            <span className="label-text">Total Price</span>
          </label>
          <input
            type="number"
            placeholder="Type here"
            className="input input-bordered w-full max-w-xs"
            value={totalPrice}
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
            value={portfolio.initialValue || 1000}
          />
        </div>

        <div>
          <label className="label mt-3">
            <span className="label-text">Cash Balance after Purchase</span>
          </label>
          <input
            type="text"
            placeholder="Select a stock"
            className="input input-bordered w-full max-w-xs"
            value={cashBalance}
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
    return data.map((stock, index) => (
      <option key={index} value={stock.Symbol}>
        {stock.Symbol + " " + stock.Name}
      </option>
    ));
  }
}
