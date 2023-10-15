/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
      },
      colors: {
        "primary-100": "#7399C6",
        "primary-200": "#186ADE",
        "primary-300": "#0D4EA6",
        "secondary-100": "#11294D",
        "secondary-200": "#23254D",
        background: "#C1CCD6",
      },
    },
  },
  plugins: [require("daisyui")],
};
