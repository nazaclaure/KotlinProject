# Backend de notificaciones push

Servidor Node.js con Firebase Admin SDK. Lee los tokens de `device_tokens` (Realtime Database)
y envia notificaciones FCM con el payload `titulo`, `contenido` e `icono`.

## Configuracion
1. En Firebase Console > Configuracion del proyecto > Cuentas de servicio, generar una clave privada.
2. Guardar el archivo en esta carpeta como `serviceAccountKey.json` (esta en .gitignore, nunca se sube).
3. `npm install`
4. `npm start`  (escucha en http://localhost:3000)

## Uso
POST /send
{ "userId": "1", "titulo": "Hola", "contenido": "Mensaje", "icono": "info" }

Respuesta: { "enviados": 1, "fallidos": 0 }