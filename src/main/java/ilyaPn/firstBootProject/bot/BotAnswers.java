package ilyaPn.firstBootProject.bot;

import ilyaPn.firstBootProject.Repository.EmailingRepository;
import ilyaPn.firstBootProject.common.City;
import ilyaPn.firstBootProject.common.Weather;
import ilyaPn.firstBootProject.model.Emailing;
import net.aksingh.owmjapis.api.APIException;
import org.telegram.abilitybots.api.objects.MessageContext;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

public class BotAnswers {

    public BotAnswers(EmailingRepository repository) {
        this.repository = repository;
    }

    public EmailingRepository repository;



    public void addEmailing(long chatId, long cityId, int updateFrequency) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, updateFrequency);
        Date date = cal.getTime();
        Emailing emailing = new Emailing();
        emailing.setChatId(chatId);
        emailing.setCityId(cityId);
        emailing.setUpdateFrequency(updateFrequency);
        emailing.setNextTime(date.toString());
        repository.save(emailing);
    }

    public String getWeatherAnswer(long chatId) {
        try {
            return Weather.getCelsiusTemperature(repository.findFirstByChatId(chatId).getCityId())
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

    public String cityAddAnswer(MessageContext context){
        if (City.hasCity(context.firstArg())) {
            try {
                addEmailing(context.chatId(), City.getCityId(context.firstArg()).get(0), 1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return "теперь ты будешь получать сообщение о погоде в " + context.firstArg();
        }
        return "я не нашел токого города =(";
    }
    public String startAnswer(){
        return "Для того что бы пользоваться ботом посмотри команды с помощью /help";
    }
    public String helpAnswer(){
        return "/город название_города \n" +
                "название города на английском\n" +
                "/время частота_обновления_кол-во_часов \n" +
                "по умолчанию время обновления 1 час\n" +
                "/сейчас что бы узнать погоду";
    }
}