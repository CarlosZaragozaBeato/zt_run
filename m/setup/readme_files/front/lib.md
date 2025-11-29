# 🛠️ Lib

Funciones utilitarias, configuración de Axios, validaciones, etc.

## 📌 Convenciones

- No dependientes de la UI
- Usables desde cualquier parte del proyecto

## 📎 Ejemplo

```js
// src/lib/axios.js
import axios from "axios";

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});
```
