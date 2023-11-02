"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie } from "cookies-next";

import {
  BASE_SERVER_URL,
  PORTFOLIO_API_PATH,
  USER_API_PATH,
} from "./apiFactory";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const axiosInstance = axios.create({
  baseURL: BASE_SERVER_URL + PORTFOLIO_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + getCookie("token"),
  },
});

const axiosUserInstance = axios.create({
  baseURL: BASE_SERVER_URL + USER_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + getCookie("token"),
  },
});

export const useGetPublicPortfolios = () => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPublicPortfolios"],
    queryFn: () => getPublicPortfolios(),
  });

  return { data, isLoading, isError, error };
};

export const getPublicPortfolios = async () => {
  try {
    let response = await axiosInstance.get("/all");
    let final = [];
    if (!response.data) {
      return final;
    }
    return response.data;

    for (let portfolio of response.data) {
      if (portfolio.isPublic) {
        final.push(portfolio);
      }
    }
    return final;
  } catch (error) {
    console.log(error);
    return [];
  }
};

export const useGetPortfoliosOfUser = () => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfoliosOfUser"],
    queryFn: () => getPortfoliosOfUser(),
  });

  return { data, isLoading, isError, error };
};

const getPortfoliosOfUser = async () => {
  try {
    let userId = jwtDecode(getCookie("token")).userId;
    console.log(userId);
    let response = await axiosInstance.get("/user/" + userId);
    return response.data.toReversed();
  } catch (error) {
    console.log(error);
    return [];
  }
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

export const useGetPortfolios = (userId) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfolios", userId],
    queryFn: () => getPortfolios(userId),
  });

  return { data, isLoading, isError, error };
};

const getPortfolios = async (userId) => {
  const userRes = await axiosUserInstance.get("/" + userId);
  const userData = userRes.data;

  const portfolioPromises = userData.portfolioIds.map(async (portfolioId) => {
    const portfolioRes = await axiosInstance.get("/" + portfolioId);
    return portfolioRes.data;
  });

  const portfolioData = await Promise.all(portfolioPromises);

  return { userData, portfolioData };
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

export const useUpdatePortfolio = () => {
  const queryClient = useQueryClient();
  const {
    isLoading: isCreating,
    isSuccess: isSuccessCreating,
    isError: isErrorCreating,
    error,
    mutate,
  } = useMutation({
    mutationFn: (data) => updatePortfolio(data),
    onSuccess: () => {
      queryClient.invalidateQueries(["savePortfolio"]);
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
      toast.success("Portfolio has been saved!");
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
    mutate,
  };
};

const updatePortfolio = async (portfolioData) => {
  try {
    console.log(portfolioData);
    let response = await axiosInstance.put("/", portfolioData);
    return response.data;
  } catch (error) {
    console.log(error);
    return [];
  }
};

export const useDeletePortfolio = () => {
  const queryClient = useQueryClient();
  const {
    isLoading: isDeleteing,
    isSuccess: isSuccessDeleting,
    isError: isErrorDeleting,
    error: deleteError,
    mutate: delPortfolio,
  } = useMutation({
    mutationFn: (data) => deletePortfolio(data),
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
      toast.success("Portfolio Successfully Delete!");
      console.log(data);
    },
    onSettled: (data, error, variables, context) => {
      // Error or success... doesn't matter!
    },
  });

  return {
    isDeleteing,
    isSuccessDeleting,
    isErrorDeleting,
    deleteError,
    delPortfolio,
  };
};

const deletePortfolio = async (portfolioId) => {
  console.log(portfolioId);
  try {
    let response = await axiosInstance.delete("/" + portfolioId);
    return response.data;
  } catch (error) {
    return error;
  }
};
