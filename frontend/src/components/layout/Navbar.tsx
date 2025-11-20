"use client";

import { useAuth } from "@/context/AuthContext";
import Link from "next/link";

export function Navbar() {
  const { token, logout } = useAuth();

  return (
    <nav className="bg-white border-b border-gray-200 shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo/Brand */}
          <div className="flex-shrink-0">
            <Link
              href="/"
              className="text-2xl font-bold text-brand-600 hover:text-brand-700 transition-colors"
            >
              MyApp
            </Link>
          </div>

          {/* Navigation Links */}
          <div className="flex items-center gap-2">
            {!token ? (
              <>
                <Link
                  href="/login"
                  className="px-4 py-2 text-gray-700 hover:text-brand-600 font-medium transition-colors rounded-lg hover:bg-gray-50"
                >
                  Login
                </Link>
                <Link
                  href="/register"
                  className="px-4 py-2 bg-brand-500 text-white font-medium rounded-lg hover:bg-brand-600 active:bg-brand-700 transition-colors shadow-sm"
                >
                  Register
                </Link>
              </>
            ) : (
              <>
                <Link
                  href="/calendar"
                  className="px-4 py-2 text-gray-700 hover:text-brand-600 font-medium transition-colors rounded-lg hover:bg-gray-50"
                >
                  Calendar
                </Link>
                <Link
                  href="/profile"
                  className="px-4 py-2 text-gray-700 hover:text-brand-600 font-medium transition-colors rounded-lg hover:bg-gray-50"
                >
                  Profile
                </Link>
                <button
                  onClick={logout}
                  className="px-4 py-2 text-gray-700 hover:text-red-600 font-medium transition-colors rounded-lg hover:bg-red-50 border border-gray-300 hover:border-red-300"
                >
                  Logout
                </button>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
