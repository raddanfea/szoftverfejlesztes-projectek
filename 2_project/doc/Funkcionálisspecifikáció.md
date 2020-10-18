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
<img src="res/menu.png" width="200"><br/>
- Játék kezdése
- Pakli cseréje
- Beállítások

<img src="res/játék.png" width="200"><br/>
- AI pénze
- Kör nyeremény pot
- Kiterített lapok
- Döntésgombok
- Játékos pénze
- Játékos lapjai

<img src="res/pakli.png" width="200"><br/>
- Minden jutalom kártyát mutat ABC sorrendben
- Kattintással lehet aktíválni

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
