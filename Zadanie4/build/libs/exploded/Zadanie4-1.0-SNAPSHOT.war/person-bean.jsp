<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>PersonBeanJSP</title>
</head>
<body bgcolor="white">
<h3>Zastosowanie komponentu PersonBean na stronie JSP</h3>

<jsp:useBean id="personBean"
             class="org.example.wipi.PersonBean" />

<p>
    <b>Dane poczatkowe osoby (PersonBean):</b><BR>
    Imie: <jsp:getProperty name="personBean"
                           property="imie"/><BR>
    Nazwisko: <jsp:getProperty name="personBean"
                               property="nazwisko"/><BR>
    Wiek: <jsp:getProperty name="personBean"
                           property="wiek"/><BR>
    Plec: <jsp:getProperty name="personBean"
                           property="plec"/>
</p>

<jsp:setProperty name="personBean"
                 property="imie" value="Andrzej"/>
<jsp:setProperty name="personBean"
                 property="nazwisko" value="Nowak"/>
<jsp:setProperty name="personBean"
                 property="wiek" value="24"/>
<jsp:setProperty name="personBean"
                 property="plec" value="mezczyzna"/>

<p>
    <b>Dane osoby po zmianie:</b><BR>
    Imie: <jsp:getProperty name="personBean"
                           property="imie"/><BR>
    Nazwisko: <jsp:getProperty name="personBean"
                               property="nazwisko"/><BR>
    Wiek: <jsp:getProperty name="personBean"
                           property="wiek"/><BR>
    Plec: <jsp:getProperty name="personBean"
                           property="plec"/>
</p>
</body>
</html>
