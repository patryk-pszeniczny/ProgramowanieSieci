package org.example.wipi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class FormParamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imie = getParamOrDefault(request, "imie", getInitParameter("domyslneImie"));
        String nazwisko = getParamOrDefault(request, "nazwisko", getInitParameter("domyslneNazwisko"));
        String wiek = getParamOrDefault(request, "wiek", getInitParameter("domyslnyWiek"));
        String plec = getParamOrDefault(request, "plec", getInitParameter("domyslnaPlec"));

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<HTML>\n" +
                "<HEAD><TITLE>Odczyt danych formularza</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"white\">\n" +
                "<H3 ALIGN=LEFT>Odczyt danych formularza</H3><HR>\n" +
                "<B>Imie</B>: " + imie + "\n" +
                "<BR><B>Nazwisko</B>: " + nazwisko + "\n" +
                "<BR><B>Wiek</B>: " + wiek + "\n" +
                "<BR><B>Plec</B>: " + plec + "\n" +
                "<H4>Parametry inicjujace serwletu</H4>\n" +
                "<B>domyslneImie</B>: " + getInitParameter("domyslneImie") + "\n" +
                "<BR><B>domyslneNazwisko</B>: " + getInitParameter("domyslneNazwisko") + "\n" +
                "<BR><B>domyslnyWiek</B>: " + getInitParameter("domyslnyWiek") + "\n" +
                "<BR><B>domyslnaPlec</B>: " + getInitParameter("domyslnaPlec") + "\n" +
                "</BODY></HTML>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private static String getParamOrDefault(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        if (value == null || value.isBlank()) {
            return defaultValue == null ? "" : defaultValue;
        }
        return value;
    }
}
