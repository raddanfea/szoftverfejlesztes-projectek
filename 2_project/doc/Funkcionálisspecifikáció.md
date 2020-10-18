# 1. A rendszer céljai és nem céljai.
### Célok:
- Android alapú játék.
- Egy játékossal élvezhető játékmenet.
- Texas Hold'Em játészabály.
- Értékelhető és érzékelhető előrehaladás a játékmenetben.
### Nem cél:
- Egyéb platformok.
- Egyéb játékszabályok.
- Többjátékos mód.
# 2. Jelenlegi helyzet
A póker az egyik legnépszerűbb kártyajáték a világon. Minden esetben a játék lényege, hogy a játékosok a  kártyáikból a legjobbat 
kihozva elvigyék az asztal közepén lévő kasszát. Ezt a legjobb lap kombinációval tehetik meg, tehát minden játékosnak 
arra kell törekednie, hogy a saját kezében vagy a saját kézben tartott és a lenti kártyák összessége legyen a legjobb.
A Texas Hold'Em a közössági pókerjátékok legismertebb változata, amin 2-10 játékos vesz részt. A játékot 52 lapos francia
kártyával játszák a joker lapok nélkül. A játék kezdetekor minden játékos kap két zárt lapot, amit a játékoson kívül más nem lát
majd ezt követi öt közös nyílt lap ami színnel fölfele amit minden játékos lát és felhasználhat. Ebből a hét ismert lapból 
(2 ami kézben van + 5 ami az asztalon) kell kihozni a legjobb ötlapos kombinációt. A végén viszi el a kasszát akinek
a legerősebb pókerkezet(öt lapot) sikerült összegyűjteni. Ahogy sok minden más kártya játékot úgy ezt is utól érte a technológia, így már 
ez a kártya játék is játszható online környezetben vagy épp mobilos környezetben is. Aki rendelkezik okos eszközzel annak 
egy pár kattintás és már élvezheti is a játékot, viszont ezek tele vannak reklámmal, amiknek java része nem is kapcsolódik 
a témahoz ezzel sok embert zavarva, illetve mikrotranzakcióval amivel arra az útra probálják terelni a játékost, hogy zsetont
vegyen, vagy olyan dolgokra költsön amiket a játékmenet előrehaladásával amúgy is megszerezne.
# 3. Vágyálomrendszer
# 4. Jelenlegi üzleti folyamatok modellje

4.1 A játékos kiosztja a kártyákat.

4.2 Játékszabály szerint kisvagy és nagyvak eldöntése.

4.3 Négy Licit kör kijátszása:

4.3.1 A játékos tetszés szerint emel, tart vagy dob.

4.3.2 Pre-flop, Flop, Turn, River kártyaosztás.

4.4 Showdown, a játkosok lapjainak mutatása.  A játékos nyer vagy veszít.

4.5 A játékos felírja az eredményt magának.

4.6 A játékos összegyűji és újrakeveri a kártyát.

4.7 A játékos újrakezdi a folyamatot előrehaladási élmény nélkül.


# 5. Igényelt üzleti folyamatok modellje
# 6. Követelmény lista
- K01 A játék kövesse a hivatalos nemzetközi  Texas Hold'Em játékszabályokat.
- K02 A játéknak jutalmaznia kell a győzelmet és jeleznie az előrehaladást.
- K03 A program legalább az android rendszerek 95%-án működjön.
- K04 A szoftver megvalósítása Java programozási nyelvben kell hogy történjen.
- K05 Az UI megvalósításához xml-t kell használni.
- K06 A mentéseket titkosítva kell lokálisan tárolni.
- K07 Képfájlok jpg/png formátumúak legyenek.
- K08 A hangfájlokat wav kiterjesztéssel kell kezeléni.
# 7. Használati esetek
### Játékos
- Játéklogikával való interakció: Tart, Dob, Emel
- Játékállapot olvasása.
- Pakli változtatása.
- Beállítások szerkesztése.

![Image](res/usecases.jpg)
# 8. Megfeleltetés, hogyan fedik le a használati esetek a követelményeket
# 9. Képernyő tervek
### Menü
<img src="res/menu.png" width="200"><br/>
- Játék kezdése
- Pakli cseréje
- Beállítások
### Játék
<img src="res/játék.png" width="200"><br/>
- AI pénze
- Kör nyeremény pot
- Kiterített lapok
- Döntésgombok
- Játékos pénze
- Játékos lapjai
### Pakli
<img src="res/pakli.png" width="200"><br/>
- Minden jutalom kártyát mutat ABC sorrendben
- Kattintással lehet aktíválni
### Beállítások
<img src="res/settings.png" width="200"><br/>
- Hangerő
- Pot mérete
- Nehézség
- Mentés törlése gomb
# 10. Fogalomszótár
- Chip: A játék valutája.
- Pot: Chipek kupaca
- Pakli: Kártyalapok összegsége
- Pre-flop, Flop, Turn, River: A körök megnevezése, 1. től a 4.-ig.
- Kisvak, Nagyvak: Póker szabály, a játék kezdetekor kötelező kezdőööszeget kell betenni bizonyos játékosoknak.
