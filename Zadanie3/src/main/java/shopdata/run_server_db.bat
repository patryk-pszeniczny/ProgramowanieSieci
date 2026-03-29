rem uruchomienie HSQLDB 2.4 w trybie serwera 
rem java -cp "./lib/hsqldb.jar" org.hsqldb.server.Server --help

java -cp "./lib/hsqldb.jar" org.hsqldb.server.Server --database.0 file:./data/shopdb;user=student;password=student --dbname.0 shopdb

pause
