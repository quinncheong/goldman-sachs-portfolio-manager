"use client";
import { useEffect, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

import { verifyRegisteredUser } from "@/api/authentication";
import Loader from "@/components/loading/Loader";

function RegisterTokenVerified({ token }) {
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    handleRegisterUser();

    async function handleRegisterUser() {
      let finishedConfirmation = await verifyRegisteredUser(token);
      if (finishedConfirmation) {
        setLoading(false);
      } else {
        router.push("/");
      }
    }
  }, [router, token]);

  if (loading) {
    return <Loader />;
  }

  return (
    <section style={{ height: 1700 }} className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center p-8 m-auto">
        <a
          href="#"
          className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
        >
          <img
            className="w-8 h-8 mr-2"
            src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg"
            alt="logo"
          />
          Goldman Sachs Portfolio Analyzer
        </a>
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-2xl xl:p-0 dark:bg-gray-800 dark:border-gray-700">
          <div className="flex flex-col p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
              Congratulations! Your account has been successfully registered and
              verified.
            </h1>
            <p className="">
              You can now click the button below to go back to the Sign in page
              and access your account.
            </p>
            <button className="btn bg-primary-300 hover:bg-green-800 my-auto mt-3">
              <Link href={"/"}>Sign In</Link>
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}

export default RegisterTokenVerified;
