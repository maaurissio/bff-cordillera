# Documentacion Tecnica: bff-cordillera

## 1. Introduccion y proposito

El componente `bff-cordillera` implementa un Backend for Frontend (BFF) encargado de orquestar llamadas entre clientes y microservicios de dominio/seguridad. Su finalidad es exponer una interfaz unificada y orientada al consumo del cliente, reduciendo complejidad de integracion en el frontend.

El BFF centraliza flujos compuestos, como registro de usuario (datos personales + credenciales) y consulta de perfil autenticado.

## 2. Arquetipo del servicio

`bff-cordillera` corresponde al arquetipo de **BFF de orquestacion**. Este arquetipo se caracteriza por:

- Adaptar contratos hacia necesidades del cliente.
- Integrar respuestas de multiples servicios.
- Encapsular reglas de flujo inter-servicio.
- Desacoplar frontend de topologia interna de microservicios.

## 3. Arquitectura del servicio

La arquitectura interna es deliberadamente liviana:

- **REST Resource:** define endpoints de fachada (`/bff/usuarios/*`).
- **REST Clients:** conectores tipados a `ms-users` y `ms-auth`.
- **DTO Layer:** contratos de entrada/salida para cliente y servicios remotos.

Ubicaciones clave:

- `src/main/java/cl/duoc/cordillera/resource/BffUsuarioResource.java`
- `src/main/java/cl/duoc/cordillera/client/UsersClient.java`
- `src/main/java/cl/duoc/cordillera/client/AuthClient.java`
- `src/main/java/cl/duoc/cordillera/dto/`

## 4. Patrones de diseno de software aplicados

1. **BFF Pattern**
   - Exposicion de endpoints orientados a experiencia de cliente.

2. **Orchestration Pattern**
   - Coordinacion secuencial entre `ms-users` y `ms-auth` para completar un caso de uso.

3. **Facade Pattern**
   - Presenta una interfaz unica para operaciones que internamente dependen de multiples servicios.

4. **DTO Pattern**
   - Permite modelar contratos de entrada para cliente y contratos internos para consumo remoto.

5. **Compensating Transaction Pattern**
   - Si falla registro en auth luego de crear usuario, se ejecuta accion compensatoria (`desactivar`).

## 5. Diseno de datos y contratos

### 5.1 Endpoints principales

- `POST /bff/usuarios/register`
- `GET /bff/usuarios/me`

### 5.2 Contratos relevantes

- Entrada de registro cliente: `BffUsuarioRegisterRequestDTO`.
- Salida de usuario: `UsuarioResponseDTO`.
- Request interno para auth: `RegisterRequestDTO`.
- Validacion de token: `TokenValidationResponseDTO`.

`POST /register` recibe `rut`, `dv`, `nombre`, `apellido`, `email` y `password`.

## 6. Flujo operativo (donde, como y por que)

### 6.1 Registro de usuario compuesto

1. El cliente invoca `POST /bff/usuarios/register`.
2. BFF valida presencia de contrasena.
3. BFF envia datos personales a `ms-users`.
4. Con `id` devuelto, BFF envia credenciales a `ms-auth`.
5. Si auth falla, BFF desactiva usuario en `ms-users` como compensacion.
6. Retorna usuario creado al cliente.

**Por que:** concentra complejidad de coordinacion en backend y evita que el frontend gestione transacciones distribuidas.

### 6.2 Consulta de perfil autenticado

1. Cliente invoca `GET /bff/usuarios/me` con bearer token.
2. BFF delega validacion de token a `ms-auth`.
3. Obtiene `usuarioId` y consulta perfil en `ms-users`.
4. Retorna perfil consolidado.

**Por que:** el cliente no necesita conocer ni consumir directamente servicios internos de seguridad y dominio.

## 7. Seguridad y consideraciones de calidad

- El BFF no almacena credenciales; solo enruta de forma transaccional.
- Requiere token bearer para consulta de perfil.
- Propaga estados HTTP de servicios aguas abajo para trazabilidad funcional.
- Debe estandarizar payload de error para mejorar experiencia de consumo en frontend.

## 8. Decisiones de diseno y justificacion tecnica

1. **Centralizar orquestacion en BFF**
   - Reduce acoplamiento del frontend con microservicios y contratos internos.

2. **Usar REST Client tipado**
   - Aumenta mantenibilidad y legibilidad frente a integracion HTTP manual.

3. **Implementar compensacion en registro**
   - Mitiga inconsistencias cuando falla segunda fase (auth) tras creacion en users.

4. **Exponer endpoint `/me` derivado de token**
   - Evita manipular identificadores de usuario en cliente para obtener perfil propio.

## 9. Riesgos, limitaciones y deuda tecnica

- Falta de manejo unificado de errores con estructura JSON de dominio.
- Compensacion actual por desactivacion puede requerir enriquecimiento para escenarios de reintento.
- Dependencia de disponibilidad de servicios remotos en tiempo real.
- Recomendable incorporar trazas correlacionadas entre BFF y microservicios.

## 10. Conclusiones

`bff-cordillera` cumple el rol de fachada y orquestador para flujos multicomponente, aplicando patrones de BFF y compensacion acordes a una arquitectura de microservicios. Su diseno simplifica integracion cliente, mejora separacion de responsabilidades y facilita evolucion de servicios internos sin exponer complejidad operacional al frontend.
