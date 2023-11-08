"use client";

import { useState, useEffect } from "react";
import { getCookie } from "cookies-next";
import { useRouter } from "next/navigation";
import { jwtDecode } from "jwt-decode";

export const withAuth = (Component) => {
  return function AuthenticatedComponent(props) {
    const router = useRouter();
    const [isAuth, setIsAuth] = useState(false);

    let isAuthenticated = false;
    try {
      jwtDecode(getCookie("token"));
      isAuthenticated = true;
    } catch (error) {}

    useEffect(() => {
      verifyAuth();
      async function verifyAuth() {
        if (!isAuthenticated) {
          router.push("/");
        } else {
          setIsAuth(true);
        }
      }
    }, [isAuthenticated, router]);

    return !!isAuth ? <Component {...props} /> : null;
  };
};

export default withAuth;
