"use client"
import React, { useState, useEffect } from 'react'
import { useGetPortfolios } from "@api/portfolio.js";

export default function portfolio() {
  const { data, isLoading, isError, error } = useGetPortfolios();
  
  if (isLoading) return (<div>Loading...</div>)
  if (isError) return (<div>Error: {error.message}</div>)

  return (
    <>
      <div>This is the portfolio page</div>
    </>
  )
}
