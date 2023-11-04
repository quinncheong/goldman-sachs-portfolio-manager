"use client";
import Header from "@components/layout/Header";
import Footer from "@components/layout/Footer";
import { withAuth } from "@/middleware/authentication";

function AuthenticatedLayout({ children }) {
  return (
    <>
      <Header />
      <div className="flex-grow">{children}</div>
      <Footer />
    </>
  );
}

export default withAuth(AuthenticatedLayout);
