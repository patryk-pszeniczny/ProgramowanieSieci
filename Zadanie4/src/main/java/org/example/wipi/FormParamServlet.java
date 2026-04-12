package org.example.wipi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class FormParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        renderResponse(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        renderResponse(request, response);
    }

    private void renderResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imie = getParamOrDefault(request, "imie", getInitParameter("domyslneImie"));
        String nazwisko = getParamOrDefault(request, "nazwisko", getInitParameter("domyslneNazwisko"));
        String wiek = getParamOrDefault(request, "wiek", getInitParameter("domyslnyWiek"));
        String plec = getParamOrDefault(request, "plec", getInitParameter("domyslnaPlec"));

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<HTML>\n" +
                    "<HEAD><TITLE>Odczyt danych formularza</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"white\">\n" +
                    "<H3 ALIGN=LEFT>Odczyt danych formularza</H3><HR>\n" +
                    "<B>Imie</B>: " + escapeHtml(imie) + "\n" +
                    "<BR><B>Nazwisko</B>: " + escapeHtml(nazwisko) + "\n" +
                    "<BR><B>Wiek</B>: " + escapeHtml(wiek) + "\n" +
                    "<BR><B>Plec</B>: " + escapeHtml(plec) + "\n" +
                    "<H4>Parametry inicjujace serwletu</H4>\n" +
                    "<B>domyslneImie</B>: " + escapeHtml(getInitParameter("domyslneImie")) + "\n" +
                    "<BR><B>domyslneNazwisko</B>: " + escapeHtml(getInitParameter("domyslneNazwisko")) + "\n" +
                    "<BR><B>domyslnyWiek</B>: " + escapeHtml(getInitParameter("domyslnyWiek")) + "\n" +
                    "<BR><B>domyslnaPlec</B>: " + escapeHtml(getInitParameter("domyslnaPlec")) + "\n" +
                    "<P><A HREF=\"" + request.getContextPath() + "/\">Powrot</A></P>" +
                    "</BODY></HTML>");
        }
    }

    private static String getParamOrDefault(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        if (value == null || value.isBlank()) {
            return defaultValue == null ? "" : defaultValue;
        }
        return value;
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
