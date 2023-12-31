"use client";

import { useState } from "react";
import { useUpdatePortfolio } from "@/api/portfolio";

export default function UpdatePortfolioModal({ portfolio, closeModal }) {
  const [description, setDescription] = useState(portfolio.description);
  const [name, setName] = useState(portfolio.name);
  const [status, setStatus] = useState(portfolio.publiclyAccessible);

  const {
    isCreating,
    isSuccessCreating,
    isErrorCreating,
    error: updateError,
    mutate: updatePortfolio,
  } = useUpdatePortfolio();

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const handleStatusChange = (e) => {
    setStatus(e.target.value);
  };

  const update = (e) => {
    e.preventDefault();
    const updated = {
      ...portfolio,
      name: name,
      description: description,
      publiclyAccessible: status,
    };
    updatePortfolio(updated);
    closeModal();
  };

  return (
    <>
      <dialog
        id="update-portfolio-modal"
        className="modal text-white bg-opacity-75"
      >
        <div className="modal-box text-white w-3/4 h-2/6 max-w-5xl">
          <h3 className="font-bold text-lg">Update Portfolio</h3>
          <form method="dialog">
            {/* if there is a button in form, it will close the modal */}
            <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">
              ✕
            </button>
          </form>
          <p className="py-4">Update your portfolio</p>
          {modalBody()}
          <div className="mt-auto modal-action">
            <form method="dialog">
              {/* if there is a button, it will close the modal */}
              <button onClick={update} className="btn">
                Update
              </button>
            </form>
          </div>
        </div>
      </dialog>
    </>
  );

  function modalBody() {
    return <>{formDetails()}</>;
  }

  function formDetails() {
    return (
      <div className="form-control w-full">
        <div className="flex justify-between w-full">
          <div className="flex flex-col justify-center w-1/2">
            <label className="label">
              <span className="label-text">Portfolio Name</span>
            </label>
            <input
              type="text"
              value={name}
              className="input input-bordered w-full max-w-xs"
              onChange={handleNameChange}
            />
            <label className="label mt-3">
              <span className="label-text">Portfolio Description</span>
            </label>
            <input
              type="text"
              value={description}
              className="input input-bordered w-full max-w-xs"
              onChange={handleDescriptionChange}
            />
          </div>

          <div className="flex flex-col ml-auto w-1/2">
            <label className="label">
              <span className="label-text">Visibility</span>
            </label>
            <select
              onChange={handleStatusChange}
              value={status}
              className="select select-bordered max-w-xs"
            >
              <option value={true}>Public</option>
              <option value={false}>Private</option>
            </select>
          </div>
        </div>
      </div>
    );
  }
}
