"use client";

import { useRouter } from "next/navigation";

import Login from "@/components/login/Login";
import Loader from "@/components/loading/Loader";

import { useGetLoginStatus } from "@/api/authentication";

export default function App({ children }) {
  const router = useRouter();
  const { data: isLoggedIn, isLoading, isError, error } = useGetLoginStatus();

  if (isLoading) {
    return <Loader />;
  }

  if (isError) {
    return (
      <div className="flex min-h-screen items-center justify-between p-24">
        <p>Error: {error}</p>
      </div>
    );
  }

  if (isLoggedIn) {
    router.push("/dashboard");
    return;
  }

  return (
    <>
      <Login />
    </>
    // {children}
  );
}
