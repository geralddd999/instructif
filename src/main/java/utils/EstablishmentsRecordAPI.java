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
import java.util.ArrayList;

import metier.modele.Establishment;

public class EstablishmentsRecordAPI {

    private static JsonObject fetchEstablishmentFromAPI(String establishmentUai){
        JsonObject result = null;

        try {

            // TODO: adapter l'URL de l'API et la liste des paramètres
            URI requestUri = URI.create(
                    "https://data.education.gouv.fr/api/explore/v2.1/catalog/datasets/fr-en-adresse-et-geolocalisation-etablissements-premier-et-second-degre/records"
                            + "?refine=numero_uai:"+ URLEncoder.encode(establishmentUai, StandardCharsets.UTF_8)
            );

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(requestUri).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String body = httpResponse.body();
                //System.out.println(body);

                result = Json.createReader(new StringReader(body)).readObject();
                result = result.getJsonArray("results").getJsonObject(0);
            }
            else {
                throw new IOException("HTTP Error Status Code "+httpResponse.statusCode());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            result = null;
        }

        return result;

    }

    private static JsonObject fetchEstablishmentIPSFromAPI(String establishmentUai){
        JsonObject result = null;

        try {

            // TODO: adapter l'URL de l'API et la liste des paramètres
            URI requestUri = URI.create(
                    "https://data.education.gouv.fr/api/explore/v2.1/catalog/datasets/fr-en-ips_colleges/records"
                            + "?refine=numero_uai:"+ URLEncoder.encode(establishmentUai, StandardCharsets.UTF_8)
                            + "&order_by=" + URLEncoder.encode("rentree_scolaire desc", StandardCharsets.UTF_8)
                            + "&limit" + URLEncoder.encode("1", StandardCharsets.UTF_8)
            );

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(requestUri).GET().build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                String body = httpResponse.body();
                //System.out.println(body);

                result = Json.createReader(new StringReader(body)).readObject();
                result = result.getJsonArray("results").getJsonObject(0);
            }
            else {
                throw new IOException("HTTP Error Status Code "+httpResponse.statusCode());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
            result = null;
        }

        return result;
    }
    public static Establishment buildEstablishmentFromAPI(String establishmentUai){
        JsonObject json = EstablishmentsRecordAPI.fetchEstablishmentFromAPI(establishmentUai);
        if(json == null){
            return null;
        }

        String code = json.getString("numero_uai");
            String name = json.getString("appellation_officielle");
            String sector = json.getString("secteur_public_prive_libe");
            String commune = json.getString("libelle_commune");
            String address = json.getString("adresse_uai");
            Double lat = json.getJsonNumber("latitude").doubleValue();
            Double lon = json.getJsonNumber("longitude").doubleValue();
            String departmentCode = json.getString("code_departement");
            String academyCode = json.getString("code_academie");
            String department = json.getString("libelle_departement");
            String academy = json.getString("libelle_academie");
            String postalCode = json.getString("code_postal_uai");

            return new Establishment(code, name, sector,
                    address, postalCode, commune, lat, lon,
                    departmentCode, academyCode,
                    department, academy);
    }
    public static ArrayList<Double> getEstablishmentIPSFromAPI(String establishmentUai){
        JsonObject json = EstablishmentsRecordAPI.fetchEstablishmentIPSFromAPI(establishmentUai);
        ArrayList<Double> res;
        if(json == null){
            res = null;
        }
        else
        {
            Double ips = json.getJsonNumber("ips").doubleValue();
            Double ecart = json.getJsonNumber("ecart_type_de_l_ips").doubleValue();

            res= new ArrayList<>();

            res.add(ips);
            res.add(ecart);
        }


        return res;

    }

}

