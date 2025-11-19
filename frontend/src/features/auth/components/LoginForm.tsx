"use client";

import { useState } from "react";
import { useAuth } from "@/context/AuthContext";
import { loginUser } from "@/features/auth/services/apiAuth";

export default function LoginForm() {
  const { login } = useAuth();
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
    const usernameOrEmail = formData.get("username") as string;
    // Asegúrate de que el Input de contraseña tiene un `name`
    const password = formData.get("password") as string;

    // **Validación simple inicial**
    if (!usernameOrEmail || !password) {
      setError("Por favor, introduce usuario/email y contraseña.");
      setIsLoading(false);
      return;
    }

    try {
      // 2. Intentar loguear al usuario
      const { accessToken } = await loginUser({ usernameOrEmail, password });
      login(accessToken); // ✅ guarda token en contexto y localStorage
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
      {/* Mensaje de Error */}
      {error && <p className="text-red-500 bg-red-100 p-2 rounded">{error}</p>}

      {/* Asegúrate de añadir el atributo `name="password"` al Input de contraseña */}
      <input
        type="text"
        name="username"
        placeholder="Usuario o Correo electrónico"
        required // Opcional: Validación HTML nativa
        disabled={isLoading}
      />
      <input
        type="password"
        name="password"
        placeholder="Contraseña"
        required
        disabled={isLoading}
      />

      {/* Botón con estado de carga */}
      <button
        type="submit"
        className="w-full"
        disabled={isLoading} // Deshabilitar durante la carga
      >
        {isLoading ? "Accediendo..." : "Login"}
      </button>
    </form>
  );
}
