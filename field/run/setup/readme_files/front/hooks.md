---

### 📁 `hooks/README.md`

````md
# 🪝 Hooks

Contiene hooks personalizados para reutilizar lógica entre componentes.

## 🧠 Reglas

- Nombrar siempre con `use` al inicio: `useAuth.js`, `useFetch.js`
- Son funciones puras, reutilizables y aisladas de UI

## 📎 Ejemplo

```js
// src/hooks/useCounter.js
import { useState } from "react";

export function useCounter(initial = 0) {
  const [count, setCount] = useState(initial);
  return { count, increment: () => setCount((c) => c + 1) };
}
```
````
