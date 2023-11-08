import {
  useGetAnalysis,
} from "@/api/portfolio";
import Link from "next/link";

export default function PortfolioTableRow({ portfolio, index }) {
  function formatCurrency(value) {
    return `$${value.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, "$&,")}`;
  }

  if (!portfolio) {
    return <></>
  }

  return (
    <tr key={index} className={index % 2 === 0 ? "bg-gray-100" : ""}>
      <td className="py-2 px-4">{portfolio.name}</td>
      <td className="py-2 px-4">{portfolio.description}</td>
      <td className="py-2 px-4">{formatCurrency(portfolio.initialValue)}</td>
      <td className="py-2 px-4">{formatCurrency(portfolio.value)}</td>
      <td
        className={`py-2 px-4 ${
          portfolio.pnla >= 0 ? "text-green-600" : "text-red-500"
        }`}
      >
        <span>
          {portfolio.pnla >= 0 ? "+" : ""}
          {portfolio.pnla.toFixed(2)} 
          ({portfolio.pnl >= 0 ? "+" : ""}
          {portfolio.pnl.toFixed(2)}%)
        </span>
      </td>
      <td className="py-2 px-4">
        <Link href={`/portfolios/${portfolio.id}`}>
          <span className="text-blue-500 hover:underline">View Portfolio</span>
        </Link>
      </td>
    </tr>
  );
}
