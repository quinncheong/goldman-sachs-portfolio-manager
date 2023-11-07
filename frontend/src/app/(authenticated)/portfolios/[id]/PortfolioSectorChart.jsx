import {
  PieChart,
  Pie,
  Sector,
  Cell,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042"];

export default function PortfolioSectorChart({ stockData, type }) {
  let data
  if (type == "sector") {
    data = Object.entries(stockData.sector).map(([key, value], index) => ({
      name: key.charAt(0).toUpperCase() + key.slice(1).toLowerCase(),
      value: +value.toFixed(2),
      fill: COLORS[index % COLORS.length]
    }))
  } else {
    data = Object.entries(stockData.country).map(([key, value], index) => ({
      name: key,
      value: +value.toFixed(2),
      fill: COLORS[index % COLORS.length]
    }))
  }


  const pieChart = () => {
    return (
      <ResponsiveContainer width={400} height="100%">
        <PieChart width={400} height={400}>
          <Pie
            dataKey="value"
            isAnimationActive={false}
            data={data}
            cx="50%"
            cy="50%"
            outerRadius={80}
            fill="#8884d8"
            label={(entry)  => `${entry.value}%`}
          />
          {
          	data.map((entry, index) => {
            	const color = entry.pv > 4000 ? COLORS[0] : COLORS[1];
            	return <Cell fill={color} />;
            })
          }
          <Tooltip formatter={(value) => `${value}%`}/>
        </PieChart>
      </ResponsiveContainer>
    );
  };

  return <>{pieChart()}</>;
}
