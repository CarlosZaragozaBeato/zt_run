"use client";

import { createContext, useContext, useState, useEffect } from "react";

type AuthContextType = {
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
  register: (token: string) => void;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    // Recuperar token del localStorage si existe
    const savedToken = localStorage.getItem("accessToken");
    if (savedToken) setToken(savedToken);
  }, []);

  const login = (token: string) => {
    setToken(token);
    localStorage.setItem("accessToken", token);
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem("accessToken");
  };

  const register = (token: string) => {
    setToken(token);
    localStorage.setItem("accessToken", token);
  };

  return (
    <AuthContext.Provider value={{ token, login, logout, register }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}
