import { useAuth } from "@/context/AuthContext";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export async function loginUser(data: {
  usernameOrEmail: string;
  password: string;
}) {
  const url: string = `${API_URL}/api/auth/login`;

  const res = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  console.log(res.json());
  if (!res.ok) throw new Error("Error en login");
  return res.json();
}

export async function registerUser(data: {
  username: string;
  email: string;
  password: string;
}) {
  const url: string = `${API_URL}/api/auth/register`;

  const res = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!res.ok) throw new Error("Error en registro");
  console.log(res.json());
  return res.json();
}
