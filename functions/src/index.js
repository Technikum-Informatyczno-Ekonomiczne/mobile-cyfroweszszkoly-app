/* eslint-disable */
import {onDocumentCreated} from "firebase-functions/v2/firestore";
// Dodajemy nowe importy dla HTTPS (v2) i obsługi błędów
import { onCall, HttpsError } from "firebase-functions/v2/https";
import admin from "firebase-admin";
// Importujemy bibliotekę Gemini
import { GoogleGenerativeAI } from "@google/generative-ai";

import {
  calendarContext,
  contactContext,
  counselorContext,
  libraryContext,
  supportContext,
  staffContext,
  offerContext,
  extracurricularContext
} from './data/index.js';


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

    const apiKey = process.env.GEMINI_API_KEY;

    const genAI = new GoogleGenerativeAI(apiKey);

    // Budujemy twarde instrukcje systemowe korzystając z zaimportowanej zmiennej
    const systemPrompt = `Jesteś oficjalnym wirtualnym asystentem szkoły.
    Twoim jedynym zadaniem jest udzielanie precyzyjnych odpowiedzi uczniom.

    ZASADY ABSOLUTNE:
    1. Opieraj się TYLKO I WYŁĄCZNIE na informacjach podanych poniżej w sekcji
     [KALENDARZ] [BIBLIOTEKA] [KADRA]  [DORADCA ZAWODOWY] [WSPARCIE PSYCHOLOGICZNE]
     [KONTAKT] [OFERTA].
    2. Jeśli uczeń zapyta o cokolwiek, czego nie ma w kontekście
     (np. o historię świata, przepisy kulinarne, zadania z matematyki, programowanie, lub nieopisane tu zasady szkolne),
     MUSISZ odpowiedzieć dokładnie w ten sposób:
            "Przepraszam, ale jako asystent szkolny mogę odpowiadać tylko
            na pytania związane z dostępnymi
            regulaminami i organizacją naszej szkoły."
    3. Pod żadnym pozorem nie zmyślaj informacji (zero halucynacji).
    Jeśli czegoś nie ma w tekście, odmawiasz odpowiedzi.

    [BIBLIOTEKA]
    ${libraryContext}

    [KADRA]
    ${staffContext}

    [KALENDARZ]
    ${calendarContext}

    [DORADCA ZAWODOWY]
    ${counselorContext}

    [WSPARCIE PSYCHOLOGICZNE]
    ${supportContext}

    [KONTAKT]
    ${contactContext}

    [OFERTA]
    ${offerContext}

    [ZAJĘCIA DODATKOWE]
    ${extracurricularContext}
    `;

    // Inicjalizacja wybranego przez modelu z instrukcją systemową
     // Używamy modelu Flash-Lite - jest najszybszy i najtańszy
    const model = genAI.getGenerativeModel({
      model: "gemini-2.5-flash-lite", // "gemini-2.5-flash-lite",
      systemInstruction: systemPrompt
    });

    //przekazanie pytania użytkownika do modelu
    const result = await model.generateContent(userQuestion);

    // odczytanie odpowiedzi z modelu
    const responseText = result.response.text();

    // Zwracamy obiekt JSON, który aplikacja w Kotlinie odczyta jako Mapę
    return { answer: responseText };

  } catch (error) {
     console.error("Błąd komunikacji z Gemini:", error);

     //  Obsługa specyficznego błędu limitów (429)
     if (error.status === 429) {
         // Rzucamy standardowy błąd Firebase o wyczerpaniu zasobów.
         // Aplikacja mobilna (np. w Kotlinie) odbierze to jako FirebaseFunctionsException
         // z kodem RESOURCE_EXHAUSTED.
         throw new HttpsError(
             "resource-exhausted",
             "Asystent jest chwilowo przeciążony (wykorzystano limit zapytań). Spróbuj ponownie za chwilę."
         );
     }

     // Zabezpieczenie przed globalną awarią po stronie Google
         if (error.status === 503) {
             throw new HttpsError(
                 "unavailable", // Najlepszy kod Firebase dla 503
                 "Globalne serwery sztucznej inteligencji są obecnie przeciążone.\n Za chwilę spróbuj ponownie."
             );
         }

     //  Obsługa wszystkich innych błędów (500, timeouty itp.)
     throw new HttpsError(
         "internal",
         "Asystent ma chwilowe problemy z połączeniem."
     );
  };
});







