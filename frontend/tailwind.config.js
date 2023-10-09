/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic':
          'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
      colors : {
        "gs-blue-1": "#7399C6",
        "gs-blue-2": "#186ADE",
        "gs-blue-3": "#0D4EA6",
        "gs-blue-4": "#11294D",
        "gs-blue-5": "#23254D",
      }
    },
  },
  plugins: [],
}
