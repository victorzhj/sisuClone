package fi.tuni.prog3.sisu;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.TreeMap;
public class networkHandler {
    private String sisuUrl = "https://sis-tuni.funidata.fi/kori/api/";
    private String tuniId = "universityId=tuni-university-root-id";
    private String defaultPeriod = "curriculumPeriodId=uta-lvv-2021";
    private String defaultLimit = "limit=1000";
    private String groupIdSearch = "by-group-id?";
    private TreeMap<String, String> endpoints = new TreeMap<String,String>();
    private HttpClient client = HttpClient.newHttpClient();

    public networkHandler(){
        endpoints.put("moduleSearch", "module-search");
        endpoints.put("modules", "modules/");
        endpoints.put("courseUnits", "course-units/");
    }
    
    public String getDegreePrograms(){
        HttpRequest req = moduleSearch(defaultPeriod, tuniId, "moduleType=DegreeProgramme", defaultLimit);
        return client.sendAsync(req, BodyHandlers.ofString())
        .thenApply(HttpResponse::body).join();
    }
    public String getModuleById(String id){
        HttpRequest req = moduleGet(id);
        return client.sendAsync(req, BodyHandlers.ofString())
        .thenApply(HttpResponse::body).join();
    }

    public String getModuleByGroupId(String id){
        HttpRequest req = moduleIdGet(id);
        return client.sendAsync(req, BodyHandlers.ofString())
        .thenApply(HttpResponse::body).join();
    }
    public String getCourseByGroupId(String id){
        HttpRequest req = courseIdGet(id);
        return client.sendAsync(req, BodyHandlers.ofString())
        .thenApply(HttpResponse::body).join();
    }

    private HttpRequest courseIdGet(String id) {
        var uri = URI.create(sisuUrl+endpoints.get("courseUnits")+groupIdSearch+id+"&"+tuniId);
        return createGetRequest(uri);
    }

    private HttpRequest moduleIdGet(String id) {
        var uri = URI.create(sisuUrl+endpoints.get("modules")+groupIdSearch+id+"&"+tuniId);
        return createGetRequest(uri);
    }

    private HttpRequest moduleGet(String id){
        var uri = URI.create(sisuUrl+endpoints.get("modules")+id);
        return createGetRequest(uri);
    }
    private HttpRequest moduleSearch(String period, String universityId, String moduleType,String limit){
        var uri = URI.create(sisuUrl+endpoints.get("moduleSearch")+"?"+period+"&"+universityId+"&"+moduleType+"&"+limit);
        return createGetRequest(uri);
    }
    private HttpRequest createGetRequest(URI url){
        return HttpRequest.newBuilder(url)
        .header("accept","application/json")
        .build();
    }



}
