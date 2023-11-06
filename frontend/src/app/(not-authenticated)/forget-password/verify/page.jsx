"use client";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";

import { verifyJWT } from "@/api/authentication";
import { useRouter } from "next/navigation";
import { toast } from "react-toastify";

import TokenVerified from "./TokenVerified";

function Page() {
  const searchParams = useSearchParams();
  const [verified, setVerified] = useState(false);

  let token = searchParams.get("token");

  useEffect(() => {
    verifyToken();
    async function verifyToken() {
      if (!token) {
        return;
      }

      let isVerified = await verifyJWT(token);
      console.log("isVerified " + isVerified);
      setVerified(isVerified);
    }
  }, [searchParams]);

  return verified ? (
    <div>
      <TokenVerified token={token} />
    </div>
  ) : (
    <RenderUnverified />
  );
}

function RenderUnverified() {
  const router = useRouter();

  useEffect(() => {
    // router.push("/");
  }, []);

  return <></>;
}

export default Page;
