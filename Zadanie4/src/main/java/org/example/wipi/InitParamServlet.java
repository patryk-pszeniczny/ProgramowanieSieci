package org.example.wipi;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class InitParamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String nazwisko = "";
    private String imie = "";
    private int wiek = 0;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        nazwisko = config.getInitParameter("nazwisko");
        if (nazwisko == null) {
            nazwisko = "Brak";
        }
        imie = config.getInitParameter("imie");
        if (imie == null) {
            imie = "Brak";
        }
        try {
            String wiekString = config.getInitParameter("wiek");
            wiek = Integer.parseInt(wiekString);
            if (wiek < 0 || wiek > 120) {
                wiek = 0;
            }
        } catch (NumberFormatException ignored) {
            // wiek domyslny to 0
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<HTML>\n" +
                "<HEAD><TITLE>Odczyt danych inicjujacych serwlet</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"white\">\n" +
                "<H3 ALIGN=LEFT>Odczyt danych inicjujacych serwlet</H3><HR>\n" +
                "<B>Imie</B>: " + imie + "\n" +
                "<BR><B>Nazwisko</B>: " + nazwisko + "\n" +
                "<BR><B>Wiek</B>: " + wiek + "\n" +
                "</BODY></HTML>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
