// src/app/layout.tsx
import "@/styles/globals.css";
import { Navbar } from "@/components/layout/Navbar";

export const metadata = {
  title: "ZT RUN",
  description: "Run legs Run",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="es">
      <body className="min-h-screen bg-white text-gray-900">
        <Navbar />

        {children}
      </body>
    </html>
  );
}
