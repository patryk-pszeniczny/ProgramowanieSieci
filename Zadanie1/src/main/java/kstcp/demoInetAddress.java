/*
 *  Koszalin 2004
 *  demoInetAddress.java
 *  Przyklad demonstrujacy podstawowe zastosowania klasy InetAddress
 *  Dariusz Rataj (C)
 */
package kstcp;

import java.net.InetAddress;
import java.net.UnknownHostException;

class demoInetAddress {

    public static void main(String[] args) {
        try {
            // pobranie danych lokalnego hosta
            InetAddress adres1 = InetAddress.getLocalHost();
            System.out.println("Nazwa lokalnego hosta: " + adres1.getHostName());
            System.out.println("Adres lokalnego hosta: " + adres1.getHostAddress() + "\n");
        } catch (UnknownHostException e) {
            System.err.println(e);
        }

        String[] hostyDoSprawdzenia = {
                "wbiis.tu.koszalin.pl",
                "termit.ie.tu.koszalin.pl",
                "ibm.com"
        };

        System.out.println("Adresy hostow (na podstawie nazw):");
        for (String host : hostyDoSprawdzenia) {
            try {
                InetAddress adres = InetAddress.getByName(host);
                System.out.println("Nazwa: " + adres.getHostName());
                System.out.println("Adres: " + adres.getHostAddress() + "\n");
            } catch (UnknownHostException e) {
                System.out.println("Nazwa: " + host);
                System.out.println("Blad: " + e.getMessage() + "\n");
            }
        }

        String[] adresyDoSprawdzenia = {
                "192.168.55.20",
                "148.81.247.7",
                "153.19.134.114"
        };

        System.out.println("Nazwy hostow (na podstawie adresow IP):");
        for (String ip : adresyDoSprawdzenia) {
            try {
                InetAddress adres = InetAddress.getByName(ip);
                System.out.println("Nazwa: " + adres.getHostName());
                System.out.println("Adres: " + adres.getHostAddress() + "\n");
            } catch (UnknownHostException e) {
                System.out.println("Adres: " + ip);
                System.out.println("Blad: " + e.getMessage() + "\n");
            }
        }
    }
}
 

 
