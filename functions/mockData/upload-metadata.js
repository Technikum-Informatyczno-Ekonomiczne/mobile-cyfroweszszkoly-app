import admin from 'firebase-admin';
import fs from 'fs';

const serviceAccount = JSON.parse(fs.readFileSync('./serviceAccountKey.json', 'utf-8'));

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function generateAndUploadMetadata() {
  console.log("⏳ Wczytuję plik planu zajęć...");
  const rawData = fs.readFileSync('mock_schedule.json', 'utf-8');
  const scheduleList = JSON.parse(rawData);

  // Używamy struktury Set, aby automatycznie odrzucić duplikaty
  const teachersSet = new Set();
  const roomsSet = new Set();
  const classesSet = new Set();

  scheduleList.forEach(item => {
    if (item.teacherName) teachersSet.add(item.teacherName);
    if (item.location) roomsSet.add(item.location);
    if (item.className) classesSet.add(item.className);
  });

  // Tworzymy jeden spłaszczony obiekt z tablicami
  const metadata = {
    teacherNames: Array.from(teachersSet).sort(),
    roomNames: Array.from(roomsSet).sort(),
    classNames: Array.from(classesSet).sort()
  };

  console.log("🚀 Wysyłam dokument słownika 'metadata/schoolData' do Firestore...");

  // Zapisujemy pod konkretną, stałą ścieżką
  await db.collection('metadata').doc('schoolData').set(metadata);

  console.log(`✅ Sukces! Słownik został zaktualizowany.`);
  console.log(`   - Nauczycieli: ${metadata.teacherNames.length}`);
  console.log(`   - Sal: ${metadata.roomNames.length}`);
  console.log(`   - Klas: ${metadata.classNames.length}`);

  process.exit(0);
}

generateAndUploadMetadata().catch(error => {
  console.error("❌ Błąd generowania słownika:", error);
  process.exit(1);
});