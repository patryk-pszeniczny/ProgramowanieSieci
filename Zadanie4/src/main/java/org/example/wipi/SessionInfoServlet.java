package org.example.wipi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class SessionInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        Integer licznik = (Integer) session.getAttribute("licznik");
        if (licznik == null) {
            licznik = 1;
        } else {
            licznik = licznik + 1;
        }

        session.setAttribute("licznik", licznik);
        session.setAttribute("status", "aktywny");
        session.setAttribute("kurs", "Witryny i portale internetowe");

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<HTML>\n" +
                    "<HEAD><TITLE>Informacje o sesji</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"white\">\n" +
                    "<H2 ALIGN=\"CENTER\">Informacje o sesji:</H2>\n" +
                    "<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
                    "<TR BGCOLOR=\"blue\">\n" +
                    "<TH><FONT COLOR=\"yellow\">Typ</FONT></TH>\n" +
                    "<TH><FONT COLOR=\"yellow\">Wartosc</FONT></TH></TR>\n" +
                    "<TR><TD>ID</TD><TD>" + escapeHtml(session.getId()) + "</TD></TR>\n" +
                    "<TR><TD>Czas utworzenia</TD><TD>" + new Date(session.getCreationTime()) + "</TD></TR>\n" +
                    "<TR><TD>Czas ostatniego dostepu</TD><TD>" + new Date(session.getLastAccessedTime()) + "</TD></TR>\n" +
                    "<TR><TD>Ilosc odwiedzin</TD><TD>" + licznik + "</TD></TR>\n" +
                    "<TR><TD>status</TD><TD>" + escapeHtml(String.valueOf(session.getAttribute("status"))) + "</TD></TR>\n" +
                    "<TR><TD>kurs</TD><TD>" + escapeHtml(String.valueOf(session.getAttribute("kurs"))) + "</TD></TR>\n" +
                    "</TABLE>\n" +
                    "<P><A HREF=\"" + request.getContextPath() + "/\">Powrot</A></P>\n" +
                    "</BODY></HTML>");
        }
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
