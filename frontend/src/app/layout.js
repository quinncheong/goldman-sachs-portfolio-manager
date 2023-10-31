import "./globals.css";
import { Inter } from "next/font/google";
import Providers from "../util/providers";

import { ToastContainer } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
  title: "Goldman Sachs Portfolio Analyzer",
  description: "Analyze your portfolios with Goldman Sachs",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en" className="flex flex-col min-h-screen">
      <body className={inter.className}>
        <main className="">
          <Providers>{children}</Providers>
          <ToastContainer
            position="bottom-right"
            autoClose={3000}
            limit={5}
            hideProgressBar={false}
            newestOnTop={false}
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            draggable
            theme="colored"
          />
        </main>
      </body>
    </html>
  );
}
