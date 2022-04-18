package fi.tuni.prog3.sisu;
import java.net.URI;
import java.net.http.*;
import java.security.KeyStore.Entry;
import java.util.Map;
import java.util.TreeMap;

public class networkHandler {
    private String sisuUrl = "https://sis-tuni.funidata.fi/kori/api/";
    private String tuniId = "universityId=tuni-university-root-id";
    private String defaultPeriod = "curriculumPeriodId=uta-lvv-2021";
    private TreeMap<String, String> endpoints = new TreeMap<String,String>();
    private HttpClient client = HttpClient.newHttpClient();
    public networkHandler(){
        endpoints.put("moduleSearch", "module-search");
    }

    public getDegreePrograms(){

    }

    public moduleSearch(){

    }
    private HttpRequest createGetRequest(String url){
        return HttpRequest.newBuilder(URI.create(url))
        .header("accept","application/json")
        .build();
    }



}
