package ilyaPn.firstBootProject.common;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;

public class Weather {
    static OWM owm = new OWM("1cd0b9ac594ef8fa0b7b7d0000bccfd4");

    public static float getCelsiusTemperature(long cityId) throws APIException {
            return (float) (owm.currentWeatherByCityId(Math.toIntExact(cityId)).getMainData().getTemp()- 273.15);
    }
}
