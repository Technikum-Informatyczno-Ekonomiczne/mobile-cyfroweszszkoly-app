/* eslint-disable */
import {onDocumentCreated} from "firebase-functions/v2/firestore";
import * as admin from "firebase-admin";

admin.initializeApp();

export const onAlertCreated = onDocumentCreated("global_alerts/{alertId}", async (event) => {
  const snap = event.data;
  if (!snap) return;

  const newAlert = snap.data();
  const title = newAlert.title || "Nowy komunikat!";
  const msg = newAlert.message || "Sprawdź aplikację, dodano nowy alert.";

  const payload = {
    notification: {
      title: title,
      body: msg,
    },
    topic: "global_alerts",
  };

  try {
    const res = await admin.messaging().send(payload);
    console.log("Rozesłano alert do uczniów. ID:", res);
  } catch (error) {
    console.error("Błąd podczas wysyłania powiadomienia:", error);
  }
});