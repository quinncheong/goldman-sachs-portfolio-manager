import Link from "next/link";

export default function PortfolioTableRow({ portfolio, index }) {
  if (!portfolio) {
    return <></>;
  }

  return (
    <tr key={index} className={index % 2 === 0 ? "bg-gray-100" : ""}>
      <td className="py-2 px-4">{portfolio.name}</td>
      <td className="py-2 px-4">{portfolio.description}</td>
      <td className="py-2 px-4">
        {new Intl.NumberFormat("en-US", {
          style: "currency",
          currency: "USD",
        }).format(portfolio.initialValue)}
      </td>
      <td className="py-2 px-4">
        {new Intl.NumberFormat("en-US", {
          style: "currency",
          currency: "USD",
        }).format(portfolio.value)}
      </td>
      <td
        className={`py-2 px-4 ${
          portfolio.pnla >= 0 ? "text-green-600" : "text-red-500"
        }`}
      >
        <span>
          {portfolio.pnla >= 0 ? "+" : ""}
          {new Intl.NumberFormat("en-US", {
            style: "currency",
            currency: "USD",
          }).format(portfolio.pnla)}
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
