"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useSendResetPwMail } from "@api/authentication";

export default function Page() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [emailErrors, setEmailErrors] = useState([]);

  const {
    data,
    isLoading,
    isSuccess: mailSentSuccess,
    isError,
    error,
    mutateAsync,
  } = useSendResetPwMail();

  const handleSubmit = async (e) => {
    e.preventDefault();
    let errors = [];
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
      errors.push("Email is invalid!");
    }

    if (errors.length > 0) {
      setEmailErrors(errors);
      return;
    }

    await mutateAsync(email);
  };

  //   if (mailSent) {
  //     return (

  //     )
  //   }

  const renderSuccessMsg = () => {
    return (
      <div className="container flex flex-col">
        <p className="mb-4">
          The Reset Password link has been sent. Check your inbox shortly!
        </p>
        <Link
          href="/"
          className="btn btn-info mx-auto text-sm mt-3 font-medium text-primary-600 hover:underline dark:text-primary-500"
        >
          Back to Login
        </Link>
      </div>
    );
  };

  const renderForm = () => {
    return (
      <form className="space-y-4 md:space-y-6" action="#">
        <div>
          <label
            htmlFor="email"
            className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
          >
            Your email
          </label>
          <input
            type="text"
            name="email"
            id="email"
            className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="email"
            required=""
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <RenderFormErrors errors={emailErrors} />
        <button
          onClick={handleSubmit}
          type="submit"
          className="w-full text-black font-bold btn bg-primary-100 hover:bg-green-800 hover:text-white focus:ring-4 focus:outline-none focus:ring-primary-300 rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
        >
          Reset Password
        </button>
      </form>
    );
  };

  return (
    <section className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
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
        <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
              Enter your email:
            </h1>
            <p>
              If you are registered with us, we will send you an email at your
              registered email:
            </p>
            {mailSentSuccess && renderSuccessMsg()}
            {!mailSentSuccess && renderForm()}
          </div>
        </div>
      </div>
    </section>
  );
}

function RenderFormErrors({ errors }) {
  if (!errors || errors.length === 0) {
    return;
  }
  return (
    <div className="container flex flex-col gap-3 p-3 my-5 border-2 border-error">
      {errors.map((err, index) => (
        <div key={index} className="alert alert-error">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="stroke-current shrink-0 h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span>{err}</span>
        </div>
      ))}
    </div>
  );
}
