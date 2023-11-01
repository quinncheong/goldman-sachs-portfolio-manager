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

export const test = (portfolioId) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfolioByPortfolioId", portfolioId],
    queryFn: () => getPortfolioByPortfolioId(portfolioId),
  });

  return { data, isLoading, isError, error };
};

const getPortfolioByPortfolioId = async (portfolioId) => {
  let response = await axiosInstance.get("/" + portfolioId);
  return response.data;
};

export const useCreatePortfolio = () => {
  const queryClient = useQueryClient();
  const {
    isLoading: isCreating,
    isSuccess: isSuccessCreating,
    isError: isErrorCreating,
    error,
    mutate: createNewPortfolio,
  } = useMutation({
    mutationFn: (data) => createPortfolio(data),
    onSuccess: () => {
      queryClient.invalidateQueries(["getPortfoliosOfUser"]);
    },
    onMutate: (variables) => {
      // A mutation is about to happen!
      // Optionally return a context containing data to use when for example rolling back
      // return { id: 1 };
    },
    onError: (error, variables, context) => {
      // An error happened!
      // console.log(`rolling back optimistic update with id ${context.id}`);
      toast.error(error);
    },
    onSuccess: (data, variables, context) => {
      toast.success("Portfolio Created!");
      console.log(data);
    },
    onSettled: (data, error, variables, context) => {
      // Error or success... doesn't matter!
    },
  });

  return {
    isCreating,
    isSuccessCreating,
    isErrorCreating,
    error,
    createNewPortfolio,
  };
};

const createPortfolio = async (partialPortfolioData) => {
  try {
    partialPortfolioData.userId = jwtDecode(getCookie("token")).userId;
    let response = await axiosInstance.post("/", partialPortfolioData);
    return response.data;
  } catch (error) {
    console.log(error);
    return [];
  }
};
