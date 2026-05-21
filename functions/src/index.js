/* eslint-disable */
import {onDocumentCreated} from "firebase-functions/v2/firestore";
// Dodajemy nowe importy dla HTTPS (v2) i obsługi błędów
import { onCall, HttpsError } from "firebase-functions/v2/https";
import admin from "firebase-admin";
// Importujemy bibliotekę Gemini
import { GoogleGenerativeAI } from "@google/generative-ai";
import {libraryContext} from "./libraryData.js";
import {stuffContext} from  "./stuffData.js";
import {calendarContext} from  "./calendarData.js";



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

    // Budujemy twarde instrukcje systemowe korzystając z zaimportowanej zmiennej
    const systemPrompt = `Jesteś oficjalnym wirtualnym asystentem szkoły.
    Twoim jedynym zadaniem jest udzielanie precyzyjnych odpowiedzi uczniom.

    ZASADY ABSOLUTNE:
    1. Opieraj się TYLKO I WYŁĄCZNIE na informacjach podanych poniżej w sekcji
     [KALENDARZ] [BIBIOTEKA] [KADRA].
    2. Jeśli uczeń zapyta o cokolwiek, czego nie ma w kontekście (np. o historię świata, przepisy kulinarne, zadania z matematyki, programowanie, lub nieopisane tu zasady szkolne), MUSISZ odpowiedzieć dokładnie w ten sposób: "Przepraszam, ale jako asystent szkolny mogę odpowiadać tylko na pytania związane z dostępnymi regulaminami i organizacją naszej szkoły."
    3. Pod żadnym pozorem nie zmyślaj informacji (zero halucynacji). Jeśli czegoś nie ma w tekście, odmawiasz odpowiedzi.

    [BIBIOTEKA]
    ${libraryContext}

    [KADRA]
    ${stuffContext}

    [KALENDARZ]
    ${calendarContext}
    `;

    // Inicjalizacja wybranego przez Ciebie, działającego modelu z instrukcją systemową
     // Używamy modelu Flash-Lite - jest najszybszy i najtańszy
    const model = genAI.getGenerativeModel({
      model: "gemini-2.5-flash", // "gemini-2.5-flash-lite",
      systemInstruction: systemPrompt
    });
    const result = await model.generateContent(systemPrompt);
    const responseText = result.response.text();

    // Zwracamy obiekt JSON, który aplikacja w Kotlinie odczyta jako Mapę
    return { answer: responseText };

  } catch (error) {
    console.error("Błąd komunikacji z Gemini:", error);
    throw new HttpsError("internal", "Asystent ma chwilowe problemy z połączeniem.");
  }
});







