package org.example.wipi;

public class PersonBean {
    private String imie = "Jan";
    private String nazwisko = "Kowalski";

    public PersonBean() {
        this("Jan", "Kowalski");
    }

    public PersonBean(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }
}
