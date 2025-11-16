// src/features/auth/hooks/useAuth.ts
"use client";

import { loginUser, registerUser } from "../services/apiAuth";

export function useAuth() {
  async function login(email: string, password: string) {
    return await loginUser(email, password);
  }

  async function register(data: {
    username: string;
    email: string;
    password: string;
  }) {
    return await registerUser(data);
  }
  return { login, register };
}
