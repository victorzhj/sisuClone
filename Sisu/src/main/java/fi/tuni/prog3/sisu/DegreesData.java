package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

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
     * @return TreeMap<String, TreeMap<String, String>> TreeMap of degrees data.
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
     * @return TreeSet<String> TreeSet with degrees names.
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
