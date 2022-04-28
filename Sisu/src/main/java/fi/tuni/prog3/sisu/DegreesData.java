package fi.tuni.prog3.sisu;

import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * A class to get all the available degrees in the Sisu api.
 */
public class DegreesData {

    private static DegreesJsonClass degrees;

    /**
     * @hidden
     */
    private static void addDegrees(){
        networkHandler networkWorker = new networkHandler();
        Gson gson = new Gson();
        try{
            degrees = gson.fromJson(networkWorker.getDegreePrograms(), DegreesJsonClass.class);
        } catch (JsonSyntaxException e){
            System.out.println("Error with getting DegreesJsonClass: " + e);
        }
    }

    /**
     * Static function that stores every degree data to treemap.
     * TreeMap<Degree name, TreeMap<Degree data that you want, data>>
     * Degree data that you want can be "name", "groupId", "credits"
     * e.g if you want the groupId of Arkkitehtuurin kandidaattiohjelma you can get it by doing like so getDegreesInformation().get("Arkkitehtuurin kandidaattiohjelma").get("groupId").
     * @return TreeMap<String, TreeMap<String, String>> TreeMap with data of all the degrees.
     */
    public static TreeMap<String, TreeMap<String, String>> getDegreesInformation() {
        addDegrees();
        TreeMap<String, TreeMap<String, String>> toBeReturn = new TreeMap<>();
        for (var degreeData : degrees.getSearchResulst()) {
            TreeMap<String, String> temp = new TreeMap<>();
            temp.put("name", degreeData.getName());
            temp.put("groupId", degreeData.getGroupId());
            temp.put("credits", degreeData.getCreditsObject().getCredits());
            toBeReturn.put(degreeData.getName(), temp);
        }
        return toBeReturn;
    }

    /**
     * Static function that stores every degree name to TreeSet.
     * @return TreeSet<String> TreeSet with every degree name.
     */
    public static TreeSet<String> getDegreesNames(){
        addDegrees();
        TreeSet<String> toBeReturn = new TreeSet<>();
        for (var degreeData : degrees.getSearchResulst()) {
            toBeReturn.add(degreeData.getName());
        }
        return toBeReturn;
    }
}
