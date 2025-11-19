import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(req: NextRequest) {
  const token = req.cookies.get("accessToken")?.value || null;

  // Si el usuario ya tiene token y quiere entrar a /login o /register → redirigir
  if (
    token &&
    (req.nextUrl.pathname === "/login" || req.nextUrl.pathname === "/register")
  ) {
    return NextResponse.redirect(new URL("/calendar", req.url));
  }

  return NextResponse.next();
}

// Configura las rutas donde se aplica el middleware
export const config = {
  matcher: ["/login", "/register"],
};
