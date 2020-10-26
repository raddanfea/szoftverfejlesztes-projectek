# 1. A rendszer céljai és nem céljai
## 1.1 A rendszer céljai:
## 1.2 A rendszernek nem célja:

# 2. Projekt terv
## 2.1 A projekt résztvevői:
A projekten 4 programtervező informatikus hallgató dolgozik, egyenlő munkakörben,
   - Rádi Dániel (fejlesztő)
   - Simion Tamás (fejlesztő)
   - Nagy Kristián (fejlesztő)
   - Puskás Máté (fejlesztő)
## 2.2 Ütemterv:
- Projekt kezdete: 2020.10.12.
- Dokumentációk határideje: 2020.10.26.
  - Követelményspecifikáció: 2020.10.19.
  - Funkcionálisspecifikáció: 2020.10.19.
  - Rendszerterv: 2020.10.26.
- Fejlesztési sprintek: 2020.11.30.
  - első fejlesztési sprint: 2020.11.02.
  - második fejlesztési sprint: 2020.11.09.
  - harmadik fejlesztési sprint + Demó: 2020.11.16.
  - negyedik fejlesztési sprint + Tesztelés: 2020.11.23.
  - ötödik fejlesztési sprint + tesztelés + teszt dokumentálás: 2020.11.30.
- Átadás-átvétel: 2020.12.07.
# 3. Üzleti folyamatok modellje
## 3.1 Üzleti szereplők
## 3.2 Üzleti folyamatok
## 3.3 Üzleti entitások

# 4. Követelmények

# 5. Fizikai környezet
## Hardver:

Megfontolt opciók:
 * Számítógép
 * Okostelefonok

Hogy egy ilyen nagyszerű és népszerű játékot akárhol és akármikor játszhasson az ember, ahhoz manapság az okostelefonok a legjobb választás. Számítógépen egyértelműen látványosabb játékélmény nyújtható és az irányítás is egyszerűbb, de nem olyan mértékben, hogy emiatt kénytelenek lennénk szánítógépre fejleszteni. Valamint játék nem igényel intenzív aktivitást a felhasználótól és kényelmesen élvezhető bárhol bármilyen körülmények között.

## Operációs rendszer:

A mai okostelefonok operációs rendszerei:
 * IOS
 * Android

Ezek közül az androidot választottuk, hogy így több felhasználót érjünk el.

## Fejlesztői eszközök:

Rendelkezésre álló fejlesztői eszközök:

 * IntellIj IDEA
 * Android Studio
 * Visual Studio
 * Eclipse
 * Flutter

Mivel az okostelefonok közül az Andriod operációs rendszerrel rendelkező eszközöket céloztuk meg, ezért a cross-platform opciók - mint például a Visual Stidio Xamarinnal (ami a c# programozási nyelvet használja), Flutter (saját nyelvvel rendelkezik Dart néven) - az átaluk használt programozási nyelvek miatt, valamint az alkalmazás úgyis csupán az Android operációs rendelkező okostelefonokra készül, ezért tehát ezt a 2 opciót kizártuk.
Az Eclipse IDE-t a felhasználói felület bonyolultsága miatt zártuk ki, a csapatunk egyébként sem jártas a szoftver használatában és ez nem is jár semmiféle hátránnyal számunkra.
Az Eclipse-el ellentétben az Intellij IDEA már egy sokkal barátságosabb fejlesztői környezet, de mivel ez nem egy ingyenes opció és a követelményekben sem szerepel, ezért ezt az opciót is elvetettük.

Végül csapatunk az Android Studio nevű IDE-t fogja használni a fejlesztéshez, amely a Gradle fejlesztői projektautomatizációs eszközt használja. Itt a UI-t is meg tudjuk tervezni, amelyel párhuzamosan generálja belőle az xml fájlt.

Az Android Studio 2 programozási nyelvben is lehetővé teszi a fejlesztést:

- Java
- Kotlin

Mivel a csapatunk a Java nyelvet ismeri, így a fejlesztés során is azt fogjuk használni.

# 6. Funkcionális terv

# 7. Implementációs terv:

# 8. Teszt terv
