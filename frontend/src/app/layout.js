import "./globals.css";
import { Inter } from "next/font/google";
import Providers from "../util/providers";

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
        </main>
      </body>
    </html>
  );
}
