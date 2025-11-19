"use client";

import { useState } from "react";
import { useAuth } from "@/context/AuthContext";
import { registerUser } from "@/features/auth/services/apiAuth";

export function RegisterForm() {
  const { register } = useAuth();

  // Estado para manejar si la solicitud está en curso
  const [isLoading, setIsLoading] = useState(false);
  // Estado para almacenar y mostrar mensajes de error
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    // 1. Iniciar carga y limpiar errores anteriores
    setIsLoading(true);
    setError(null);

    const formData = new FormData(e.currentTarget);
    const username = formData.get("username") as string;
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;

    // **Validación simple inicial**
    if (!username || !password || !email) {
      setError("Por favor, introduce usuario/email/contraseña.");
      setIsLoading(false);
      return;
    }

    try {
      // 2. Intentar loguear al usuario
      const { accessToken } = await registerUser({ username, email, password });
      register(accessToken); // ✅ guarda token en contexto y localStorage
    } catch (err) {
      // 3. Manejo del error
      // Ya que 'err' no tiene tipo, lo tratamos como 'any' o 'unknown' y extraemos el mensaje.
      console.error("Error de Login:", err);

      let errorMessage = "Ocurrió un error desconocido. Inténtalo de nuevo.";

      // Intentar acceder a un mensaje de error común de una API
      if (
        err &&
        typeof err === "object" &&
        "message" in err &&
        typeof err.message === "string"
      ) {
        errorMessage = err.message;
      } else if (typeof err === "string") {
        errorMessage = err;
      }

      setError(errorMessage);
    } finally {
      // 4. Finalizar carga, independientemente del resultado
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4 max-w-sm mx-auto">
      {/* MOSTRAR ERROR */}
      {error && <p className="text-red-500">{error}</p>}
      <input type="text" placeholder="Nombre completo" name="username" />
      <input type="email" placeholder="Correo electrónico" name="email" />
      <input type="password" placeholder="Contraseña" name="password" />
      <button type="submit" className="w-full" disabled={isLoading}>
        {/* TEXTO CONDICIONAL */}
        {isLoading ? "Cargando..." : "Registrarse"}
      </button>
    </form>
  );
}
