"use client";

import { useState } from "react";

export default function AddPortfolio() {
    return (
        <div>
            <div className="min-w-full">
                <div className="container mx-auto p-4">
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text text-black">Portfolio Name</span>
                        </label>
                        <input type="text" placeholder="e.g. Portfolio 1" className="input text-black bg-white input-bordered input-md w-full max-w-xs" />
                    </div>
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text text-black">Portfolio Description</span>
                        </label>
                        <textarea placeholder="Description" className="textarea text-black bg-white textarea-bordered textarea-md w-full max-w-xs" ></textarea>
                    </div>
                    <button className="btn bg-primary-200 border-0 text-white mt-5">Create Portfolio</button>
                </div>
            </div>
        </div>
      </div>
    </div>
  );
}
