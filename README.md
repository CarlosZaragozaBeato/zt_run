# zt_run

Proyecto backend en **Spring Boot 3** usando **H2** como base temporal y **ELK stack** (Elasticsearch, Logstash y Kibana) para logging.  
El proyecto está preparado para futura migración a **PostgreSQL** o **MySQL**.

---

## 🔹 Estructura del proyecto

````
zt_run/
├── backend/                # Código fuente del backend (Spring Boot)
│   ├── src/main/java/      # Clases Java
│   ├── src/main/resources/ # application.properties, logback, Flyway, etc.
│   └── Dockerfile          # Imagen del backend
├── docker-compose.yml      # Orquestación Docker de backend y ELK
├── logstash/               # Configuración y pipelines de Logstash
│   └── logstash.conf       # Pipeline principal para logs JSON
├── logs/                   # Directorio donde se guardarán logs locales (opcional)
└── README.md               # Documentación del proyecto

````

---

## 🔹 Servicios

| Servicio       | Puerto | Descripción |
|----------------|--------|------------|
| Backend        | 8080   | Aplicación Spring Boot |
| Elasticsearch  | 9200   | Almacenamiento de logs JSON |
| Kibana         | 5601   | Visualización de logs |
| Logstash       | 5000   | Recepción de logs desde backend |

---

## 🔹 Comandos iniciales

### Levantar los servicios

```bash
# Construye la imagen del backend y levanta todos los contenedores
docker-compose up --build -d
````

### Verificación

```bash
# Ver contenedores activos
docker-compose ps

# Ver logs del backend en tiempo real
docker-compose logs -f ztrun-backend

# Ver logs de Logstash
docker-compose logs -f logstash
```

### Detener los servicios

```bash
docker-compose down
```

---

## 🔹 Endpoints de ejemplo

| Método | Endpoint    | Descripción                  |
| ------ | ----------- | ---------------------------- |
| GET    | /api/health | Devuelve estado del servicio |
| GET    | /api/items  | Listado de items (ejemplo)   |
| POST   | /api/items  | Crear un nuevo item          |
| GET    | /h2-console | Consola H2 para desarrollo   |

> 🔹 Nota: La URL de H2 en memoria es `jdbc:h2:mem:ztrun_db`, usuario: `SA`, contraseña: vacía.

---

## 🔹 Configuración de perfiles

Actualmente se usa un **perfil default** con H2.
En el futuro se pueden agregar perfiles para PostgreSQL o MySQL:

```properties
# application.properties
spring.profiles.active=default
spring.datasource.url=jdbc:h2:mem:ztrun_db
spring.datasource.username=SA
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

Para PostgreSQL o MySQL se crearían archivos separados:

```
application-postgres.properties
application-mysql.properties
```

---

## 🔹 Logging JSON

* Los logs del backend se envían a Logstash en **formato JSON**.
* Ejemplo de log generado:

```json
{
  "@timestamp": "2025-11-10T09:08:12.228Z",
  "level": "INFO",
  "thread": "main",
  "logger": "com.carloszaragoza.ztrun.ZtRunApplication",
  "message": "Starting ZtRunApplication",
  "app": "zt-run",
  "env": "local"
}
```

* Logstash los procesa y los envía a Elasticsearch, donde pueden visualizarse en Kibana (`http://localhost:5601`).

---

## 🔹 Notas importantes

* **Seguridad**: La contraseña generada por defecto es para desarrollo únicamente.
* **H2**: Base de datos en memoria, se pierde al apagar la aplicación.
* **Flyway**: Maneja migraciones de esquema automáticamente.
* **Logs**: Se recomienda revisar los pipelines de Logstash y ajustar filtros según sea necesario.
* **Futuro**: Migración a PostgreSQL/MySQL para persistencia real.

---

## 🔹 Referencias

* [Spring Boot Documentation](https://spring.io/projects/spring-boot)
* [Docker Compose Documentation](https://docs.docker.com/compose/)
* [H2 Database](https://www.h2database.com/)
* [Elastic Stack](https://www.elastic.co/what-is/elk-stack)
* [Flyway Database Migrations](https://flywaydb.org/)

```

---

Si quieres, puedo hacer otra versión **aún más práctica**, incluyendo **comandos de ejemplo para probar la API usando `curl` o Postman** y ver cómo se registran los logs en Kibana. Esto haría tu README un manual completo de desarrollo y pruebas.  

¿Quieres que haga esa versión?
```
