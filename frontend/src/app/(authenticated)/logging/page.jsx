"use client";
import React, { useEffect } from "react";
import { getCookie } from "cookies-next";
import { useRouter } from "next/navigation";
import { jwtDecode } from "jwt-decode";
import { useGetAccessLogs } from "@/api/user";
import withAuth from "@/middleware/authentication";

function Logging() {
  const router = useRouter();
  const { data, isLoading, isError, error } = useGetAccessLogs();

  useEffect(() => {
    if (!getCookie("token") || jwtDecode(getCookie("token")).role !== "ADMIN") {
      router.push("/");
    }
  }, []);

  if (!data || jwtDecode(getCookie("token")).role !== "ADMIN") {
    return <div>Empty</div>;
  }

  return (
    <div className="h-screen container m-auto p-5 overflow-y-scroll">
      {renderLogs()}
    </div>
  );

  function renderLogs() {
    return data.map((log, index) => {
      return (
        <div
          key={index}
          className="flex flex-col border-2 border-black rounded m-auto p-5 text-black mb-3"
        >
          <p>
            <b>USER:</b> {log.userId}
          </p>
          <p>
            <b>NAME:</b> {log.userName}
          </p>
          <p>
            <b>ACTION:</b> {log.action}
          </p>
          <p>
            <b>TIME:</b> {log.date}
          </p>
        </div>
      );
    });
  }
}

export default withAuth(Logging);