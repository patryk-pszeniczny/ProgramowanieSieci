# RMICallback (pkt 5)

Krotko: klient rejestruje callback u serwera, a serwer cyklicznie wysyla aktualizacje ceny.

## Jak uruchomic

1. Skompiluj projekt:
```powershell
.\gradlew.bat compileJava
```
2. Terminal 1 (serwer callback):
```powershell
java -cp build/classes/java/main rmi.RMICallback.StockImpl
```
3. Terminal 2 (klient callback):
```powershell
java -cp build/classes/java/main rmi.RMICallback.StockClient
```
4. Zatrzymanie: `Ctrl+C` w obu terminalach.

## Output (z uruchomienia)

Serwer:
```text
Uruchomiono lokalny rejestr RMI na porcie 1099
Obiekt StockImpl gotowy
Klient zarejestrowany: Proxy[StockUpdate,RemoteObjectInvocationHandler[UnicastRef [liveRef: [endpoint:[10.83.10.165:60188](remote),objID:[746d2bdc:19d389581a7:-7fff, -7428394755662858911]]]]]
```

Klient:
```text
Klient callback podlaczony. Czekam na aktualizacje...
Aktualizacja danych: papier TPSA = 21.56 zl
Aktualizacja danych: papier TPSA = 21.65 zl
Aktualizacja danych: papier TPSA = 21.25 zl
Aktualizacja danych: papier TPSA = 20.93 zl
```
