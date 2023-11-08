"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useRegister } from "@/api/authentication";

export default function Register() {
  const router = useRouter();

  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");

  const [passwordErrors, setPasswordErrors] = useState([]);
  const [confirmPasswordErrors, setConfirmPasswordErrors] = useState([]);
  const [emailErrors, setEmailErrors] = useState([]);

  const {
    data,
    isLoading,
    isSuccess: isRegisterSuccess,
    isError: isRegisterError,
    error: registerError,
    mutateAsync,
  } = useRegister();

  const handleRegisterUser = async (e) => {
    e.preventDefault();

    if (
      passwordErrors.length > 0 ||
      confirmPasswordErrors.length > 0 ||
      emailErrors.length > 0
    ) {
      return;
    }

    if (
      password.length === 0 ||
      confirmPassword.length === 0 ||
      email.length === 0 ||
      name.length === 0
    ) {
      return;
    }

    let newUser = {
      name,
      username,
      password,
      email,
    };

    await mutateAsync(newUser);
  };

  const validatePassword = async (e) => {
    let errors = [];
    if (password.length < 8 || password.length > 25) {
      errors.push("Password must be between 8 - 25 characters");
    }

    // Must contain at least 1 uppercase letter
    if (!/[A-Z]/.test(password)) {
      errors.push("Password must contain at least 1 Uppercase Letter");
    }

    // Must contain at least 1 lowercase letter
    if (!/[a-z]/.test(password)) {
      errors.push("Password must contain at least 1 Lowercase Letter");
    }

    if (!/\d/.test(password)) {
      errors.push("Password must contain at least 1 digit");
    }

    if (!/[^\w\s]/.test(password)) {
      errors.push("Password must contain at least 1 symbol");
    }

    setPasswordErrors(errors);

    return errors;
  };

  const validateConfirmPassword = (e) => {
    let errors = [];

    if (password !== confirmPassword) {
      errors.push("Passwords do not match!");
    }

    setConfirmPasswordErrors(errors);
    return errors;
  };

  const validateEmail = (e) => {
    let errors = [];
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
      errors.push("Email is invalid!");
    }
    setEmailErrors(errors);
  };

  const renderSuccessMsg = () => {
    return (
      <div className="container flex flex-col">
        <p className="mb-4">
          Your Registration is Successful! The last step is to confirm your
          email.
        </p>
        <p className="mb-4">
          A link has been sent to your email for confirmation. Check your inbox
          shortly!
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
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
              Register for a new account
            </h1>

            {/* Form */}

            {!isRegisterSuccess && (
              <form className="space-y-4 md:space-y-6" action="#">
                <div>
                  <label
                    htmlFor="username"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    What is your name
                  </label>
                  <input
                    type="text"
                    name="name"
                    id="name"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="Enter your name"
                    required
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                </div>
                <div>
                  <label
                    htmlFor="username"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Your username
                  </label>
                  <input
                    type="text"
                    name="username"
                    id="username"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="username"
                    required
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                  />
                </div>
                <div>
                  <label
                    htmlFor="password"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Password
                  </label>
                  <input
                    type="password"
                    name="password"
                    id="password"
                    placeholder="••••••••"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    onBlur={validatePassword}
                  />
                  <RenderFormErrors errors={passwordErrors} />
                </div>
                <div>
                  <label
                    htmlFor="confirmPassword"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Confirm Password
                  </label>
                  <input
                    type="password"
                    name="confirmPassword"
                    id="confirmPassword"
                    placeholder="••••••••"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    required
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    onBlur={validateConfirmPassword}
                  />
                  <RenderFormErrors errors={confirmPasswordErrors} />
                </div>
                <div>
                  <label
                    htmlFor="email"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Email
                  </label>
                  <input
                    type="email"
                    name="email"
                    id="email"
                    placeholder="Enter your Email"
                    className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    onBlur={validateEmail}
                  />
                  <RenderFormErrors errors={emailErrors} />
                </div>

                <button
                  onClick={handleRegisterUser}
                  type="submit"
                  className="w-full text-white btn bg-primary-300 hover:bg-green-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
                >
                  Create Account
                </button>
              </form>
            )}
            {isRegisterSuccess && renderSuccessMsg()}
            {/* Form End */}
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
