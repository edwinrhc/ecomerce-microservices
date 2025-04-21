# 🛠️ Config Server - Sistema de Gestión de Pedidos

Este microservicio actúa como **servidor central de configuración** para todos los microservicios del sistema. Expone propiedades externas desde un repositorio Git (local) a través de HTTP.

---

## 📁 Estructura del proyecto

- Lenguaje: Java 17
- Framework: Spring Boot 3.3.0
- Spring Cloud: 2023.0.1
- Gestión de dependencias: Maven
- Puerto por defecto: `8888`

---

## 🚀 ¿Qué hace?

- Expone configuración externa desde un repositorio Git local.
- Los microservicios leen su configuración desde la siguiente URL:

```
http://localhost:8888/{application-name}/{profile}
```

Ejemplo:

```
http://localhost:8888/product-service/default
```

---

## 📦 Requisitos

- Java 17
- Maven
- Git (mínimo para `git init`)
- IntelliJ IDEA (u otro IDE)

---

## ⚙️ Configuración inicial del repositorio de configuración

1. Crear la carpeta:

```
C:\Users\<TU_USUARIO>\config-repo
```

2. Dentro, crear el archivo:

```
product-service.properties
```

3. Agregar el siguiente contenido de prueba:

```properties
server.port=8081
spring.application.name=product-service
```

4. Inicializar el repositorio Git:

```bash
cd C:\Users\<TU_USUARIO>\config-repo
git init
git add .
git commit -m "Init config repo"
```

---

## 🗂️ `application.properties` de este microservicio

Ubicado en `src/main/resources/application.properties`:

```properties
server.port=8888
spring.application.name=config-server

# Ruta del repositorio de configuración
spring.cloud.config.server.git.uri=file:///${user.home}/config-repo
spring.cloud.config.server.git.default-label=master
spring.cloud.config.server.git.clone-on-start=true
```

---

## 📌 Notas adicionales

- Si usas otra unidad (por ejemplo `D:`), actualiza la ruta así:

```properties
spring.cloud.config.server.git.uri=file:///D:/TuRuta/config-repo
```

- Si cambias de PC, recuerda copiar la carpeta `config-repo` y volver a ejecutar:

```bash
git init
git add .
git commit -m "Migrated config files"
```

---

## ✅ Prueba de funcionamiento

1. Ejecuta la clase `ConfigServerApplication.java`
2. Accede a:

```
http://localhost:8888/product-service/default
```

Si todo está bien, verás las propiedades en formato JSON.

---

## 👨‍💻 Autor

Desarrollado por **Edwin HC**  
📅 Abril 2025
