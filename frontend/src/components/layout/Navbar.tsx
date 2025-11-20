"use client";

import Link from "next/link";
import { useAuth } from "@/context/AuthContext";

export function Navbar() {
  const { token, logout } = useAuth();

  return (
    <nav className="flex gap-4 p-4 border-b">
      {!token ? (
        <>
          <Link href="/login">Login</Link>
          <Link href="/register">Register</Link>
        </>
      ) : (
        <>
          <Link href="/calendar">Calendar</Link>
          <Link href="/profile">Profile</Link>
          <button onClick={logout}>Logout</button>
        </>
      )}
    </nav>
  );
}
