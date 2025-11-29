"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export function useRedirectIfAuthenticated() {
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      router.replace("/dashboard"); // ✅ redirige si ya está logueado
    }
  }, [router]);
}
