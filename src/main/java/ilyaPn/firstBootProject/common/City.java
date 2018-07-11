package ilyaPn.firstBootProject.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class City {

    private static FileReader fileReader;

    public static List<Integer> getCityId(String CityName) throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        fileReader = new FileReader("src/main/resources/city.list.json");
        JsonElement element = jsonParser.parse(fileReader);
        JsonArray jsonArray = element.getAsJsonArray();
        for (JsonElement jsonElement : jsonArray)
            if ((jsonElement.getAsJsonObject().get("name").getAsString().equals(CityName)))
                list.add(jsonElement.getAsJsonObject().get("id").getAsInt());
        return list;
    }

    public static boolean hasCity(String cityName) {
        try {
            if(!getCityId(cityName).isEmpty())
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
