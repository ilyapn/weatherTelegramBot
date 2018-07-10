package ilyaPn.firstBootProject.bot;

import ilyaPn.firstBootProject.Repository.EmailingRepository;
import ilyaPn.firstBootProject.common.City;
import ilyaPn.firstBootProject.common.Weather;
import ilyaPn.firstBootProject.model.Emailing;
import net.aksingh.owmjapis.api.APIException;
import org.telegram.abilitybots.api.objects.MessageContext;

import java.util.Calendar;
import java.util.Date;

public class BotAnswers {
    public EmailingRepository repository;

    public EmailingRepository getRepository() {
        return repository;
    }

    public void setRepository(EmailingRepository repository) {
        this.repository = repository;
    }

    public City city = new City();

    public City getCity() {
        return city;
    }

    public Weather weather = new Weather();

    public BotAnswers() {
    }

    public void addEmailing(long chatId, long cityId, int updateFrequency) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, updateFrequency);
        Date date = cal.getInstance().getTime();
        Emailing emailing = new Emailing();
        emailing.setChatId(chatId);
        emailing.setCityId(cityId);
        emailing.setUpdateFrequency(updateFrequency);
        emailing.setNextTime(date.toString());
        repository.save(emailing);
    }

    public String getWeatherAnswer(long chatId) {
        try {
            return weather.getCelsiusTemperature(repository.findFirstByChatId(chatId).getCityId())
                    + " по цельсию";
        } catch (APIException e) {
            return "что то пошло не так c API";
        }
    }

    public void setTime(MessageContext context) {
        Emailing emailing = repository.findFirstByChatId(context.chatId());
        addEmailing(emailing.getChatId(), emailing.getCityId(), Integer.parseInt(context.firstArg()));
    }

    public String setTimeAnswer(MessageContext context) {
        if (repository.findFirstByChatId(context.chatId()) == null)
            return "ты не добавил город";
        setTime(context);
        return "теперь ты будешь получать сообщение о погоде раз в " + context.firstArg() + " час";
    }
}