import React from "react";
import { Dna } from "react-loader-spinner";

export default function Loader() {
  return (
    <div className="flex flex-col justify-center items-center h-screen w-screen">
      <Dna
        visible={true}
        height="300"
        width="500"
        ariaLabel="dna-loading"
        wrapperStyle={{}}
        wrapperClass="dna-wrapper"
      />
      <p className="text-xl">Goldman Sachs Portfolio Analyzer</p>
    </div>
  );
}
