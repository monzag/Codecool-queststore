package com.codecool.jlamas.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public abstract class AbstractHandler implements HttpHandler {

    @Override
    public abstract void handle(HttpExchange httpExchange) throws IOException;

    protected abstract void addGetCommands (HttpExchange httpExchange);

    protected abstract void addPostCommands (HttpExchange httpExchange);

    protected String findCommand(HttpExchange httpExchange, Map<String, Callable> mapName) {
        String response = null;
        String path = httpExchange.getRequestURI().getPath();
        Set<String> keys = mapName.keySet();

        this.addGetCommands(httpExchange);
        this.addPostCommands(httpExchange);

        for (String key : keys) {
            if (path.matches(key)) {
                try {
                    response = (String) mapName.get(key).call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    protected Map<String, String> parseUserInputsFromHttp(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        return this.parseFormData(formData);
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    protected String parseStringFromURL(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath().split("/")[5];
    }

    protected Integer parseIntFromURL(HttpExchange httpExchange) {
        return Integer.valueOf(this.parseStringFromURL(httpExchange));
    }
}