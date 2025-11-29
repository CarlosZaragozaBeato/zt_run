# 🧩 Components

Componentes reutilizables y desacoplados, pensados para ser usados en múltiples partes del proyecto.

## 📌 Convenciones

- Nombrar en PascalCase: `Button.jsx`, `Card.jsx`
- Cada componente puede tener su propio archivo de estilo o usar Tailwind

## 📎 Ejemplo

```jsx
// src/components/Button.jsx
export default function Button({ children }) {
  return (
    <button className="bg-blue-500 text-white px-4 py-2 rounded">
      {children}
    </button>
  );
}
```
