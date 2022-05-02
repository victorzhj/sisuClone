package fi.tuni.prog3.sisu;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Collectors;
import java.util.Map;
public class networkHandler {
    private static final String sisuUrl = "https://sis-tuni.funidata.fi/kori/api/";
    private static final String tuniId = "universityId=tuni-university-root-id";
    private static final String defaultPeriod = "curriculumPeriodId=uta-lvv-2021";
    private static final String defaultLimit = "limit=1000";
    private static final String groupIdSearch = "by-group-id?groupId=";
    private static final TreeMap<String, String> endpoints = new TreeMap<String,String>();
    private HttpClient client = HttpClient.newHttpClient();

    /**
     * Default constructor for networkHandler
     */
    public networkHandler(){
        endpoints.put("moduleSearch", "module-search");
        endpoints.put("modules", "modules/");
        endpoints.put("courseUnits", "course-units/");
    }
    
    
    /** 
     * Retrieves all degree programmes from sisu api with tuni id from 2021 curriculum period
     * @return String Containing data from the request
     */
    public String getDegreePrograms(){
        HttpRequest req = moduleSearch(defaultPeriod, tuniId, "moduleType=DegreeProgramme", defaultLimit);
        return client.sendAsync(req, BodyHandlers.ofString())
        .thenApply(HttpResponse::body).join();
    }
    
    /** 
     * Searches module by id.
     * @param id Of the module to be searched
     * @return String array containing data from the request and the original id used for search
     */
    public String[] getModuleById(String id){
        HttpRequest req = moduleGet(id);
        String[] retValue={client.sendAsync(req, BodyHandlers.ofString())
            .thenApply(HttpResponse::body).join(), id};
        return retValue;
    }

    
    /** 
     *  Searches module by group-id
     * @param id Of the module to be searched
     * @return String array containing data from the request and the original id used for search
     */
    public String[] getModuleByGroupId(String id){
        HttpRequest req = moduleIdGet(id);
        String[] retValue={client.sendAsync(req, BodyHandlers.ofString())
            .thenApply(HttpResponse::body).join(), id};
        return retValue;
    }

    
    /** 
     * Searches course by group-id
     * @param id Of the course to be searched
     * @return String array containing data from the request and the original id used for search
     */
    public String[] getCourseByGroupId(String id){
        HttpRequest req = courseIdGet(id);
        String[] retValue={client.sendAsync(req, BodyHandlers.ofString())
            .thenApply(HttpResponse::body).join(), id};
            return retValue;
    }

    
    /** 
     * 
     * @hidden
     */
    private HttpRequest courseIdGet(String id) {
        var uri = URI.create(sisuUrl+endpoints.get("courseUnits")+groupIdSearch+id+"&"+tuniId);
        return createGetRequest(uri);
    }

    
    /** 
     * @hidden
     */
    private HttpRequest moduleIdGet(String id) {
        var uri = URI.create(sisuUrl+endpoints.get("modules")+groupIdSearch+id+"&"+tuniId);
        return createGetRequest(uri);
    }

    
    /** 
     * @hidden
     */
    private HttpRequest moduleGet(String id){
        var uri = URI.create(sisuUrl+endpoints.get("modules")+id);
        return createGetRequest(uri);
    }
    
    /** 
     * @hidden
     */
    private HttpRequest moduleSearch(String period, String universityId, String moduleType,String limit){
        var uri = URI.create(sisuUrl+endpoints.get("moduleSearch")+"?"+period+"&"+universityId+"&"+moduleType+"&"+limit);
        return createGetRequest(uri);
    }
    
    /** 
     * @hidden
     */
    private HttpRequest createGetRequest(URI url){
        return HttpRequest.newBuilder(url)
        .header("accept","application/json")
        .build();
    }
}
