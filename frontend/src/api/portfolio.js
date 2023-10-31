import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { BASE_SERVER_URL, PORTFOLIO_API_PATH } from "./apiFactory";

import mockPortfolios from "@data/portfolio.json";

const token = localStorage.getItem("token");
console.log(token);

const axiosInstance = axios.create({
  baseURL: BASE_SERVER_URL + PORTFOLIO_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + token,
  },
});

export const useGetPortfolios = () => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfolios"],
    queryFn: (userId) => getPortfoliosByUserId("quinncheong"),
  });

  return { data, isLoading, isError, error };
};

const getPortfoliosByUserId = async (userId) => {
  // let response = await axiosInstance.get("/user/" + userId);
  let response = await axiosInstance.get("/all");
  return response.data;
};

export const useGetPortfolioByPortfolioId = (portfolioId) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfolioByPortfolioId", portfolioId],
    queryFn: () => getPortfolioByPortfoliId(portfolioId),
  });

  return { data, isLoading, isError, error };
};

const getPortfolioByPortfoliId = async (portfolioId) => {
  if (process.env.NODE_ENV !== "production") {
    return mockPortfolios[portfolioId - 1];
  }
  let response = await axiosInstance.get("/" + "portfolioId");
  return response.data;
};
