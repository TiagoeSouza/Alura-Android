package br.com.android.tiago.agendaIII.web;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Tiago on 07/11/2017.
 */

public class WebClient {

    public String post(String json) {
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.addRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);

            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            connection.connect();
            Scanner scanner = new Scanner(connection.getInputStream());
            String response = scanner.next();
            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
