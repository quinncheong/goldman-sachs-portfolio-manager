"use client";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import { verifyJWT } from "@/api/authentication";
import { useRouter } from "next/navigation";
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
      setVerified(isVerified);
    }
  }, [searchParams]);

  return verified ? (
    <div>
      <TokenVerified token={token} />
    </div>
  ) : (
    <></>
  );
}

export default Page;
