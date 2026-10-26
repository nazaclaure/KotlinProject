# KotlinProject - Examen de Desarrollo Móvil (grupal)

## Integrantes
- Nazaret Fabiane Claure Martinez
- Ariana Aylen Pita Vargas
- Maria Jose Sandoval Orellana
- Celeste Limeira Quispe Sarmiento
- Valery Marcela Lamas Soliz

## Usuario de prueba
- Correo: docente@test.com
- Contraseña: Docente2026

(Cuenta creada solo para la revisión del examen.)

## Combinación elegida (100 puntos)
| Ejercicio | Puntos | Responsables |
|---|---|---|
| E50-3 FCM externa + notificación local en foreground + token en Firestore | 50 | Celeste Limeira (token en Firestore), Maria Jose Sandoval (servicio FCM y notificación), Ariana Pita (DetailActivity) |
| E25-5 Notificaciones internas con NotificationManager | 25 | Nazaret Claure |
| E25-1 Remote Config con caché en SharedPreferences | 25 | Valery Lamas |

## Cómo compilar y ejecutar
1. Abrir el proyecto en Android Studio, rama `examen-grupal`.
2. Elegir la variante `dev` y ejecutar `androidApp` en un emulador o celular con Android 13 o superior.
3. Por consola: `.\gradlew :androidApp:assembleDevDebug`. El APK queda en `androidApp\build\outputs\apk`.
4. Firebase: el archivo `androidApp/google-services.json` ya está en el repositorio (proyecto `kotlinproject-69db0`).
5. Iniciar sesión con el usuario de prueba de la sección anterior.

## Cómo probar cada ejercicio
**E25-5 (notificación local):** iniciar sesión, en la pantalla de películas tocar "Probar notificación", aceptar el permiso, ver la notificación en la bandeja y tocarla para abrir la app.

**E25-1 (Remote Config):** abrir la pantalla de configuración y ver los 3 valores (`welcome_message`, `max_items`, `feature_enabled`). Cambiar un valor en la consola de Firebase, publicar y tocar "Refrescar". Con modo avión, reiniciar la app y ver los valores guardados con el badge "offline".

**E50-3 (FCM):** iniciar sesión y verificar en Firestore `usuarios/{uid}/token`. Enviar un mensaje de prueba desde la consola de Firebase (Messaging) con el dato `message_id`: con la app abierta aparece una notificación local; con la app cerrada, la del sistema. Al tocarla se abre `DetailActivity` con el `message_id`.

## Decisiones técnicas
- Kotlin Multiplatform con Compose, Koin y arquitectura limpia (dominio, datos, presentación).
- Los componentes visuales vienen del Design System propio (`edu.ucb.ds`).
- Textos en español e inglés con recursos de Compose Multiplatform.
- Notificaciones locales: el código común usa un puente (`LocalNotifier`) y Android lo implementa con `NotificationHelper`.
- Cada integrante trabajó en su propia rama (`examen/<nombre>-...`) y se juntaron en `examen-grupal`.