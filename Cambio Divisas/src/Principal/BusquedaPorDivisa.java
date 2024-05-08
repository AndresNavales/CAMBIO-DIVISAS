package Principal;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class BusquedaPorDivisa {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);

        // Mostrar la lista de divisas disponibles
        System.out.println("INDIQUE LA ABREVIATURA DE LA MONEDA QUE DESEA VENDER: ");
        Divisas.main(args);

        // Obtener la divisa seleccionada por el usuario para la venta
        String divisaVender = lectura.nextLine().toUpperCase(); // Convertir a mayúsculas

        // Solicitar al usuario el monto a vender
        System.out.println("INDIQUE EL MONTO QUE DESEA VENDER: ");
        double montoVender = Double.parseDouble(lectura.nextLine());

        // Solicitar al usuario la moneda que desea comprar
        System.out.println("INDIQUE LA ABREVIATURA DE LA MONEDA QUE DESEA COMPRAR: ");
        String divisaComprar = lectura.nextLine().toUpperCase(); // Convertir a mayúsculas

        // Construir la URL de la API para la divisa seleccionada para la venta
        String direccionVender = "https://v6.exchangerate-api.com/v6/fe94de9f04bf1e2ec38c06dd/latest/" + divisaVender;

        // Construir la URL de la API para la divisa seleccionada para la compra
        String direccionComprar = "https://v6.exchangerate-api.com/v6/fe94de9f04bf1e2ec38c06dd/latest/" + divisaComprar;

        // Crear un cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Crear una solicitud HTTP GET para la divisa a vender
        HttpRequest requestVender = HttpRequest.newBuilder()
                .uri(URI.create(direccionVender))
                .build();

        // Enviar la solicitud y obtener la respuesta para la divisa a vender
        HttpResponse<String> responseVender = client.send(requestVender, HttpResponse.BodyHandlers.ofString());

        // Crear una solicitud HTTP GET para la divisa a comprar
        HttpRequest requestComprar = HttpRequest.newBuilder()
                .uri(URI.create(direccionComprar))
                .build();

        // Enviar la solicitud y obtener la respuesta para la divisa a comprar
        HttpResponse<String> responseComprar = client.send(requestComprar, HttpResponse.BodyHandlers.ofString());

        // Verificar si ambas solicitudes fueron exitosas (código de estado 200)
        if (responseVender.statusCode() == 200 && responseComprar.statusCode() == 200) {
            // Obtener el cuerpo de la respuesta (los datos JSON) para la divisa a vender
            String responseBodyVender = responseVender.body();

            // Convertir la respuesta JSON a un objeto Java para la divisa a vender
            Gson gsonVender = new Gson();
            ApiResponse apiResponseVender = gsonVender.fromJson(responseBodyVender, ApiResponse.class);

            // Obtener la tasa de cambio para la divisa a vender
            double tasaCambioVender = apiResponseVender.conversion_rates.get(divisaComprar);

            // Obtener el cuerpo de la respuesta (los datos JSON) para la divisa a comprar
            String responseBodyComprar = responseComprar.body();

            // Convertir la respuesta JSON a un objeto Java para la divisa a comprar
            Gson gsonComprar = new Gson();
            ApiResponse apiResponseComprar = gsonComprar.fromJson(responseBodyComprar, ApiResponse.class);

            // Obtener la tasa de cambio para la divisa a comprar
            double tasaCambioComprar = apiResponseComprar.conversion_rates.get(divisaVender);

            // Realizar la comparación de tasas de cambio
            if (tasaCambioVender > tasaCambioComprar) {
                // Si la tasa de cambio para la divisa a vender es mayor que la de la divisa a comprar, multiplicar el monto
                double montoComprar = montoVender * tasaCambioVender;
                System.out.printf("Monto a comprar en %s: %.3f%n", divisaComprar, montoComprar);
            } else if (tasaCambioVender == tasaCambioComprar) {
                // Si las tasas de cambio son iguales, el monto a comprar es el mismo que el monto a vender
                System.out.println("La tasa de cambio es igual, el monto a comprar es el mismo que el monto a vender.");
            } else {
                // Si la tasa de cambio para la divisa a vender es menor que la de la divisa a comprar, dividir el monto
                double montoComprar = montoVender / tasaCambioComprar;
                System.out.printf("Monto a comprar en %s: %.3f%n", divisaComprar, montoComprar);
            }
        } else {
            // Si alguna de las solicitudes no fue exitosa, mostrar el código de estado correspondiente
            System.out.println("Error al realizar la solicitud.");
            System.out.println("Código de estado para la divisa a vender: " + responseVender.statusCode());
            System.out.println("Código de estado para la divisa a comprar: " + responseComprar.statusCode());

        }
    }

    // Clase para representar la respuesta JSON de la API
    static class ApiResponse {
        // Un objeto JSON que contiene las tasas de cambio
        ConversionRates conversion_rates;

        // Clase para representar las tasas de cambio
        static class ConversionRates {
            // Las tasas de cambio para las diferentes divisas
            double USD;
            double ARS;
            double BRL;
            double CLP;
            double EUR;
            double BOB;
            double COP;

            // Método para obtener la tasa de cambio de una divisa específica
            public double get(String divisa) {
                switch (divisa) {
                    case "USD":
                        return USD;
                    case "ARS":
                        return ARS;
                    case "BRL":
                        return BRL;
                    case "CLP":
                        return CLP;
                    case "EUR":
                        return EUR;
                    case "BOB":
                        return BOB;
                    case "COP":
                        return COP;
                    default:
                        return 0.0; // Retorna 0 si la divisa no está disponible
                }
            }
        }
    }
}
