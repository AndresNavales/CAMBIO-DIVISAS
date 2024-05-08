package Principal;

// Clase para representar la respuesta JSON de la API
public class ApiResponse {
    ConversionRates conversion_rates;

    // Clase interna para representar las tasas de cambio
    static class ConversionRates {
        double USD;
        double ARS;
        double BRL;
        double CLP;
        double EUR;
        double BOB;
        double COP;

        // Agrega más tasas de cambio según sea necesario
    }
}
