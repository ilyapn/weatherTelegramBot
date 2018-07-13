package ilyaPn.weatherTelegramBot.common;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Weather {
    @Value("${WEATHER_API_KEY}")
    private String apiKey;

    @Autowired
    public Weather() {}

    public float getCelsiusTemperature(long cityId) throws APIException {
            return (float) (new OWM(apiKey).currentWeatherByCityId(Math.toIntExact(cityId)).getMainData().getTemp()- 273.15);
    }
}
