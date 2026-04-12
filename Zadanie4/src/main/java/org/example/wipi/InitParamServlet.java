package org.example.wipi;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class InitParamServlet extends HttpServlet {
    private String nazwisko = "";
    private String imie = "";
    private int wiek = 0;
    private String plec = "";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        nazwisko = valueOrDefault(config.getInitParameter("nazwisko"), "Brak");
        imie = valueOrDefault(config.getInitParameter("imie"), "Brak");
        plec = valueOrDefault(config.getInitParameter("plec"), "Brak");
        try {
            String wiekString = config.getInitParameter("wiek");
            wiek = Integer.parseInt(wiekString);
            if (wiek < 0 || wiek > 120) {
                wiek = 0;
            }
        } catch (NumberFormatException ignored) {
            wiek = 0;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ServletContext context = getServletContext();
            out.println("<HTML>\n" +
                    "<HEAD><TITLE>Odczyt danych inicjujacych serwlet</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"white\">\n" +
                    "<H3 ALIGN=LEFT>Odczyt danych inicjujacych serwlet</H3><HR>\n" +
                    "<B>Imie</B>: " + escapeHtml(imie) + "\n" +
                    "<BR><B>Nazwisko</B>: " + escapeHtml(nazwisko) + "\n" +
                    "<BR><B>Wiek</B>: " + wiek + "\n" +
                    "<BR><B>Plec</B>: " + escapeHtml(plec) + "\n" +
                    "<H4>Parametry globalne aplikacji</H4>\n" +
                    "<B>uczelnia</B>: " + escapeHtml(context.getInitParameter("uczelnia")) + "\n" +
                    "<BR><B>przedmiot</B>: " + escapeHtml(context.getInitParameter("przedmiot")) + "\n" +
                    "<BR><B>prowadzacy</B>: " + escapeHtml(context.getInitParameter("prowadzacy")) + "\n" +
                    "<P><A HREF=\"" + request.getContextPath() + "/\">Powrot</A></P>" +
                    "</BODY></HTML>");
        }
    }

    private static String valueOrDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
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
