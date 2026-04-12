package org.example.wipi;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FormParamServletTest {
    private FormParamServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter body;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new FormParamServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        body = new StringWriter();
        writer = new PrintWriter(body);
        when(response.getWriter()).thenReturn(writer);

        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(config.getServletName()).thenReturn("FormParamServlet");
        when(config.getServletContext()).thenReturn(context);
        when(config.getInitParameter("domyslneImie")).thenReturn("Jan");
        when(config.getInitParameter("domyslneNazwisko")).thenReturn("Kowalski");
        when(config.getInitParameter("domyslnyWiek")).thenReturn("20");
        when(config.getInitParameter("domyslnaPlec")).thenReturn("nieznana");
        servlet.init(config);
    }

    @Test
    void shouldRenderAgeAndGenderFromRequest() throws Exception {
        when(request.getParameter("imie")).thenReturn("Ala");
        when(request.getParameter("nazwisko")).thenReturn("Nowak");
        when(request.getParameter("wiek")).thenReturn("23");
        when(request.getParameter("plec")).thenReturn("kobieta");

        servlet.doGet(request, response);
        writer.flush();
        String html = body.toString();

        assertTrue(html.contains("<B>Wiek</B>: 23"), html);
        assertTrue(html.contains("<BR><B>Plec</B>: kobieta"), html);
    }

    @Test
    void shouldShowInitParamsAndUseDefaultsWhenRequestParamsMissing() throws Exception {
        servlet.doGet(request, response);
        writer.flush();
        String html = body.toString();

        assertTrue(html.contains("<B>Wiek</B>: 20"), html);
        assertTrue(html.contains("<BR><B>Plec</B>: nieznana"), html);
        assertTrue(html.contains("<B>domyslnyWiek</B>: 20"), html);
        assertTrue(html.contains("<BR><B>domyslnaPlec</B>: nieznana"), html);
    }
}
