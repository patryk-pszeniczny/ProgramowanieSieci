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
    private static final long serialVersionUID = 1L;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private static String buildHtml(HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<HTML>\n");
        html.append("<HEAD><TITLE>Prezentacja wlasciwosci naglowka zadania</TITLE></HEAD>\n");
        html.append("<BODY BGCOLOR=\"white\">\n");
        html.append("<H1 ALIGN=CENTER>Prezentacja wlasciwosci naglowka zadania</H1>\n");
        html.append("<B>Metoda: </B>").append(request.getMethod()).append("<BR>\n");
        html.append("<B>dane URI: </B>").append(request.getRequestURI()).append("<BR>\n");
        html.append("<B>Protokol: </B>").append(request.getProtocol()).append("<BR><BR>\n");
        html.append("<TABLE BORDER=1 ALIGN=CENTER>\n");
        html.append("<TR BGCOLOR=\"blue\">\n");
        html.append("<TH><FONT COLOR=\"yellow\">Nazwa naglowka</FONT></TH>\n");
        html.append("<TH><FONT COLOR=\"yellow\">Wartosc naglowka</FONT></TH>");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            html.append("<TR><TD>")
                    .append(headerName)
                    .append("</TD><TD>")
                    .append(request.getHeader(headerName))
                    .append("</TD></TR>\n");
        }
        html.append("</TABLE>\n");
        html.append("</BODY></HTML>");
        return html.toString();
    }
}
