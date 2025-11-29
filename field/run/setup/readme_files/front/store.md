---

### 📁 `store/README.md`

````md
# 🧠 Store

Contiene el estado global usando Zustand (u otro state manager).

## 📌 Convenciones

- Un archivo por dominio: `auth.js`, `cart.js`
- Evitar lógica directamente dentro del componente

## 📎 Ejemplo

```js
// src/store/auth.js
import { create } from "zustand";

export const useAuthStore = create((set) => ({
  user: null,
  login: (user) => set({ user }),
}));
```
````
