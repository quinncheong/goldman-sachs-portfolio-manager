"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie } from "cookies-next";

import { BASE_SERVER_URL, STOCK_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const axiosInstance = axios.create({
  baseURL: BASE_SERVER_URL + STOCK_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + getCookie("token"),
  },
});

export const useGetListOfStocks = () => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getListOfStocks"],
    queryFn: () => getStocks(),
  });

  return { data, isLoading, isError, error };
};

const getStocks = async () => {
  try {
    let response = await axiosInstance.get("/all");
    return response.data;
  } catch (error) {
    console.log(error);
    return [];
  }
};
