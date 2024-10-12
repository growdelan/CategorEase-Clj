
# Categorease

Categorease to narzędzie napisane w języku Clojure, które automatycznie organizuje pliki w wybranym katalogu. Program wykorzystuje model językowy do klasyfikacji plików na podstawie ich nazw i przenosi je do odpowiednich podkatalogów. Dzięki temu utrzymanie porządku w systemie plików staje się prostsze i bardziej efektywne.

## Funkcjonalności

- **Automatyczna klasyfikacja plików** na podstawie ich nazw przy użyciu modelu językowego.
- **Przenoszenie plików** do odpowiednich podkatalogów zgodnie z ustalonymi kategoriami.
- **Tworzenie potrzebnych katalogów** jeśli jeszcze nie istnieją.
- **Obsługa wielu kategorii**, takich jak muzyka, dokumenty, obrazy, wideo i inne.

## Wymagania wstępne

- Zainstalowany [Leiningen](https://leiningen.org).
- Zainstalowana [Ollama](https://ollama.com)

## Instalacja

1. **Klonuj repozytorium** lub pobierz pliki projektu do wybranego katalogu.

2. **Zainstaluj zależności** wymagane przez projekt (jeśli istnieją pliki konfiguracyjne takie jak `project.clj` lub `deps.edn`, użyj odpowiednich narzędzi).

3. **Ustaw zmienne środowiskowe**:

Zmienne środowiskowe są domyślnie ustawione w `.lein-env` dla Ollamy.

## Sposób użycia

### Domyślne ustawienia

Domyślnie program przetwarza pliki znajdujące się w katalogu `~/Downloads`. Jeśli chcesz uporządkować właśnie ten katalog, wystarczy uruchomić program bez dodatkowych zmian.

### Zmiana katalogu do sprzątania

Jeśli chcesz ustawić inny katalog do sprzątania:

1. **Edytuj plik `core.clj`** znajdujący się w głównym katalogu projektu.

2. **Znajdź funkcję `get-download-path`**:

   ```clojure
   (defn get-download-path []
     (str (System/getProperty "user.home") "/Downloads"))
   ```

3. **Zmień zwracaną ścieżkę** na ścieżkę do wybranego przez Ciebie katalogu. Na przykład:

   ```clojure
   (defn get-download-path []
     "/ścieżka/do/twojego/katalogu")
   ```

4. **Zapisz zmiany** w pliku.

### Uruchomienie programu

1. **Otwórz terminal** i przejdź do katalogu z projektem.

2. **Uruchom program** za pomocą komendy:

   ```bash
   lein run
   ```

   Lub zbuduj uberjar

   ```bash
   lein uberjar
   ```

   a następnie uruchom:

   ```bash
   java -jar target/uberjar/categorease-0.1.0-SNAPSHOT-standalone.jar
   ```

3. Program utworzy potrzebne podkatalogi w wybranym katalogu i przeniesie pliki do odpowiednich kategorii.

## Kategorie plików

Program wykorzystuje następujące kategorie do organizacji plików:

- `music` - pliki muzyczne
- `documents` - dokumenty tekstowe i PDF
- `installations` - pliki instalacyjne (np. `.exe`, `.msi`)
- `data` - arkusze kalkulacyjne, bazy danych
- `images` - pliki graficzne
- `videos` - pliki wideo
- `archives` - archiwa (np. `.zip`, `.rar`)
- `others` - pozostałe pliki niepasujące do powyższych kategorii

## Informacje dodatkowe

- **Model językowy**: Program domyślnie używa modelu o nazwie `"llama3.2"` za pośrednictwem [Ollamy](https://ollama.com). Jeśli chcesz użyć innego modelu, zmień wartość zmiennej `model-name` w pliku `core.clj`.

- **Błędy i wyjątki**: Jeśli podczas działania programu wystąpią błędy, zostaną one wyświetlone w konsoli wraz z informacją o pliku, którego dotyczą.

- **Rozszerzenie funkcjonalności**: Możesz dodać własne kategorie lub zmienić istniejące, modyfikując zmienną `categories` w pliku `core.clj`.

## Przykład

Po uruchomieniu programu pliki w katalogu `~/Downloads` zostaną przeniesione do odpowiednich podkatalogów:

- `~/Downloads/music/`
- `~/Downloads/documents/`
- `~/Downloads/installations/`
- `~/Downloads/data/`
- `~/Downloads/images/`
- `~/Downloads/videos/`
- `~/Downloads/archives/`
- `~/Downloads/others/`

## Uwagi

- **Kopia zapasowa**: Przed uruchomieniem programu zaleca się wykonanie kopii zapasowej katalogu, aby uniknąć przypadkowej utraty danych.

- **Uprawnienia**: Upewnij się, że masz odpowiednie uprawnienia do odczytu i zapisu w wybranym katalogu.

- **Środowisko uruchomieniowe**: Program został przetestowany na systemach MacOS. Działanie na innych systemach może wymagać dodatkowych modyfikacji.

