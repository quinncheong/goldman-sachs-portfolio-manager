"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie } from "cookies-next";

import { BASE_SERVER_URL, AUTH_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const axiosAuthInstance = axios.create({
  baseURL: BASE_SERVER_URL + AUTH_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

export const useRegister = () => {
  const { data, isLoading, isSuccess, isError, error, mutate } = useMutation({
    mutationFn: (data) => register(data),
  });

  return {
    data,
    isLoading,
    isSuccess,
    isError,
    error,
    mutate,
  };
};

const register = async (userData) => {
  try {
    let response = await axiosAuthInstance.post("/register", userData);
    console.log(response);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
};

export const login = async (username, password) => {
  const json = {
    username: username,
    password: password,
  };
  try {
    let response = await axiosAuthInstance.post("/authenticate", json);
    return response.data.token;
  } catch (err) {
    if (err.response.status === 403) {
      console.log("Wrong username/password.");
      return 403;
    } else {
      console.log("Something went wrong.");
    }
  }
};

export const useGetLoginStatus = () => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getPortfolios"],
    queryFn: () => getLoginStatus(),
  });

  return { data, isLoading, isError, error };
};

const getLoginStatus = async () => {
  let isLoggedIn = false;
  if (process.env.NODE_ENV === "production") {
    let response = await axiosAuthInstance.get("/all");
    isLoggedIn = response.data;
  }

  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve(isLoggedIn);
    }, 1000);
  });
};
