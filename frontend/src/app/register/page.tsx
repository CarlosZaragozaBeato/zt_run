"use client";

import { RegisterForm } from "@/features/auth/components/RegisterForm";
import { useRedirectIfAuthenticated } from "@/hooks/auth/useRedirectIfAuthenticated";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function RegisterPage() {
  useRedirectIfAuthenticated();

  return (
    <main className="flex min-h-screen items-center justify-center bg-gray-50">
      <div className="p-8 bg-white shadow rounded-lg w-full max-w-md">
        <h1 className="text-2xl font-bold mb-6 text-center">Crea tu cuenta</h1>
        <RegisterForm />
      </div>
    </main>
  );
}
