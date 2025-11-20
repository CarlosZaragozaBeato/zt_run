"use client";

import { useState } from "react";
import { useRouter } from "next/navigation"; // ✅ importar router
import { useAuth } from "@/context/AuthContext";
import { registerUser } from "@/features/auth/services/apiAuth";

export function RegisterForm() {
  const { register } = useAuth();
  const router = useRouter(); // ✅ hook para redirección

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    const formData = new FormData(e.currentTarget);
    const username = formData.get("username") as string;
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;

    if (!username || !password || !email) {
      setError("Por favor, introduce usuario/email/contraseña.");
      setIsLoading(false);
      return;
    }

    try {
      // Llamada al backend
      const { accessToken } = await registerUser({ username, email, password });

      // Guardar token en contexto + localStorage
      register(accessToken);

      // ✅ Redirigir al dashboard
      router.replace("/dashboard");
    } catch (err: any) {
      console.error("Error de Registro:", err);
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
      {error && <p className="text-red-500">{error}</p>}

      <input
        type="text"
        placeholder="Nombre completo"
        name="username"
        required
        disabled={isLoading}
      />
      <input
        type="email"
        placeholder="Correo electrónico"
        name="email"
        required
        disabled={isLoading}
      />
      <input
        type="password"
        placeholder="Contraseña"
        name="password"
        required
        disabled={isLoading}
      />

      <button type="submit" className="w-full" disabled={isLoading}>
        {isLoading ? "Cargando..." : "Registrarse"}
      </button>
    </form>
  );
}
