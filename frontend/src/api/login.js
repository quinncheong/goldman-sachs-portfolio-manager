import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { BASE_SERVER_URL, LOGIN_API_PATH } from "./apiFactory";

const axiosLoginInstance = axios.create({
  baseURL: BASE_SERVER_URL + PORTFOLIO_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

export const useLogin = () => {
  const queryClient = useQueryClient();
  const {
    isLoading: isCreating,
    isSuccess: isSuccessCreating,
    isError: isErrorCreating,
    error: errorCreateing,
    mutate: CreateTodo,
  } = useMutation((data) => postTodoFn(data), {
    onSuccess: () => {
      queryClient.invalidateQueries(["todos"]);
    },
  });
};

const getPortfolios = async () => {
  if (process.env.NODE_ENV !== "production") {
    return mockPortfolios;
  }
  let response = await axiosInstance.get("/all");
  return response.data;
};

export const getPortfolioByPortfoliId = (portfolioId) => {
  return axiosInstance.get("/" + portfolioId);
};
