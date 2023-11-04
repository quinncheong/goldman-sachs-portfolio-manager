"use client";
import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getCookie } from "cookies-next";

import { BASE_SERVER_URL, USER_API_PATH } from "./apiFactory";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";

const axiosUserInstance = axios.create({
  baseURL: BASE_SERVER_URL + USER_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
    Authorization: "Bearer " + getCookie("token"),
  },
});

export const useGetAccessLogs = () => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ["getAccessLogs"],
    queryFn: () => getAccessLogs(),
  });

  return { data, isLoading, isError, error };
};

const getAccessLogs = async () => {
  try {
    let userId = jwtDecode(getCookie("token")).userId;
    let response = await axiosUserInstance.get("/" + userId + "/log");
    return response.data.toReversed();
  } catch (error) {
    toast.error("You have no permissions!");
    return [];
  }
};

export const useCreateAccessLog = () => {
  const queryClient = useQueryClient();
  const {
    isLoading: isCreatingAccessLog,
    isSuccess: isAccessLogSuccess,
    isError: isAccessLogError,
    error: accessLogError,
    mutateAsync: addAccessLog,
  } = useMutation({
    mutationFn: (data) => createAccessLog(data),
    onSuccess: () => {
      queryClient.invalidateQueries(["getAccessLogs"]);
    },
    onMutate: (variables) => {
      // A mutation is about to happen!
      // Optionally return a context containing data to use when for example rolling back
      // return { id: 1 };
    },
    onError: (error, variables, context) => {
      // An error happened!
      // console.log(`rolling back optimistic update with id ${context.id}`);
      console.log(error);
    },
    onSuccess: (data, variables, context) => {},
    onSettled: (data, error, variables, context) => {
      // Error or success... doesn't matter!
    },
  });

  return {
    isCreatingAccessLog,
    isAccessLogSuccess,
    isAccessLogError,
    accessLogError,
    addAccessLog,
  };
};

const createAccessLog = async (action) => {
  try {
    let userId = jwtDecode(getCookie("token")).userId;
    let response = await axiosUserInstance.post("/log", { userId, action });
    return response.data;
  } catch (error) {
    return "";
  }
};
