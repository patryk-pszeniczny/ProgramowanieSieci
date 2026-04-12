package org.example.wipi;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ShowCookiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<HTML>\n" +
                    "<HEAD><TITLE>ShowCookies</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                    "<H1 ALIGN=\"CENTER\">Aktywne cookies</H1>\n" +
                    "<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
                    "<TR BGCOLOR=\"#FFAD00\">\n" +
                    "<TH>Nazwa cookie</TH>\n" +
                    "<TH>Wartosc cookie</TH>\n" +
                    "</TR>\n");
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    out.println("<TR>\n" +
                            "<TD>" + escapeHtml(cookie.getName()) + "</TD>\n" +
                            "<TD>" + escapeHtml(cookie.getValue()) + "</TD>\n" +
                            "</TR>\n");
                }
            }
            out.println("</TABLE>\n");
            out.println("<P><A HREF=\"" + request.getContextPath() + "/\">Powrot</A></P>\n");
            out.println("</BODY></HTML>");
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
