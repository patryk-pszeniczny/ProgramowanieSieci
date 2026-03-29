# CalculatorCallback (zadanie dodatkowe 2)

Krotko: klient wywoluje zdalna metode `oblicz`, a serwer wykonuje wlasciwe dzialanie przez callback na metodach klienta (`dodaj`, `odejmij`, `pomnoz`, `podziel`).

## Jak uruchomic

1. Skompiluj projekt:
```powershell
.\gradlew.bat compileJava
```
2. Terminal 1 (serwer kalkulatora):
```powershell
java -cp build/classes/java/main rmi.CalculatorCallback.CalculatorServer
```
3. Terminal 2 (klient z callbackiem):
```powershell
java -cp build/classes/java/main rmi.CalculatorCallback.CalculatorClient
```

## Output (z uruchomienia)

Serwer:
```text
Uruchomiono lokalny rejestr RMI na porcie 1099
CalculatorService gotowy.
Serwer: oblicz(10.0, 5.0, dodaj)
Serwer: oblicz(10.0, 5.0, odejmij)
Serwer: oblicz(10.0, 5.0, pomnoz)
```

Klient:
```text
Wynik [dodaj] dla 10.0 i 5.0 = 15.0
Wynik [odejmij] dla 10.0 i 5.0 = 5.0
Wynik [pomnoz] dla 10.0 i 5.0 = 50.0
Blad: Blad przy wywolaniu callback: RemoteException occurred in server thread; nested exception is: 
	java.rmi.RemoteException: Dzielenie przez zero.
```
