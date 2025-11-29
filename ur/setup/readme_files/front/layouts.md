---

### 📁 `layouts/README.md`

````md
# 🧱 Layouts

Componentes de layout que definen estructuras globales (navbar, sidebar, etc.)

## 🧩 Ejemplo de uso

```jsx
// src/layouts/MainLayout.jsx
export default function MainLayout({ children }) {
  return (
    <div className="min-h-screen">
      <Navbar />
      <main>{children}</main>
    </div>
  );
}
```
````
