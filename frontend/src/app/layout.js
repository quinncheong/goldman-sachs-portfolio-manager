import './globals.css'
import { Inter } from 'next/font/google'
import Header from '@components/layout/Header'
import Footer from '@components/layout/Footer'

const inter = Inter({ subsets: ['latin'] })

export const metadata = {
  title: 'Goldman Sachs Portfolio Analyzer',
  description: 'Analyze your portfolios with Goldman Sachs',
}

export default function RootLayout({ children }) {
  return (
    <html lang="en" className="flex flex-col min-h-screen">
      <body className={inter.className}>
        <div className="flex-grow">
          <Header />
          <main className="">{children}</main>
          <Footer />
        </div>
      </body>
    </html>
  )
}
