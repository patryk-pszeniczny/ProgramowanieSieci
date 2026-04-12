<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>PersonBeanJSP</title>
</head>
<body>
<h2>Zastosowanie komponentu PersonBean na stronie JSP</h2>

<jsp:useBean id="personBean" class="org.example.wipi.PersonBean" scope="page"/>

<p>
    <b>Dane domyslne:</b><br/>
    Imie: <jsp:getProperty name="personBean" property="imie"/><br/>
    Nazwisko: <jsp:getProperty name="personBean" property="nazwisko"/><br/>
    Wiek: <jsp:getProperty name="personBean" property="wiek"/><br/>
    Plec: <jsp:getProperty name="personBean" property="plec"/>
</p>

<jsp:setProperty name="personBean" property="imie" value="Anna"/>
<jsp:setProperty name="personBean" property="nazwisko" value="Nowak"/>
<jsp:setProperty name="personBean" property="wiek" value="24"/>
<jsp:setProperty name="personBean" property="plec" value="kobieta"/>

<p>
    <b>Dane po modyfikacji:</b><br/>
    Imie: <jsp:getProperty name="personBean" property="imie"/><br/>
    Nazwisko: <jsp:getProperty name="personBean" property="nazwisko"/><br/>
    Wiek: <jsp:getProperty name="personBean" property="wiek"/><br/>
    Plec: <jsp:getProperty name="personBean" property="plec"/>
</p>

<p><a href="<%=request.getContextPath()%>/">Powrot</a></p>
</body>
</html>
