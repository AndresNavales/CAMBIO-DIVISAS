package Principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Divisas {
    public static void main(String[] args) {

        ArrayList<String> nombresDivisas = new ArrayList<>();

        nombresDivisas.add("1 - USD Dolar Estado Unidense");
        nombresDivisas.add("2 - ARS Peso Argentino");
        nombresDivisas.add("3 - BRL Real brasileño");
        nombresDivisas.add("4 - CLP Peso Chileno");
        nombresDivisas.add("5 - EUR Euro");
        nombresDivisas.add("6 - BOB Boliviano");
        nombresDivisas.add("7 - COP Peso colombiano");

        System.out.println("Nombres de las divisas:");
        for (String divisa : nombresDivisas) {
            System.out.println(divisa);
        }
    }
}

