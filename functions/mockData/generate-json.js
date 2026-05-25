import fs from 'fs';

const teachers = [
  "Jarosław Strzelecki", "Anna Nowak", "Piotr Bukowski",
  "Ewa Barańska", "Witold Kostuj", "Jolanta Meller", "Adam Roman"
];
const classes = ["1A LO", "1 TIE", "2B LO", "3T TIE", "4INF"];
const rooms = ["Sala 18", "Sala 102", "Sala 115", "Sala 204", "Sala 214"];
const days = ["Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek"];

const lessonSlots = [
  { number: 0, time: "07:10 - 07:55" },
  { number: 1, time: "08:00 - 08:45" },
  { number: 2, time: "08:55 - 09:40" },
  { number: 3, time: "09:50 - 10:35" },
  { number: 4, time: "10:45 - 11:30" },
  { number: 5, time: "11:45 - 12:30" },
  { number: 6, time: "12:45 - 13:30" },
  { number: 6, time: "13:45 - 14:30" },
  { number: 6, time: "14:35 - 15:20" },
  { number: 6, time: "15:25 - 16:10" },
];

// Funkcja pomocnicza do tasowania tablic (klasyczny algorytm Fisher-Yates)
function shuffle(array) {
  const result = [...array];
  for (let i = result.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [result[i], result[j]] = [result[j], result[i]];
  }
  return result;
}

function generateMockData() {
  const schedule = [];

  for (const day of days) {
    for (const slot of lessonSlots) {

      // KRYTYCZNA ZMIANA: Przed każdą lekcją tasujemy pełne listy zasobów
      const availableTeachers = shuffle(teachers);
      const availableClasses = shuffle(classes);
      const availableRooms = shuffle(rooms);

      // Pobieramy 3 równoległe lekcje na tę samą godzinę
      // Ponieważ tablice są przetasowane i sięgamy po indeksy 0, 1 i 2,
      // mamy 100% pewności, że nie wylosujemy duplikatu w tym slocie czasowym.
      for (let i = 0; i < 3; i++) {
        schedule.push({
          teacherName: availableTeachers[i],
          className: availableClasses[i],
          location: availableRooms[i],
          dayOfWeek: day,
          lessonNumber: slot.number,
          time: slot.time
        });
      }
    }
  }

  // Zapisujemy strukturę do pliku JSON na dysku komputera
  fs.writeFileSync('mock_schedule.json', JSON.stringify(schedule, null, 2), 'utf-8');
  console.log(`✅ Plik 'mock_schedule.json' został pomyślnie wygenerowany na Twoim komputerze! (Liczba lekcji: ${schedule.length})`);
}

generateMockData();