/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import metier.modele.Establishment;

/**
 *
 * @author gschambiram
 */
public class EstablishmentUtils {
    public static Establishment createEstablishmentFromRecord(String codeEstablishment){
        JsonObject result = null;

        Establishment resultEstablishment = null;
        try {

            // TODO: adapter l'URL de l'API et la liste des paramètres
            URI requestUri = URI.create(
                    "https://data.education.gouv.fr/api/explore/v2.1/catalog/datasets/fr-en-adresse-et-geolocalisation-etablissements-premier-et-second-degre/records"
                    + "?refine=numero_uai:"+ URLEncoder.encode(codeEstablishment, StandardCharsets.UTF_8)
                );

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(requestUri).GET().build();
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String body = (String) httpResponse.body();
                System.out.println(body);

                result = Json.createReader(new StringReader(body)).readObject();

                 String nameEstablishment = result.getJsonArray("results").getJsonObject(0).getString("appellation_officielle");
                 
                 System.out.println(nameEstablishment);
                // construct an establishment

                resultEstablishment = new Establishment(codeEstablishment, nameEstablishment);
            }
            else {
                throw new IOException("HTTP Error Status Code "+httpResponse.statusCode());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            result = null;
        }

        
        return resultEstablishment;
    }

    
}
