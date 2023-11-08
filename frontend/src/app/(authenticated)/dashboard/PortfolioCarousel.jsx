import React from "react";
import PortfolioCarouselCard from "@/components/PortfolioCarouselCard";

export default function PortfolioCarousel({ publicPortfolios }) {
  if (!publicPortfolios || publicPortfolios.length === 0) {
    return "There are no publicly available portfolios";
  }
  return (
    <div className="mx-auto carousel carousel-end rounded-box flex w-full overflow-x-auto">
      <RenderPublicPortfolios publicPortfolios={publicPortfolios} />
    </div>
  );
}

function RenderPublicPortfolios({ publicPortfolios }) {
  return publicPortfolios.map((portfolio, index) => (
    <div key={index} className="carousel-item mr-5">
      <PortfolioCarouselCard portfolio={portfolio} />
    </div>
  ));
}
