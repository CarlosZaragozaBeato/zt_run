import Link from "next/link";

export function Navbar() {
  return (
    <header className="bg-white shadow-sm px-6 py-4 flex justify-between items-center">
      <h1 className="text-xl font-bold text-brand-600">Mi Empresa</h1>
      <nav className="space-x-4">
        <Link href="/dashboard" className="text-gray-700 hover:text-brand-600">
          Dashboard
        </Link>
        <Link href="/login" className="text-gray-700 hover:text-brand-600">
          Login
        </Link>
        <Link href="/register" className="text-gray-700 hover:text-brand-600">
          Registro
        </Link>
      </nav>
    </header>
  );
}
