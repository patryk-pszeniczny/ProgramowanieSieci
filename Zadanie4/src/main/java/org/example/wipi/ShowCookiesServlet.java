package org.example.wipi;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ShowCookiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
            for (Cookie cookie : cookies) {
                out.println("<TR>\n" +
                        "<TD>" + cookie.getName() + "</TD>\n" +
                        "<TD>" + cookie.getValue() + "</TD>" +
                        "</TR>\n");
            }
            out.println("</TABLE></BODY></HTML>");
        }
    }
}
