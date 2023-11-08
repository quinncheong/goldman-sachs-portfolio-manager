import {
  LineChart,
  Line,
  Legend,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { useGetTimeSeriesAnalysis } from "@/api/portfolio";
import Loader from "@/components/loading/Loader";

export default function PortfolioMarketChart({ portfolioData }) {
  const { data, isLoading, isError, error } = useGetTimeSeriesAnalysis({
    id: portfolioData.id,
    start: portfolioData,
    end: portfolioData.end
  });
  if (isLoading) return <Loader />;

  const entries = [];
  for (let key in data) {
    entries.push({ name: key, Price: data[key] });
  }

  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart width={640} height={300} data={entries}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="Price" stroke="#8884d8" />
      </LineChart>
    </ResponsiveContainer>
  );
}
