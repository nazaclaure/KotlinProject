const express = require("express");
const { initializeApp, cert } = require("firebase-admin/app");
const { getDatabase } = require("firebase-admin/database");
const { getMessaging } = require("firebase-admin/messaging");
const serviceAccount = require("./serviceAccountKey.json");

initializeApp({
  credential: cert(serviceAccount),
  databaseURL: "https://kotlinproject-69db0-default-rtdb.firebaseio.com",
});

const db = getDatabase();
const messaging = getMessaging();
const app = express();
app.use(express.json());

const CHANNEL_ID = "notificaciones_generales";
const TOKENS_INVALIDOS = [
  "messaging/registration-token-not-registered",
  "messaging/invalid-registration-token",
];

// Para comprobar que el servidor esta vivo
app.get("/health", (req, res) => {
  res.json({ ok: true });
});

// Envia una notificacion push a todos los dispositivos de un usuario
// Body: { "userId": "1", "titulo": "...", "contenido": "...", "icono": "..." }
app.post("/send", async (req, res) => {
  const { userId, titulo, contenido, icono } = req.body;

  if (!userId || !titulo || !contenido) {
    return res
      .status(400)
      .json({ error: "Faltan campos: userId, titulo y contenido son obligatorios" });
  }

  try {
    // 1. Buscar los tokens de ese usuario en la base de datos
    const snap = await db.ref("device_tokens").once("value");
    const tokens = [];
    snap.forEach((child) => {
      if (child.val().userId === String(userId)) {
        tokens.push(child.key);
      }
    });

    if (tokens.length === 0) {
      return res.status(404).json({ error: "El usuario no tiene dispositivos registrados" });
    }

    // 2. Enviar el mensaje con el payload titulo / contenido / icono
    const response = await messaging.sendEachForMulticast({
      tokens,
      notification: { title: titulo, body: contenido },
      data: { titulo, contenido, icono: icono || "" },
      android: {
        priority: "high",
        notification: { channelId: CHANNEL_ID },
      },
    });

    // 3. Limpiar tokens que ya no sirven
    const limpiados = [];
    response.responses.forEach((r, i) => {
      if (!r.success && r.error && TOKENS_INVALIDOS.includes(r.error.code)) {
        limpiados.push(db.ref("device_tokens/" + tokens[i]).remove());
      }
    });
    await Promise.all(limpiados);

    res.json({
      enviados: response.successCount,
      fallidos: response.failureCount,
    });
  } catch (e) {
    console.error(e);
    res.status(500).json({ error: e.message });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log("Backend de push escuchando en http://localhost:" + PORT));