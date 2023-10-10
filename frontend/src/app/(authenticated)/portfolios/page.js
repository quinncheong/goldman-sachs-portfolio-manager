"use client";

import { useGetPortfolios } from "@api/portfolio.js";

export default function Portfolio() {
  const { data, isLoading, isError, error } = useGetPortfolios();

  if (isLoading) return <div>Loading...</div>;
  if (isError) return <div>Error: {error.message}</div>;

  const renderPortfolios = () => {
    if (!data) return <div>Portfolios Loading</div>;
    console.log(data);
    return data.map((portfolio, index) => {
      return (
        <div key={index}>
          <div>{portfolio.name}</div>
          <div>{portfolio.description}</div>
        </div>
      );
    });
  };

  return (
    <>
      <div>{renderPortfolios()}</div>
    </>
  );
}
