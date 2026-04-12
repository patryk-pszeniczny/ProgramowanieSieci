package org.example.wipi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

public class HeaderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String html = buildHtml(request);
        byte[] content = html.getBytes(StandardCharsets.UTF_8);

        response.setContentType("text/html; charset=UTF-8");
        String acceptEncoding = request.getHeader("Accept-Encoding");
        if (acceptEncoding != null && acceptEncoding.toLowerCase(Locale.ROOT).contains("gzip")) {
            response.setHeader("Content-Encoding", "gzip");
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(response.getOutputStream())) {
                gzipOutputStream.write(content);
            }
            return;
        }

        response.getOutputStream().write(content);
    }

    private static String buildHtml(HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<HTML>\n");
        html.append("<HEAD><TITLE>Prezentacja wlasciwosci naglowka zadania</TITLE></HEAD>\n");
        html.append("<BODY BGCOLOR=\"white\">\n");
        html.append("<H1 ALIGN=CENTER>Prezentacja wlasciwosci naglowka zadania</H1>\n");
        html.append("<B>Metoda: </B>").append(escapeHtml(request.getMethod())).append("<BR>\n");
        html.append("<B>dane URI: </B>").append(escapeHtml(request.getRequestURI())).append("<BR>\n");
        html.append("<B>Protokol: </B>").append(escapeHtml(request.getProtocol())).append("<BR>\n");
        html.append("<B>Adres serwera: </B>").append(escapeHtml(request.getServerName())).append("<BR>\n");
        html.append("<B>Port nasluchu: </B>").append(request.getServerPort()).append("<BR>\n");
        html.append("<B>Nazwa aplikacji: </B>").append(escapeHtml(request.getContextPath())).append("<BR><BR>\n");
        html.append("<TABLE BORDER=1 ALIGN=CENTER>\n");
        html.append("<TR BGCOLOR=\"blue\">\n");
        html.append("<TH><FONT COLOR=\"yellow\">Nazwa naglowka</FONT></TH>\n");
        html.append("<TH><FONT COLOR=\"yellow\">Wartosc naglowka</FONT></TH>");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            html.append("<TR><TD>")
                    .append(escapeHtml(headerName))
                    .append("</TD><TD>")
                    .append(escapeHtml(request.getHeader(headerName)))
                    .append("</TD></TR>\n");
        }
        html.append("</TABLE>\n");
        html.append("<P><A HREF=\"").append(escapeHtml(request.getContextPath())).append("/\">Powrot</A></P>\n");
        html.append("</BODY></HTML>");
        return html.toString();
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
