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

const data = [
  {
    name: "23/07/10",
    Benchmark: 4000,
    Price: 2400,
    amt: 2400,
  },
  {
    name: "24/07/10",
    Benchmark: 3000,
    Price: 1398,
    amt: 2210,
  },
  {
    name: "25/07/10",
    Benchmark: 2000,
    Price: 9800,
    amt: 2290,
  },
  {
    name: "26/07/10",
    Benchmark: 2780,
    Price: 3908,
    amt: 2000,
  },
  {
    name: "27/07/10",
    Benchmark: 1890,
    Price: 4800,
    amt: 2181,
  },
  {
    name: "28/07/10",
    Benchmark: 2390,
    Price: 3800,
    amt: 2500,
  },
  {
    name: "29/07/10",
    Benchmark: 3490,
    Price: 4300,
    amt: 2100,
  },
];

export default function PortfolioMarketChart() {
  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart width={640} height={300} data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="Price" stroke="#8884d8" />
        <Line type="monotone" dataKey="Benchmark" stroke="#82ca9d" />
      </LineChart>
    </ResponsiveContainer>
  );
}
