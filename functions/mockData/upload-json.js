import admin from 'firebase-admin';
import fs from 'fs';

// Wczytujemy pobrany klucz prywatny administratora
const serviceAccount = JSON.parse(fs.readFileSync('./serviceAccountKey.json', 'utf-8'));
// Inicjalizujemy połączenie z Firebase używając tego klucza
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function uploadData() {
  console.log("Wczytuję plik JSON z dysku...");

  const rawData = fs.readFileSync('mock_schedule.json', 'utf-8');
  const scheduleList = JSON.parse(rawData);

  console.log(`Rozpoczynam wysyłanie ${scheduleList.length} dokumentów do Firestore...`);

  const collectionRef = db.collection('schedules-mock');

  // Ze względu na limity Firestore, warto wysyłać zapytania asynchronicznie w małych porcjach
  let uploadedCount = 0;
  for (const lesson of scheduleList) {
    await collectionRef.add(lesson);
    uploadedCount++;
    if (uploadedCount % 10 === 0) {
      console.log(`Przesłano już ${uploadedCount}/${scheduleList.length} lekcji...`);
    }
  }

  console.log("Cała baza testowa z pliku JSON znajduje się już w chmurze Firestore w schedules-mock.");
  process.exit(0);
}

uploadData().catch(error => {
  console.error("❌ Błąd podczas migracji danych z JSON:", error);
  process.exit(1);
});