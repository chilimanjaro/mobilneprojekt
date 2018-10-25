# Projekt aplikacji mobilnej
Jest to projekt wykonany jako zaliczenie laboratorium z Programowania aplikacji desktop i na urządzenia mobilne. Tematem tej pracy jest stworzenie listy klejów i fug.

## Zawartość aplikacji
Aplikacja składa się z 2 aktywności i 3 fragmentów. Przynależność fragmentów wygląda w następujący spobób:
* MainActivity *(Główna aktywność)*
  * ListFragment *(Fragment do wyświetlania listy)*
  * DetailsFragment *(Fragment do wyświetlania szczegółów przedmiotu)*
* NewItemActivity *(Aktywność tworzenia nowego przedmiotu)*
  * AddItemFragment *(Fragment do wyświetlania formularza tworzenia nowego przedmiotu)*
  
Utworzone są jeszcze 4 interfejsy wywołań zwrotnych:  
* ActivityCallback *(odpowiada za wyświetlenie nowego fragmentu w aktywności)*
* ListToFragmentCallback (odpowiada za przesłanie listy z aktywności do fragmentu i powiadomienie o zmianach)
* AddNewItemCallback *(odpowiada za przesłanie nowego przedmiotu do listy)*
* DeleteItemCallback *(odpowiada za usunięcie przedmiotu z listy)*
  
## Sposób przechowywania danych
Dane przechowywane są w pliku data.json w resources/raw. Łącznie są 22 przedmioty dodane do listy. Format JSON wygląda następująco:
```json
  {
    "type": 0,
    "name": "tytuł przedmiotu",
    "desc": "opis przedmiotu",
    "attributes": [
      "lista właściwości"
    ],
    "parameters": [
      "lista parametrów"
    ],
    "image": "nazwa_pliku_zdjęcia"
  }
```
Typ może przyjąć wartości:
* 0 - klej
* 1 - fuga

## Działanie aplikacji
Podstawową aktywnością przy uruchomieniu jest *MainActivity*. Podczas tworzenia aktywności, tworzona jest lista na podstawie pliku podanego wyżej. Po utworzeniu listy, wyświetlany jest fragment *ListFragment*, do którego ta lista jest wysyłana za pomocą *ListToFragmentCallback*. W tym fragmencie wyświetlana jest lista przedmiotów z możliwością przejścia do *DetailsFragment* po kliknięciu na jakąkolwiek pozycję. W *DetailsFragment* przycisk do usunięcia wyświetlonego przedmiotu z listy, który wysyła informację o tym do *MainActivity* za pomocą *DeleteItemCallback*.

W górnym pasku znajduje się przycisk, który przenosi do *NewItemActivity*. W nim wyświetlany jest *AddItemFragment* z formularzem dla nowego przedmiotu, który jest podzielony na 3 kategorie. Kliknięcie przycisków *Dodaj Pole*, doda nowe pola tekstowe nad wybranymi przyciskami. Kliknięcie zdjęcia wywoła prośbę o nadanie uprawnień *(Dangerous Permissions)*, żeby można było wybrać zdjęcie z galerii (UWAGA: Google Photos nie działa, ze względu na blokowanie Uri do aktywności, w której uprawnienia zostały nadane).

Aby można było dodać przedmiot, trzeba wypełnić wszystkie pola, ponieważ jest zawarta walidacja przedmiotu. Lista walidacji wygląda następująco:
* Puste pole nazwy
* Puste pole opisu
* Brak pól właściwości
* Puste pole właściwości
* Brak pól parametrów
* Puste pole parametrów
* Brak zdjęcia

Po kliknięciu przycisku *Dodaj przedmiot*, przedmiot wysyłany jest z *NewItemActivity* do *MainActivity* za pomocą *AddNewItemCallback*, a następnie dodany do listy przedmiotów. Gdy przedmiot jest dodany, wysyłane jest powiadomienie do *ListFragment*, w celu odświeżenia widoku.
