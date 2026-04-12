package org.example.wipi;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class CookiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
                    "Teraz zamknij przegladarke, uruchom ja i ponownie wyswietl\n" +
                    "serwlet <CODE>ShowCookies</CODE>, aby sprawdzic czy \n" +
                    "trzy trwale cookies sa dostepne takze w nowej sesji.\n" +
                    "</BODY></HTML>");
        }
    }
}
