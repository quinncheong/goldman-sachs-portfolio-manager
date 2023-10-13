"use client";

import Loader from "@/components/loading/Loader";
import { useGetPortfolioByPortfolioId } from "@/api/portfolio";

export default function PortfolioPage({ params }) {
  const { data, isLoading, isError, error } = useGetPortfolioByPortfolioId(
    params.id
  );
  console.log(data);

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
      <p>This is portfolio: {params.id}</p>
    </div>
  );
}
