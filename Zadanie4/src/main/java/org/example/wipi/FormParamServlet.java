package org.example.wipi;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class FormParamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<HTML>\n" +
                "<HEAD><TITLE>Odczyt danych formularza</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"white\">\n" +
                "<H3 ALIGN=LEFT>Odczyt danych formularza</H3><HR>\n" +
                "<B>Imie</B>: " + request.getParameter("imie") + "\n" +
                "<BR><B>Nazwisko</B>: " + request.getParameter("nazwisko") + "\n" +
                "</BODY></HTML>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
