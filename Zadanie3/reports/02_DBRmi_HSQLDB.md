# DBRmi + shopdb (pkt 2)

Krotko: serwer RMI laczy sie z HSQLDB (`shopdb`) i zwraca klientowi kolumny oraz dane z tabeli `customer_tbl`.

## Jak uruchomic

1. Skompiluj projekt:
```powershell
.\gradlew.bat compileJava
```
2. Terminal 1 (HSQLDB server):
```powershell
java -cp src/main/java/shopdata/lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:./src/main/java/shopdata/data/shopdb --dbname.0 shopdb
```
3. Terminal 2 (DBRmi server):
```powershell
java -cp "build/classes/java/main;src/main/java/shopdata/lib/hsqldb.jar" rmi.DBrmi.DBServer
```
4. Terminal 3 (DBRmi client):
```powershell
java -cp "build/classes/java/main;src/main/java/shopdata/lib/hsqldb.jar" rmi.DBrmi.DBClient
```
5. Zamkniecie bazy:
```powershell
java -jar src/main/java/shopdata/lib/sqltool.jar --rcfile=src/main/java/shopdata/sqltool.rc --sql "SHUTDOWN;" shopdb
```

## Output (z uruchomienia)

Serwer DBRmi:
```text
Uruchomiono lokalny rejestr RMI na porcie 1099
Obiekt DatabaseObject przygotowany... czekam...
Polaczono z baza: jdbc:hsqldb:hsql://localhost:9001/shopdb
```

Klient DBRmi:
```text
--------------- Dane tabeli customer_tbl --------------------
Cols: 8 Rows: 15
| 1.CUST_ID	| 2.CUST_NAME	| 3.CUST_ADDRESS	| 4.CUST_CITY	| 5.CUST_STATE	| 6.CUST_ZIP	| 7.CUST_PHONE	| 8.CUST_FAX	
---------------------------------------------------------------
| 1	| LESLIE GLEASON	| 798 HARDAWAY DR	| INDIANAPOLIS	| IN	| 47856	| 3175457690	| null	
| 2	| Nancy Bunker	| APT A 4556 WATERWAY	| Broad Ripple	| IN	| 47950	| 3174262323	| null	
| 3	| Angela Dobko	| RR3 Box 76	| Lebanon	| IN	| 49967	| 7658970090	| null	
| 4	| Wendy Wolf	| 3345 Gateway DR	| INDIANAPOLIS	| IN	| 46224	| 3172913421	| null	
| 5	| Marys Gift Shop	| 435 Main ST	| DANVILLE	| IL	| 47978	| 3178567221	| 3178523434	
| 6	| Scottys Market	| RR2 BOX 173	| Brownsburg	| IN	| 45687	| 3178529835	| 3178529836	
| 7	| Jasons and Dallas Goodies	| Lafayette SQ Mall	| INDIANAPOLIS	| IN	| 46222	| 3172978886	| 3172978887	
| 8	| Morgans Candies and Treats	| 5657 W Tenth ST	| INDIANAPOLIS	| IN	| 46234	| 3172714398	| null	
| 9	| Schylers Novelties	| 17 Maple ST	| Lebanon	| IN	| 48890	| 3174346758	| null	
| 10	| Gavings Place	| 99801Rockville RD	| INDIANAPOLIS	| IN	| 46244	| 3172719991	| 3172719992	
| 11	| Hollis Gamearama	| 567 US 31 South	| Whiteland	| IN	| 49980	| 3178879023	| null	
| 12	| Heathers Feathers and Things	| 4090 N Shadeland AVE	| INDIANAPOLIS	| IN	| 43278	| 317546768	| null	
| 13	| Ragans Hobies INC	| 451 Green ST	| Plainfield	| IN	| 46818	| 3178393441	| 3178399090	
| 14	| Andys Candies	| RR 1 Box 34	| Nashville	| IN	| 48756	| 8123239871	| null	
| 15	| Ryans Stuff	| 2337 S Shelby ST	| INDIANAPOLIS	| IN	| 47834	| 3175634402	| null	
---------------------------------------------------------------
```
