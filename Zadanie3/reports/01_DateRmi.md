# DateRmi (pkt 1)

Krotko: serwer RMI udostepnia metode `getDate()`, klient ja wywoluje i wypisuje date z hosta serwera.

## Jak uruchomic

1. Skompiluj projekt:
```powershell
.\gradlew.bat compileJava
```
2. Terminal 1 (serwer):
```powershell
java -cp build/classes/java/main rmi.DateRmi.DateServer
```
3. Terminal 2 (klient):
```powershell
java -cp build/classes/java/main rmi.DateRmi.DateClient
```

## Output (z uruchomienia)

Serwer:
```text
Uruchomiono lokalny rejestr RMI na porcie 1099
Obiekt DateObject przygotowany
Zdalne wywolanie metody getDate()
```

Klient:
```text
Data na zdalnym hoscie: Sun Mar 29 09:55:33 CEST 2026
```
