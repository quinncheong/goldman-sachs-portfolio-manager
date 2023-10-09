import Navbar from '@/components/layout/Navbar'
import Image from 'next/image'

export default function Home({ children }) {
  return (
    <Navbar>
        <main className="flex min-h-screen items-center justify-between p-24">
          {children}
        </main>
    </Navbar>
  )
}
