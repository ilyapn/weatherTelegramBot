package ilyaPn.firstBootProject.rest;

import ilyaPn.firstBootProject.common.City;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class WeatherController {
    City city = new City();

    @RequestMapping(value = "/getWeather/{cityName}", method = GET)
    @ResponseBody
    public WeatherDto getWeather(@PathVariable("cityName") String cityName) {
        ArrayList list = new ArrayList();
        WeatherDto weatherDto = new WeatherDto();
        OWM owm = new OWM("1cd0b9ac594ef8fa0b7b7d0000bccfd4");
        int cityId = 0;
        list.add(weatherDto);
        try {
            cityId = city.getCityId(cityName).get(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getTemperature(weatherDto, owm, cityId);
        return weatherDto;
    }

    public void getTemperature(WeatherDto weatherDto, OWM owm, int cityId) {
        try {
            CurrentWeather cwd = owm.currentWeatherByCityId(cityId);
            weatherDto.setId(cwd.getCityId());
            weatherDto.setName(cwd.getCityName());
            weatherDto.setTemp(cwd.getMainData().getTemp() - 273.15);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
    private void insertToBase() {
       // Emailing emailing = new Emailing("",0,1);
        //repository.save(emailing);

    }
}
