"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import LoginForm from "@/features/auth/components/LoginForm";
import { useRedirectIfAuthenticated } from "@/hooks/auth/useRedirectIfAuthenticated";

export default function LoginPage() {
  useRedirectIfAuthenticated();

  return (
    <main className="flex min-h-screen items-center justify-center bg-gray-50">
      <div className="p-8 bg-white shadow rounded-lg w-full max-w-md">
        <h1 className="text-2xl font-bold mb-6 text-center">
          Accede a tu cuenta
        </h1>
        <LoginForm />
      </div>
    </main>
  );
}
