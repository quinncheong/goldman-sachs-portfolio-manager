
export default function Home({ children }) {
  return (
      <main className="flex min-h-screen items-center justify-between p-24">
        <p>Welcome to the Goldman Portfolio Application</p>
        {children}
      </main>
  )
}
