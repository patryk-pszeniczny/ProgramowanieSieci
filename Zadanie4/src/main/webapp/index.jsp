<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>WiPI cw3</title>
</head>
<body>
<h1>WiPI cw3: Servlety i JSP (Tomcat 11)</h1>

<h2>Linki do zadan</h2>
<ul>
    <li><a href="<%=request.getContextPath()%>/init.html">InitParamServlet</a></li>
    <li><a href="<%=request.getContextPath()%>/ShowRequestHeader">ShowRequestHeader</a></li>
    <li><a href="<%=request.getContextPath()%>/SetCookies">SetCookies</a></li>
    <li><a href="<%=request.getContextPath()%>/ShowCookies">ShowCookies</a></li>
    <li><a href="<%=request.getContextPath()%>/session-info">SessionInfoServlet</a></li>
    <li><a href="<%=request.getContextPath()%>/person-bean.jsp">PersonBeanJSP</a></li>
    <li><a href="<%=request.getContextPath()%>/form-param.html">FormParamServlet formularz</a></li>
</ul>

<h2>FormParamServlet</h2>
<form action="<%=request.getContextPath()%>/form-param" method="post">
    <label>Imie: <input type="text" name="imie"/></label><br/>
    <label>Nazwisko: <input type="text" name="nazwisko"/></label><br/>
    <label>Wiek: <input type="number" name="wiek" min="0" max="120"/></label><br/>
    <label>Plec: <input type="text" name="plec"/></label><br/>
    <button type="submit">Wyslij</button>
</form>
</body>
</html>
