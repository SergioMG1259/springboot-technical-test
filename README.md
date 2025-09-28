# Technical Test – Spring Boot API

**Autor:** Sergio Martin Guanilo Gonzales  
---

## Tecnologías y versiones usadas

- **Java:** 17
- **Spring Boot:** 3.4.10
- **Maven:** 3.9.9
- **PostgreSQL:** 17.6
- **Postman**

---

## Clonar el repositorio

Clonar el repositorio del proyecto ejecutando:

```bash
git clone https://github.com/SergioMG1259/springboot-technical-test.git
```

## Configuración del entorno

Por motivos de seguridad, la aplicación **no incluye credenciales** en el repositorio. Se utiliza un archivo `.env` para definir variables sensibles como:

- URL de la base de datos  
- Usuario y contraseña de PostgreSQL  
- Secret JWT

---

## Base de datos

Se debe crear una base de datos en PostgreSQL llamada `technical_test_db`.  
Asegúrese de tener un usuario con permisos suficientes para conectarse y modificar esta base.

### Archivo `.env`

Cree un archivo llamado `.env` en la raíz del proyecto (al mismo nivel que `src/` y `pom.xml`).  
Incluya las siguientes variables y ajuste los valores según su entorno:

```env
# Puerto del servidor donde se ejecuta la aplicación
SERVER_PORT=8105  # Puede cambiarlo según su preferencia

# Configuración de la base de datos
SPRING_DATASOURCE_URL= url/technical_test_db  # Cambie por la url de la DB creada
SPRING_DATASOURCE_USERNAME=su_usuario  # Ingrese su usuario de PostgreSQL
SPRING_DATASOURCE_PASSWORD=su_contraseña  # Ingrese su contraseña de PostgreSQL

# Secret para JWT
# Puede generar uno usando herramientas en línea
JWT_SECRET=su_secret_base64

# Tiempo de validez del access token JWT en segundos
# Por ejemplo, 1200 segundos equivalen a 20 minutos
JWT_VALIDITY_IN_SECONDS=1200  # Tiempo de validez del access token JWT en segundos (20 minutos)
```

---

## Carga de datos iniciales

La aplicación incluye un script para poblar la base de datos con los datos iniciales necesarios para las pruebas.
El script se encuentra en el directorio `src/main/resources` y tiene el nombre: data.sql

**Importante:** El password para todos los usuarios del script es 123456789, solo para efectos de facilitar la prueba.

Para que el script se ejecute correctamente, ajuste la propiedad en `application.properties`:

```properties
spring.sql.init.mode=always
```
**Nota:** Después de la primera ejecución, se debe cambiar el valor a:

```properties
spring.sql.init.mode=never
```
De esta manera, se evita que los datos se dupliquen en ejecuciones posteriores.

También es recomendable que ejecute en PGAdmin lo siguiente si es que ejecutó el script sql:
```
SELECT setval('person_id_seq', (SELECT MAX(id) FROM person), true);
SELECT setval('employee_id_seq', (SELECT MAX(id) FROM employee), true);
SELECT setval('app_user_id_seq', (SELECT MAX(id) FROM app_user), true);
SELECT setval('specialty_id_seq', (SELECT MAX(id) FROM specialty), true);
SELECT setval('attention_id_seq', (SELECT MAX(id) FROM attention), true);
SELECT setval('patient_id_seq', (SELECT MAX(id) FROM patient), true);
```
---

## Reglas de negocio principales

Antes de interactuar con la API, se recomienda comprobar que los datos iniciales se hayan cargado correctamente en la base de datos.  
Para esto, se pueden ejecutar consultas en PostgreSQL usando PGAdmin.

Para el correcto uso de la API y la coherencia de los datos, se consideran las siguientes reglas:

- La creación de usuarios de tipo **Admin** y **Doctor** solo puede ser realizada por un usuario con rol **Admin**.
- Cualquier persona puede registrarse como **Patient** sin requerir autorización previa.
- La gestión de especialidades médicas (**Specialty**) —creación, modificación o eliminación— está restringida a usuarios con rol **Admin**.
- La creación de atenciones (**Attention**) solo puede ser realizada por usuarios con rol **Admin** o **Doctor**.
- Un **Admin** puede crear atenciones médicas para otros doctores, pero un **Doctor** no puede asignar otros doctores a una atención médica, solo a el mismo.
- Cada atención tiene una fecha y hora de inicio (**startDate**) y de fin (**endDate**).
    - La fecha de inicio debe ser la actual o en el futuro; no se permiten fechas pasadas.
    - Por defecto, la duración de la atención es de 45 minutos, aunque puede ser finalizada de inmediato o modificada según se requiera.
- Se impide la superposición de atenciones para un mismo **Patient** o un mismo **Doctor** dentro del mismo intervalo de tiempo.
    - Otro paciente o doctor distinto puede tener atenciones que coincidan en horario con otros pacientes o doctores.
    - Esta regla asegura que ningún paciente ni doctor tenga dos atenciones al mismo tiempo.
- La autenticación en la API se realiza mediante **userName** y **password**.
- Tanto el **email** como **userName** son únicos.

---

## Endpoints de la API

A continuación se listan los endpoints disponibles, su función y los roles autorizados para acceder a ellos:

| Método | Endpoint | Descripción                                                      | Roles autorizados |
|--------|---------|------------------------------------------------------------------|-----------------|
| POST | `/api/v1/auth/createPatient` | Registrar un paciente                                            | Sin restricción |
| POST | `/api/v1/auth/createAdmin` | Registrar un admin (employee)                                    | Admin |
| POST | `/api/v1/auth/createDoctor` | Registrar un doctor (employee)                                   | Admin |
| POST | `/api/v1/login` | Autenticación y obtención de token JWT                           | Sin restricción |
| POST | `/api/v1/specialties` | Registrar una nueva especialidad médica                          | Admin |
| PUT | `/api/v1/specialties/{id}` | Modificar una especialidad médica                                | Admin |
| DELETE | `/api/v1/specialties/{id}` | Eliminar una especialidad médica                                 | Admin |
| GET | `/api/v1/attentions` | Listar todas las atenciones (paginado)                           | Admin |
| GET | `/api/v1/attentions/me/patient` | Listar todas las atenciones de un paciente específico (paginado) | Patient |
| GET | `/api/v1/attentions/me/doctor` | Listar todas las atenciones de un doctor específico (paginado)   | Doctor |
| POST | `/api/v1/attentions` | Crear una nueva atención médica                                  | Admin, Doctor |
| PATCH | `/api/v1/attentions/{id}` | Finalizar una atención médica en curso                           | Admin, Doctor |
| PUT | `/api/v1/attentions/{id}` | Modificar una atención médica existente                          | Admin, Doctor |
| DELETE | `/api/v1/attentions/{id}` | Eliminar una atención médica                                     | Admin |

---

## Pruebas en Postman

En la carpeta resources del proyecto, también encontrará un archivo .json con el nombre **Technical Test API.postman_collection**. Este archivo debe ser importado en Postman para realizar las pruebas.

Algunos usuarios son:

- Alice19800510 de rol ADMIN
- Eva19921210 de rol DOCTOR
- Jack19820618 de rol PATIENT

La contraseña para todos los usuarios es: 123456789

Con estas credenciales podrá obtener el token para acceder a los distintos endpoints.

---

## Autenticación y uso del token JWT

La API utiliza **JWT (JSON Web Token)** para proteger los endpoints y controlar el acceso según el rol del usuario.

### Reglas de autenticación

- Para acceder a la mayoría de los endpoints, se requiere **Bearer Token** en el encabezado de la solicitud HTTP.
- Los endpoints que **no requieren autenticación** son:
  - `POST /api/v1/auth/createPatient` → para registrar pacientes.
  - `POST /api/v1/login` → para autenticarse y obtener el token JWT.
- Todos los demás endpoints deben incluir el token obtenido al hacer login.

### Formato del encabezado Authorization

Cuando se realiza una petición protegida, se debe incluir el token en el encabezado de la siguiente manera:

**Authorization: Bearer <token_jwt>**

Se sugiere usar **Postman** o cualquier cliente HTTP para probar los endpoints, configurando correctamente el token en el encabezado `Authorization`. Esto permitirá verificar tanto la funcionalidad como las restricciones de acceso según rol.
