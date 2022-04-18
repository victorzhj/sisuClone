package fi.tuni.prog3.sisu;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyStore.Entry;
import java.util.Map;
import java.util.TreeMap;
public class networkHandler {
    private String sisuUrl = "https://sis-tuni.funidata.fi/kori/api/";
    private String tuniId = "universityId=tuni-university-root-id";
    private String defaultPeriod = "curriculumPeriodId=uta-lvv-2021";
    private String defaultLimit = "limit=1000";
    private TreeMap<String, String> endpoints = new TreeMap<String,String>();
    private HttpClient client = HttpClient.newHttpClient();

    public networkHandler(){
        endpoints.put("moduleSearch", "module-search");
    }
    
    public degreeData getDegreePrograms(){
        HttpRequest req = moduleSearch(defaultPeriod, tuniId, "moduleType=DegreeProgramme", defaultLimit);
        return client.sendAsync(req, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept((res)->{
            return degreeData(res);

        });
    }
    
    public HttpRequest moduleSearch(String period, String universityId, String moduleType,String limit){
        var uri = URI.create(sisuUrl+endpoints.get("moduleSearch")+"?"+period+"&"+universityId+"&"+moduleType+"&"+limit);
        return createGetRequest(uri);
    }
    private HttpRequest createGetRequest(URI url){
        return HttpRequest.newBuilder(url)
        .header("accept","application/json")
        .build();
    }



}
