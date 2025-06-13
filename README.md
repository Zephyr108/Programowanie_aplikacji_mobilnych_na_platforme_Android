# 🤖 Laboratoria Android – Zestaw Aplikacji Mobilnych

Zestaw czterech aplikacji mobilnych opracowanych w ramach ćwiczeń z programowania na system Android. Każdy projekt demonstruje inne zagadnienia – od podstawowych interfejsów użytkownika po obsługę baz danych i fragmentów.

---

## 📦 Projekty

### 1️⃣ Lab1 – Kalkulator ocen

- Aplikacja do obliczania średnich ocen.
- Obsługa wielu ekranów (layouty `activity_main.xml`, `activity_grades_input.xml` itd.).
- Komponenty `TabAdapter`, `GradesModel`.
- Java + klasy XML layoutu.

---

### 2️⃣ Lab2 – Lista kontaktów

- Aplikacja CRUD do zarządzania listą telefonów.
- Wykorzystuje Room (SQLite), ViewModel oraz RecyclerView.
- Architektura: MVVM.
- Kotlin + Jetpack Libraries.

---

### 3️⃣ Lab3 – Pobieranie danych z Internetu

- Aplikacja pobierająca dane o plikach z sieci.
- Zastosowano AsyncTask (i/lub `IntentService`), serwisy, oraz obsługę JSON.
- Klasy `AsyncDownloadInfo`, `DownloadInfoTask`, `DownloadFileService`.

---

### 4️⃣ Lab4 – Interaktywna galeria obrazów

- Obsługuje fragmenty (portret/landscape).
- Dynamiczne interfejsy, adapter `ImageListAdapter`.
- Nawigacja za pomocą `NavGraph`.
- Obsługa rysowania na ekranie (DrawingSurface).

---

## 🔧 Technologie

- ☕ Java & 🧪 Kotlin (zależnie od projektu)
- 🧱 XML layouty i zasoby
- 🧩 Room, RecyclerView, ViewModel
- 🔁 Fragments, Navigation Component
- 📦 Gradle (indywidualny dla każdego projektu)

---

## 📁 Struktura katalogów

```
LaboratoriaAndroid/
├── Lab1/
├── Lab2/
├── Lab3/
└── Lab4/
```

Każdy folder zawiera kompletny projekt Android (`build.gradle`, `app/`, `src/`, itd.).

---

## 🚀 Uruchomienie projektów

1. Otwórz Android Studio.
2. Wybierz opcję **"Open an existing project"**.
3. Wskaż katalog jednego z projektów (`Lab1`, `Lab2`, itd.).
4. Poczekaj na synchronizację Gradle i uruchom aplikację na emulatorze lub urządzeniu.

---

## 📄 Licencja

Projekty do celów edukacyjnych – można dowolnie modyfikować i rozwijać 🧪

---
