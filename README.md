# System Zarządzania Serwisem Rowerowym 🚲

Kompleksowa aplikacja okienkowa do obsługi procesów biznesowych w serwisie rowerowym. Projekt realizuje pełny cykl życia zlecenia: od rejestracji klienta i przyjęcia roweru na magazyn, przez ewidencję napraw i zużytych części, aż po finalne rozliczenie. 

Logika biznesowa została w dużej mierze przeniesiona na stronę silnika relacyjnej bazy danych, co zapewnia wysoką wydajność i spójność informacji.

## 🛠 Technologie
* **Język:** Java
* **Baza danych:** PostgreSQL (procedury składowane, funkcje, wyzwalacze)
* **Komunikacja:** JDBC 
* **Narzędzie budowania:** Maven

## ✨ Główne funkcjonalności
* **Zarządzanie uprawnieniami:** System logowania z rygorystycznym podziałem na role (Kierownik, Serwisant, Administrator). Interfejs dynamicznie dostosowuje się do uprawnień aktywnego użytkownika.
* **Gospodarka magazynowa:** Kontrola limitów miejsc dla przyjmowanych rowerów, weryfikacja dostępności części zamiennych oraz powiadomienia o przekroczeniu stanów minimalnych.
* **Obsługa zleceń serwisowych:** Płynna zmiana statusów naprawy ("Przyjęte", "W trakcie", "Zakończone"), przypisywanie serwisantów oraz dodawanie konkretnych usług i części do zlecenia.
* **Automatyzacja rozliczeń:** Całkowity koszt naprawy (części + robocizna) jest wyliczany bezpiecznie po stronie bazy danych przy zamykaniu zlecenia.
* **Bezpieczeństwo:** Pełna ochrona przed atakami typu SQL Injection dzięki zastosowaniu parametryzowanych zapytań (`PreparedStatement`) oraz hermetyzacji operacji wewnątrz procedur składowanych.

## 📄 Dokumentacja
Pełna specyfikacja techniczna systemu znajduje się w katalogu `docs/`. Dokumentacja zawiera:
* Analizę wymagań i scenariusze przypadków użycia (UML)
* Logiczny i fizyczny model relacyjnej bazy danych (ERD)
* Diagramy klas aplikacji
* Schematy algorytmów zarządzających stanem magazynowym

## 👥 Autorzy
Projekt został zrealizowany w ramach zajęć na Politechnice Wrocławskiej (Wydział Informatyki i Telekomunikacji).
* Mateusz Brokos
* Szymon Tyliński
* Michał Hendrysiak
