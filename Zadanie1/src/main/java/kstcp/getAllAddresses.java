/*
 *  Koszalin 2004
 *  getAllAddresses.java
 *  Przyklad - pobranie z DNS wszystkich IP przypisanych do nazwy
 *  Dariusz Rataj (C)
 */

package kstcp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

class getAllAddresses {

    public static void main(String[] args) {

        List<String> hostyDoSprawdzenia = List.of(
                "ibm.com", "microsoft.com", "java.sun.com", "termit.ie.tu.koszalin.pl"
        );
        for(String host:hostyDoSprawdzenia) {
            try {
                System.out.println("Adresy przypisane do nazwy " + host + ":\n");
                InetAddress[] adresy = InetAddress.getAllByName(host);
                for (int i = 0; i < adresy.length; i++) {
                    System.out.println(adresy[i]);
                }
            } catch (UnknownHostException ex) {
                System.err.println("Nie moge znalezc hosta " + host + " :( !");
            } // koniec catch
        }
    } // koniec main

} // koniec getAllAddresses
