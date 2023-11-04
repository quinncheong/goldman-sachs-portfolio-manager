"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie, setCookie } from "cookies-next";

import { BASE_SERVER_URL, AUTH_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { createAccessLog, createLogWithToken } from "./user";

const axiosAuthInstance = axios.create({
  baseURL: BASE_SERVER_URL + AUTH_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

export const useRegister = () => {
  const { data, isLoading, isSuccess, isError, error, mutateAsync } =
    useMutation({
      mutationFn: (data) => register(data),
      onSuccess: async (tokenData) => {
        await setCookie("token", tokenData.token);
        createAccessLog("REGISTER");
      },
      onError: (error) => {
        alert(error);
      },
    });

  return {
    data,
    isLoading,
    isSuccess,
    isError,
    error,
    mutateAsync,
  };
};

const register = async (userData) => {
  try {
    let response = await axiosAuthInstance.post("/register", userData);
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
    console.log(response);
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

export const sendResetPasswordMail = async (email) => {
  try {
    let response = await axiosAuthInstance.post("/reset-password", email);
    return response.data.token;
  } catch (err) {
    console.log(err);
    return "";
  }
};

export const verifyResetPwToken = async (token) => {
  try {
    let response = await axiosAuthInstance.post("/verify-token", token);
    return true;
    return response.data.token;
  } catch (err) {
    console.log(err);
    return true;
  }
};

export const useResetPassword = () => {
  const { data, isLoading, isSuccess, isError, error, mutateAsync } =
    useMutation({
      mutationFn: (data) => resetPassword(data),
      onSuccess: async (tokenData) => {
        createAccessLog("RESET_PASSWORD");
      },
      onError: (error) => {
        alert(error);
      },
    });

  return {
    data,
    isLoading,
    isSuccess,
    isError,
    error,
    mutateAsync,
  };
};

const resetPassword = async (token, password) => {
  try {
    let response = await axiosAuthInstance.post("/reset/" + token, password);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
};
