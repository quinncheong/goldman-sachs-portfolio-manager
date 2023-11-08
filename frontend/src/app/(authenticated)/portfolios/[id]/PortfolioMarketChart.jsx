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
  if (isLoading) return <div className="flex"><Loader /></div>;

  const entries = [];
  const months = {"01": "Jan", "02": "Feb", "03": "Mar", "04": "Apr", "05": "May","06": "Jun", "07": "Jul", "08": "Aug", "09": "Sep", "10": "Oct","11": "Nov", "12": "Dec"};
  for (let key in data) {
    let year = key.split("-")[0].slice(-2);
    let month = key.split("-")[1];
    entries.push({ name: months[month]+"' "+year, Value: data[key] });
  }

  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart width={640} height={300} data={entries}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis type="number" domain={['auto', 'auto']}/>
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="Value" stroke="#8884d8" name="Portfolio Value"/>
      </LineChart>
    </ResponsiveContainer>
  );
}
