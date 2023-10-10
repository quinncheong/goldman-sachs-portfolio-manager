import Header from "@components/layout/Header";
import Footer from "@components/layout/Footer";

export default function AuthenticatedLayout({ children }) {
  return (
    <>
      <Header />
      <div className="flex-grow">{children}</div>
      <Footer />
    </>
  );
}
