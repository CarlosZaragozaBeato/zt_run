"use client";

import Link from "next/link";
import type React from "react";
import { useAuth } from "@/context/AuthContext";
import { loginUser } from "@/features/auth/services/apiAuth";
import { useState } from "react";
import { useRouter } from "next/navigation";

export default function LoginForm() {
  const { login } = useAuth();
  const router = useRouter(); // ✅ hook para redirección
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    const formData = new FormData(e.currentTarget);
    const usernameOrEmail = formData.get("username") as string;
    const password = formData.get("password") as string;

    if (!usernameOrEmail || !password) {
      setError("Por favor, introduce usuario/email y contraseña.");
      setIsLoading(false);
      return;
    }

    try {
      // Llamada al backend
      const { accessToken } = await loginUser({ usernameOrEmail, password });

      // Guardar token en contexto + localStorage
      login(accessToken);

      // ✅ Redirigir al dashboard
      router.replace("/dashboard");
    } catch (err: any) {
      console.error("Error de Login:", err);
      let errorMessage = "Ocurrió un error desconocido. Inténtalo de nuevo.";

      if (err?.message) {
        errorMessage = err.message;
      } else if (typeof err === "string") {
        errorMessage = err;
      }

      setError(errorMessage);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className="w-full max-w-md mx-auto">
      <div className="bg-white rounded-xl shadow-lg border border-gray-200 p-8">
        {/* Header */}
        <div className="mb-8 text-center">
          <h2 className="text-3xl font-bold text-gray-900 mb-2">Bienvenido</h2>
          <p className="text-gray-600">Inicia sesión en tu cuenta</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          {/* Error message */}
          {error && (
            <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
              {error}
            </div>
          )}

          {/* Username/Email input */}
          <div>
            <label
              htmlFor="username"
              className="block text-sm font-medium text-gray-700 mb-2"
            >
              Usuario o Email
            </label>
            <input
              id="username"
              type="text"
              name="username"
              placeholder="tu@email.com"
              required
              disabled={isLoading}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-brand-500 focus:border-transparent outline-none transition-all disabled:bg-gray-100 disabled:cursor-not-allowed"
            />
          </div>

          {/* Password input */}
          <div>
            <div className="flex items-center justify-between mb-2">
              <label
                htmlFor="password"
                className="block text-sm font-medium text-gray-700"
              >
                Contraseña
              </label>
              <a
                href="#"
                className="text-sm text-brand-600 hover:text-brand-700 transition-colors"
              >
                ¿Olvidaste tu contraseña?
              </a>
            </div>
            <input
              id="password"
              type="password"
              name="password"
              placeholder="••••••••"
              required
              disabled={isLoading}
              className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:ring-2 focus:ring-brand-500 focus:border-transparent outline-none transition-all disabled:bg-gray-100 disabled:cursor-not-allowed"
            />
          </div>

          {/* Remember me checkbox */}
          <div className="flex items-center">
            <input
              id="remember"
              type="checkbox"
              className="w-4 h-4 text-brand-600 border-gray-300 rounded focus:ring-brand-500"
            />
            <label htmlFor="remember" className="ml-2 text-sm text-gray-700">
              Recordarme
            </label>
          </div>

          {/* Submit button */}
          <button
            type="submit"
            disabled={isLoading}
            className="w-full bg-brand-500 hover:bg-brand-600 text-white font-medium py-3 px-4 rounded-lg transition-colors disabled:bg-gray-400 disabled:cursor-not-allowed"
          >
            {isLoading ? "Accediendo..." : "Iniciar Sesión"}
          </button>
        </form>

        {/* Footer */}
        <div className="mt-6 text-center">
          <p className="text-sm text-gray-600">
            ¿No tienes una cuenta? <Link href="/register">Regístrate</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
