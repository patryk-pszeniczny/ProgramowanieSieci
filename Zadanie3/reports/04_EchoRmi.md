# EchoRmi (zadanie dodatkowe 1)

Krotko: klient wysyla tekst do zdalnej metody `echo`, serwer odsyla ten sam tekst.

## Jak uruchomic

1. Skompiluj projekt:
```powershell
.\gradlew.bat compileJava
```
2. Terminal 1 (serwer echo):
```powershell
java -cp build/classes/java/main rmi.EchoRmi.EchoServer
```
3. Terminal 2 (klient echo):
```powershell
java -cp build/classes/java/main rmi.EchoRmi.EchoClient 127.0.0.1 "Echo test przez RMI"
```

## Output (z uruchomienia)

Serwer:
```text
Uruchomiono lokalny rejestr RMI na porcie 1099
EchoService gotowy (usluga echo - odpowiednik portu 7).
Wywolanie echo(), wiadomosc: Echo test przez RMI
```

Klient:
```text
Wyslano:  Echo test przez RMI
Odebrano: Echo test przez RMI
```
