package com.codecool.jlamas.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Response {

    public void sendRedirectResponse(HttpExchange httpExchange, String location) throws IOException {
        httpExchange.getResponseHeaders().set("Location", location);
        httpExchange.sendResponseHeaders(302,-1);
    }

    public void sendOKResponse(String response, HttpExchange httpExchange) throws IOException{
        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
        os.close();
    }
}
