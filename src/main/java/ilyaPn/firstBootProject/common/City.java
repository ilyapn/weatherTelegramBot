package ilyaPn.firstBootProject.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ilyaPn.firstBootProject.Repository.EmailingRepository;
import org.telegram.abilitybots.api.objects.MessageContext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class City {
    public List<Integer> getCityId(String CityName) throws FileNotFoundException {
        ArrayList<Integer> list = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(new FileReader("src/main/resources/city.list.json"));
        JsonArray jsonArray = element.getAsJsonArray();
        for (JsonElement jsonElement : jsonArray)
            if ((jsonElement.getAsJsonObject().get("name").getAsString().equals(CityName)))
                list.add(jsonElement.getAsJsonObject().get("id").getAsInt());
        return list;
    }
    public long getChatIdFromEmailing(EmailingRepository repository, long chatId){
        return repository.findFirstByChatId(chatId).getChatId();
    }
    public String cityAddAnswer(MessageContext context){
        if (hasCity(context.firstArg())) {
            return "теперь ты будешь получать сообщение о погоде в " + context.firstArg();
        }
        return "я не нашел токого города =(";
    }

    private boolean hasCity(String cityName) {
        try {
            if(!getCityId(cityName).isEmpty())
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
