import axios from "axios";
import { redirect } from "next/navigation";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { BASE_SERVER_URL, PORTFOLIO_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { useId } from "react";

const token = localStorage.getItem("token");

const axiosInstance = axios.create({
  baseURL: BASE_SERVER_URL + PORTFOLIO_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + token,
  },
});

export const useGetPortfoliosByUserId = (userId) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfoliosByUserId"],
    queryFn: () => getPortfoliosByUserId(userId),
  });

  return { data, isLoading, isError, error };
};

const getPortfoliosByUserId = async (userId) => {
  let response = await axiosInstance.get("/user/" + userId);
  return response.data.toReversed();
};

export const useGetPortfolioByPortfolioId = (portfolioId) => {
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
      queryClient.invalidateQueries(["getPortfoliosByUserId"]);
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

const createPortfolio = async (data) => {
  let response = await axiosInstance.post("/", data);
  return response.data;
};
