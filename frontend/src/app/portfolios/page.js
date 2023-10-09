"use client"
import React, { useState, useEffect } from 'react'
import { getPortfolios } from "@api/portfolio.js";

export default function portfolio() {
  const [portfolios, setPortfolios] = useState([])

  useEffect(() => {
    let portfolios = getPortfolios();
    console.log(portfolios);
    setPortfolios(portfolios);
  }, [])
  

  return (
    <>
      <div>This is the portfolio page</div>
    </>
  )
}
