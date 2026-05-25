# BFF Cordillera

Backend-for-Frontend que orquesta `ms-users` y `ms-auth`.

## Endpoints principales

- `POST /bff/usuarios/register`: crea usuario en `ms-users` y registra credencial en `ms-auth` con la `password` enviada por el cliente.
- `GET /bff/usuarios/me`: valida JWT en `ms-auth` y retorna datos completos del usuario desde `ms-users`.

## Configuracion

- `quarkus.rest-client.ms-auth.url=http://localhost:8081`
- `quarkus.rest-client.ms-users.url=http://localhost:8082`
