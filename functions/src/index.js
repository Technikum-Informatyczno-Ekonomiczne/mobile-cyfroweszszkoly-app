/* eslint-disable */
import {onDocumentCreated} from "firebase-functions/v2/firestore";
// Dodajemy nowe importy dla HTTPS (v2) i obsługi błędów
import { onCall, HttpsError } from "firebase-functions/v2/https";
import admin from "firebase-admin";
// Importujemy bibliotekę Gemini
import { GoogleGenerativeAI } from "@google/generative-ai";



admin.initializeApp();



// ------------------------------------------------------------------
//  POWIADOMIENIA PUSH
// ------------------------------------------------------------------
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


// ------------------------------------------------------------------
//  ASYSTENT AI GEMINI
// ------------------------------------------------------------------
export const askSchoolAssistant = onCall({ region: "europe-central2" }, async (request) => {
  // W Firebase v2 dane z aplikacji mobilnej siedzą w 'request.data'
  const userQuestion = request.data.question;

  if (!userQuestion) {
    throw new HttpsError("invalid-argument", "Brak pytania od ucznia.");
  }

  try {
    // UWAGA: Na etapie testów klucz można wpisać tu na sztywno,
    const apiKey = process.env.GEMINI_API_KEY;

    const genAI = new GoogleGenerativeAI(apiKey);

    // Używamy modelu Flash-Lite - jest najszybszy i najtańszy
    const model = genAI.getGenerativeModel({ model: "gemini-2.5-flash-lite" });

    // Kontekst dla modelu
    const systemPrompt = `Jesteś oficjalnym, wirtualnym asystentem Zespołu Szkół (SP, LO, TIE).
    Odpowiadaj zwięźle, kulturalnie i bezpośrednio na pytania.
    Pytanie ucznia: ${userQuestion}`;

    const result = await model.generateContent(systemPrompt);
    const responseText = result.response.text();

    // Zwracamy obiekt JSON, który aplikacja w Kotlinie odczyta jako Mapę
    return { answer: responseText };

  } catch (error) {
    console.error("Błąd komunikacji z Gemini:", error);
    throw new HttpsError("internal", "Asystent ma chwilowe problemy z połączeniem.");
  }
});







