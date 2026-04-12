package org.example.wipi;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class CookiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie autorCookie = new Cookie("autor", "verdu");
        autorCookie.setAttribute("Comment", "Cookie utworzone w cwiczeniu 3");
        autorCookie.setMaxAge(3600);
        String path = request.getContextPath();
        autorCookie.setPath(path == null || path.isBlank() ? "/" : path);
        response.addCookie(autorCookie);

        for (int i = 0; i < 3; i++) {
            Cookie cookie = new Cookie("Sesyjne-Cookie-" + i, "Cookie-Wartosc-S" + i);
            response.addCookie(cookie);
            cookie = new Cookie("Trwale-Cookie-" + i, "Cookie-Wartosc-T" + i);
            cookie.setMaxAge(600);
            response.addCookie(cookie);
        }

        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String urlShowCookies = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/ShowCookies";
            out.println("<HTML>\n" +
                    "<HEAD><TITLE>ShowCookies</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                    "<H1 ALIGN=\"CENTER\">Tworzenie cookies</H1>\n" +
                    "Ta strona tworzy szesc roznych cookies.\n" +
                    "Aby wyswietlic informacje o nich, odwiedz \n" +
                    "<A HREF=\"" + urlShowCookies + "\">serwlet <CODE>ShowCookies</CODE></A>.\n" +
                    "<P>\n" +
                    "Trzy sposrod utworzonych cookies sa skojarzone wylacznie \n" +
                    "z biezaca sesja, natomiast pozostale trzy sa trwale.\n" +
                    "<HR><H3>Cookie \"autor\" (wymagane w zadaniu)</H3>\n" +
                    "<B>Nazwa</B>: " + escapeHtml(autorCookie.getName()) + "<BR>\n" +
                    "<B>Wartosc</B>: " + escapeHtml(autorCookie.getValue()) + "<BR>\n" +
                    "<B>Komentarz</B>: " + escapeHtml(autorCookie.getAttribute("Comment")) + "<BR>\n" +
                    "<B>Czas trwania</B>: " + autorCookie.getMaxAge() + " sekund\n" +
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
