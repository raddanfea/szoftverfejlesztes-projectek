# 1. Jelenlegi helyzet
 Az amőba vagy más néven tic tac toe vagy X-O, egy kétszemélyes absztrakt stratégiai táblás játék, a gomoku változata. Annak
 ellenére hogy egy tábla játéka az őse a legtöbb ember egy papírra rajzolt 3X3 pályán játsza.A 3X3 pályán az nyer aki előbb helyez el
 a saját figurájábol 3 barabot egy viszintes/függőleges oszlopban vagy átlósan. A modern 
 világhoz alkalmazkodott ez a játék is, így már létezik belőle online elérhető, telefonra telepíthető változat is.
 Ezekben az appokban az előre megalkotott 3X3 pályán lehet csak játszani, ami lehet egyeseknek már unalmas vagy épp nagyobb
 méretben gondolkodnak, esetleg nem szeretik eldöntetlenül abba hagyni a játékot és mindenképp győznie kell valakinek. 
 Esetlegesen személyesebbé akarják tenni azzal, hogy saját képet valaszthatnak maguknak. 
# 2. Vágyálom rendszer
A mi általunk megalkotott program ezekben segíti a játékosokat, megalkottuk nekik azt a 2 személyes amőbe játékot amiben 
a pálya mérete a kezdeti 5X5-ről addig növekedik amíg az egyik játékosnak sikerül 5 db azonos figurát elhelyeznie egy sorban/oszlopban
vagy átlósan. Így elérjük azt hogy minden megkezdett játékmenetben győztest avatunk amit egy hangeffektel és a győtes nevének kiírásával jelzünk.
Eközben a háttérben egy akciódús zene szól fokozva a feszültséget, amit némítani is lehet ha valakinek nem szükséges. 
Játékosaink akár profil képet is választhatnak magunknak saját galériájukbol, ezzel kicsit személyesebbé téve a játékélményt.

# 3. Jelenlegi üzleti folyamatok
  3.1 A szoftver véletlenszerűen választ valakit a játékosok közül hogy ki kezdjen <br/>
  3.2 A képernyőn megjelenik egy előre meghatározott méretű pálya <br/>
  3.2.1 Az első játékos választ egy mezőt => megérinti ott a képernyőt => a választott mezőn megjelenik a saját jele (o vagy x) <br/>
  3.2.2 A szoftver leellenőrzi, hogy van e az első játékosnak függőlegesen vagy vízszintesen vagy átlósan, megszakítás nélkül egymás után a nyeréshez szükséges mennyiségű jel (o vagy x) <br/>
  3.2.2.1 Ha a feltétel igaz => a játék véget ér => győzött az első játékos <br/>
  3.2.2.2 Ha a feltétel hamis => a második játékos következik <br/>
  3.2.3 A második játékos választ egy mezőt => megérinti ott a képernyőt => a választott mezőn megjelenik a saját jele (o vagy x) <br/>
  3.2.4 A szoftver leellenőrzi, hogy van e a második játékosnak függőlegesen vagy vízszintesen vagy átlósan, megszakítás nélkül egymás után a nyeréshez szükséges mennyiségű jel (o vagy x) <br/>
  3.2.4.1 Ha a feltétel igaz => a játék véget ér => győzött a második játékos <br/>
  3.2.4.2 Ha a feltétel hamis => az első játékos következik <br/>
  3.3 Ha a pálya betelt és nincs győztes => a játék véget ér döntetlennel <br/>
# 4. Igényelt üzleti folyamatok
4.1 A szoftver véletlenszerűen választ valakit a játékosok közül hogy ki kezdjen <br/>
4.2 A képernyőn megjelenik egy előre meghatározott méretű pálya <br/>
4.2.1 Az első játékos választ egy mezőt => megérinti ott a képernyőt => a választott mezőn megjelenik a saját jele (o vagy x) <br/>
4.2.2 A szoftver leellenőrzi, hogy van e az első játékosnak függőlegesen vagy vízszintesen vagy átlósan, megszakítás nélkül egymás után a nyeréshez szükséges mennyiségű jel (o vagy x) <br/>
4.2.2.1 Ha a feltétel igaz => a játék véget ér => győzött az első játékos <br/>
4.2.2.2 Ha a feltétel hamis => a második játékos következik <br/>
4.2.3 A második játékos választ egy mezőt => megérinti ott a képernyőt => a választott mezőn megjelenik a saját jele (o vagy x) <br/>
4.2.4 A szoftver leellenőrzi, hogy van e a második játékosnak függőlegesen vagy vízszintesen vagy átlósan, megszakítás nélkül egymás után a nyeréshez szükséges mennyiségű jel (o vagy x) <br/>
4.2.4.1 Ha a feltétel igaz => a játék véget ér => győzött a második játékos <br/>
4.2.4.2 Ha a feltétel hamis => az első játékos következik <br/>
4.3 Ha a pálya (x) része betelt => pálya bővítése <br/>
# 5. Rendszerre vonatkozó szabályok:
 - A szoftver megvalósítása Java programozási nyelvben kell hogy történjen.
 - Az UI megvalósításához xml-elt kell hogy használni.
 - Mentési fájl JSON formátumban.
 - 10 másodperces lépési időkorlát.
 - mp3-as kiterjesztésű hangfájlok kezelése.
 - Gradle fejlesztői projektautomatizációs eszköz használata.
 - Képfájlok jpg/png formátumúak legyenek.

# 6. Követelménylista
  - K01 Egyszerű, letisztult környezet
  - K02 Játszmák alatt háttérzene
  - K03 Győztest jelző hangüzenet
  - K04 Döntetlen játékállás esetén bővülő pálya
  - K05 Játékosok adatainak JSON formátumú állományban való tárolása
# 7. Fogalomszótár
  - Végtelen méretű pálya: Amennyiben a játékosok felhasználták az összes mezőt, de az állás döntetlen, újabb mezőkkel bővüljön a pálya.
  - Fairplay: sportszerűség
