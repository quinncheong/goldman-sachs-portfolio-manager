"use client";

import React, { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { deleteCookie } from "cookies-next";

export default function Navbar() {
  const router = useRouter();

  let [menuItems, setMenu] = useState([
    "Dashboard",
    "Account",
    "Portfolios",
    "Stocks",
  ]);

  function menu() {
    return (
      <div className="navbar-center hidden lg:flex">
        <ul className="menu menu-horizontal px-1">
          {menuItems.map((menuItem, index) => {
            return (
              <li key={index}>
                <Link href={"/" + menuItem.toLowerCase()}>{menuItem}</Link>
              </li>
            );
          })}
        </ul>
      </div>
    );
  }

  function logout() {
    deleteCookie("token");
    router.push("/");
  }

  return (
    <div className="navbar bg-secondary-100">
      <div className="navbar-start">
        <div className="dropdown">
          <label tabIndex={0} className="btn btn-ghost lg:hidden">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M4 6h16M4 12h8m-8 6h16"
              />
            </svg>
          </label>
          <ul
            tabIndex={0}
            className="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52"
          >
            <li>
              <a>Item 1</a>
            </li>
            <li>
              <a>Parent</a>
              <ul className="p-2">
                <li>
                  <a>Submenu 1</a>
                </li>
                <li>
                  <a>Submenu 2</a>
                </li>
              </ul>
            </li>
            <li>
              <a>Item 3</a>
            </li>
          </ul>
        </div>
        <Link href={"/dashboard"} className="btn btn-ghost normal-case text-xl">
          Goldman Portfolio Analyzer
        </Link>
        {menu()}
      </div>

      <button
        className="btn text-white bg-primary-100 ml-auto mr-3"
        onClick={logout}
      >
        Logout
      </button>
    </div>
  );
}
