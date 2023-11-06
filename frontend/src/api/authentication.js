"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie, setCookie } from "cookies-next";

import { BASE_SERVER_URL, AUTH_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { createAccessLog, createLogWithToken } from "./user";
import { jwtDecode } from "jwt-decode";

const axiosAuthInstance = axios.create({
  baseURL: BASE_SERVER_URL + AUTH_API_PATH,
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  },
});

export const useRegister = () => {
  const { data, isLoading, isSuccess, isError, error, mutateAsync } =
    useMutation({
      mutationFn: (data) => register(data),
      onSuccess: async (tokenData) => {
        console.log(tokenData);
        createLogWithToken(tokenData.token, "REGISTER");
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

export const verifyRegisteredUser = async (token) => {
  try {
    let response = await axiosAuthInstance.post(
      "/register/verification/" + token
    );
    console.log(response);
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
};

export const useLogin = () => {
  const { data, isLoading, isSuccess, isError, error, mutateAsync } =
    useMutation({
      mutationFn: (data) => login(data),
      onSuccess: async (data) => {
        setCookie("token", data.token);
        createAccessLog("LOGIN");
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

export const login = async ({ username, password }) => {
  const json = {
    username: username,
    password: password,
  };

  let res = {
    isVerified: false,
    token: "",
  };

  try {
    let response = await axiosAuthInstance.post("/authenticate", json);
    console.log(response);
    res.token = response.data.token;
    res.isVerified = response.data.verified;
  } catch (err) {
    console.log(err);
  }

  return res;
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
    let response = await axiosAuthInstance.post("/password/reset/email", email);
    console.log(response);
    return response.data.token;
  } catch (err) {
    console.log(err);
    return "";
  }
};

export const verifyJWT = async (token) => {
  try {
    console.log("Verifying JWT: " + token);
    jwtDecode(token);
    let response = await axiosAuthInstance.get("/verify/" + token);
    console.log(response);
    if (response.data) {
      return true;
    }
    return false;
  } catch (err) {
    console.log(err);
    return false;
  }
};

export const useResetPassword = () => {
  const { data, isLoading, isSuccess, isError, error, mutateAsync } =
    useMutation({
      mutationFn: (data) => resetPassword(data),
      onSuccess: async (tokenData) => {
        createAccessLog("RESET_PASSWORD_SUCCESS");
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

const resetPassword = async ({ token, password }) => {
  try {
    let response = await axiosAuthInstance.post("/password/reset/" + token, {
      newPassword: password,
    });
    return response.data;
  } catch (error) {
    console.log(error);
    return error;
  }
};
