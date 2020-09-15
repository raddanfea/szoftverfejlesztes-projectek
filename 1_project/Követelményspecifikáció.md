1. Jelenlegi helyzet
 - Jelenlegi amőba játékokat előre meghatározott méretű pályakon játszák, például 3x3, 4x4, emiatt a két játékos között fennáll a döntetlen lehetősége.
 - A mobilos amőba játékok nem rendelkeznek megfelelő oldalcsere mechanikával, emiatt fennáll a fairplay elvesztésének veszélye.
 - Véletlen hibák gyakran előfordulnak egy eszközös játékokkal.

2. Vágyálom rendszer
  - Végtelen méretű pálya.
  - Minden játékmentben van győztes.
  - X és O felváltani tetszőleges ikonokkal.
  - Győzelmet hangüzenet is kíséri.
  - Háttérzene.
  - Esztétikus, fiatalaknak szóló grafika.

3. Jelenlegi üzleti folyamatok
  3.1 A szoftver véletlenszerűen választ valakit a játékosok közül hogy ki kezdjen
  3.2 A képernyőn megjelenik egy előre meghatározott méretű pálya
  3.2.1 Az első játékos választ egy mezőt => megérinti ott a képernyőt => a választott mezőn megjelenik a saját jele (o vagy x)
  3.2.2 A szoftver leellenőrzi, hogy van e az első játékosnak függőlegesen vagy vízszintesen vagy átlósan, megszakítás nélkül egymás után a nyeréshez szükséges mennyiségű jel (o vagy x)
  3.2.2.1 Ha a feltétel igaz => a játék véget ér => győzött az első játékos
  3.2.2.2 Ha a feltétel hamis => a második játékos következik
  3.2.3 A második játékos választ egy mezőt => megérinti ott a képernyőt => a választott mezőn megjelenik a saját jele (o vagy x)
  3.2.4 A szoftver leellenőrzi, hogy van e a második játékosnak függőlegesen vagy vízszintesen vagy átlósan, megszakítás nélkül egymás után a nyeréshez szükséges mennyiségű jel (o vagy x)
  3.2.4.1 Ha a feltétel igaz => a játék véget ér => győzött a második játékos
  3.2.4.2 Ha a feltétel hamis => az első játékos következik
  3.3 Ha a pálya betelt és nincs győztes => a játék véget ér döntetlennel
4. Igényelt üzleti folyamatok
  - Grafikus megjelenítés
  - Játékosok bevitele az adatbázisba
  - Győztes biztosítása
5. Rendszerre vonatkozó szabályok:
A szoftver megvalósítása Java programozási nyelvben kell hogy történjen.
Az UI megvalósításához xml-elt kell hogy használni.
Mentési fájl JSON formátumban.
10 másodperces lépési időkorlát.
mp3-as kiterjesztésű hangfájlok kezelése.
Gradle fejlesztői projektautomatizációs eszköz használata.
Képfájlok jpg/png formátumúak legyenek.

6. Követelménylista
  - K01 Egyszerű, letisztult környezet
  - K02 Játszmák alatt háttérzene
  - K03 Győztest jelző hangüzenet
  - K04 Döntetlen játékállás esetén bővülő pálya
  - K05 Játékosok adatbázisban való eltárolása
7. Fogalomszótár
  - Végtelen méretű pálya: Amennyiben a játékosok felhasználták az összes mezőt, de az állás döntetlen, újabb mezőkkel bővüljön a pálya.
  - Fairplay: sportszerűség
