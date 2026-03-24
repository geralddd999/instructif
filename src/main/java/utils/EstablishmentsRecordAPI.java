package utils;

import jakarta.json.JsonObject;
import metier.modele.Establishment;

public class EstablishmentsRecordAPI {
    public static JsonObject fetchAPI(){

    }
    public static Establishment buildFromJSON(JsonObject json){
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
            Integer postalCode = Integer.parseInt(json.getString("code_postal_uai"));

            //do we use setters or what

            return new Establishment(code, name, sector,
                    address, commune, lat, lon,
                    departmentCode, academyCode,
                    department, academy, postalCode);

    }
}
