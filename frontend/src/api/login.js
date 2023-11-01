import axios from "axios";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { BASE_SERVER_URL, LOGIN_API_PATH } from "./apiFactory";

const axiosLoginInstance = axios.create({
  baseURL: BASE_SERVER_URL + LOGIN_API_PATH,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

export const login = async (username, password) => {
  const json = {
    username: username,
    password: password,
  };
  try {
    let response = await axiosLoginInstance.post("/authenticate", json);
    return response.data.token;
  } catch (err) {
    if (err.response.status === 403) {
      console.log("Wrong username/password.");
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
    let response = await axiosLoginInstance.get("/all");
    isLoggedIn = response.data;
  }

  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve(isLoggedIn);
    }, 1000);
  });
};

// export const useLogin = () => {
//   const queryClient = useQueryClient();
//   const {
//     isLoading: isCreating,
//     isSuccess: isSuccessCreating,
//     isError: isErrorCreating,
//     error: errorCreateing,
//     mutate: CreateTodo,
//   } = useMutation((data) => postTodoFn(data), {
//     onSuccess: () => {
//       queryClient.invalidateQueries(["todos"]);
//     },
//   });
// };

// const getPortfolios = async () => {
//   if (process.env.NODE_ENV !== "production") {
//     return mockPortfolios;
//   }
//   let response = await axiosInstance.get("/all");
//   return response.data;
// };
