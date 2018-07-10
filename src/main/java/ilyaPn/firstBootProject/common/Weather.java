package ilyaPn.firstBootProject.common;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;

/**
 * Created by ilyaP on 09.07.2018.
 */
public class Weather {
    OWM owm = new OWM("1cd0b9ac594ef8fa0b7b7d0000bccfd4");

    public float getCelsiusTemperature(long cityId) throws APIException {
            return (float) (owm.currentWeatherByCityId(Math.toIntExact(cityId)).getMainData().getTemp()- 273.15);
    }
}
