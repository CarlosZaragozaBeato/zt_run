"use client";

import { useState } from "react";
import { useRouter } from "next/navigation"; // ✅ importar router
import { useAuth } from "@/context/AuthContext";
import { loginUser } from "@/features/auth/services/apiAuth";

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
    <form onSubmit={handleSubmit} className="space-y-4 max-w-sm mx-auto">
      {error && <p className="text-red-500 bg-red-100 p-2 rounded">{error}</p>}

      <input
        type="text"
        name="username"
        placeholder="Usuario o Correo electrónico"
        required
        disabled={isLoading}
      />
      <input
        type="password"
        name="password"
        placeholder="Contraseña"
        required
        disabled={isLoading}
      />

      <button type="submit" className="w-full" disabled={isLoading}>
        {isLoading ? "Accediendo..." : "Login"}
      </button>
    </form>
  );
}
