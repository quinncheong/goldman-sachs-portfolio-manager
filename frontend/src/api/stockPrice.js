"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie } from "cookies-next";

import { BASE_SERVER_URL, STOCK_PRICE_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const axiosInstance = axios.create({
  baseURL: BASE_SERVER_URL + STOCK_PRICE_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + getCookie("token"),
  },
});

export const useGetHistoricalStockPrice = (symbol = null, date) => {
  const { data, isLoading, isError, error, refetch } = useQuery({
    enabled: false,
    queryKey: ["getHistoricalStockPrice", symbol, date],
    queryFn: () => getHistoricalStockPrice(symbol, date),
    refetchOnWindowFocus: false,
  });

  return { data, isLoading, isError, error, refetch };
};

const getHistoricalStockPrice = async (symbol, date) => {
  try {
    let response = await axiosInstance.get("/" + symbol + "/" + date);
    return response.data;
  } catch (error) {
    console.log(error);
    return "No Price on this Date";
  }
};
