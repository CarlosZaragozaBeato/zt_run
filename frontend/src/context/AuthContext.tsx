"use client";

import { useRouter } from "next/navigation";
import {
  createContext,
  useContext,
  useState,
  useEffect,
  type ReactNode,
} from "react";

interface AuthContextType {
  token: string | null;
  login: (token: string) => void;
  register: (token: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    // Cargar token de localStorage al iniciar (opcional)
    const savedToken = localStorage.getItem("auth_token");
    if (savedToken) {
      setToken(savedToken);
    }
  }, []);

  const login = (newToken: string) => {
    setToken(newToken);
    localStorage.setItem("auth_token", newToken);

    // ✅ Guardar token en cookies para middleware
    document.cookie = `accessToken=${newToken}; path=/; secure; samesite=strict`;
  };

  const register = (newToken: string) => {
    setToken(newToken);
    localStorage.setItem("auth_token", newToken);

    // ✅ Guardar token en cookies para middleware
    document.cookie = `accessToken=${newToken}; path=/; secure; samesite=strict`;
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem("auth_token");

    // ✅ Eliminar cookie
    document.cookie =
      "accessToken=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";

    router.push("/login");
  };

  return (
    <AuthContext.Provider value={{ token, login, logout, register }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within an AuthProvider");
  return context;
}
