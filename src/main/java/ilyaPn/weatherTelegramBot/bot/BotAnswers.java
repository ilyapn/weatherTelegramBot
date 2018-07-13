package ilyaPn.weatherTelegramBot.bot;

import ilyaPn.weatherTelegramBot.repository.EmailingRepository;
import ilyaPn.weatherTelegramBot.common.City;
import ilyaPn.weatherTelegramBot.common.Weather;
import ilyaPn.weatherTelegramBot.model.Emailing;
import net.aksingh.owmjapis.api.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.MessageContext;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

@Component
public class BotAnswers {
    private EmailingRepository repository;
    private Weather weather;

    @Autowired
    public BotAnswers(EmailingRepository repository, Weather weather) {
        this.repository = repository;
        this.weather = weather;
    }


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
            return weather.getCelsiusTemperature(repository.findFirstByChatId(chatId).getCityId())
                    + " по цельсию";
        } catch (APIException e) {
            return "что то пошло не так c API";
        }
    }

    private void setTime(MessageContext context) throws Exception {
        Emailing emailing = repository.findFirstByChatId(context.chatId());
        int hours = Integer.parseInt(context.firstArg());
        if (hours <= 0)
            throw new Exception("invalid hours");
        addEmailing(emailing.getChatId(), emailing.getCityId(), hours);
    }

    public String setTimeAnswer(MessageContext context) {
        if (context.arguments().length == 0)
            return "ты не добавил кол-во часов";
        try {
            setTime(context);
        } catch (Exception e) {
            return "что то пошло не так";
        }
        return "теперь ты будешь получать сообщение о погоде раз в " + context.firstArg() + " час";
    }

    public String cityAddAnswer(MessageContext context){
        if(context.arguments().length == 0)
            return "ты не ввел город";
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
                "/сейчас что бы узнать погоду \n" +
                "/отписаться что бы отписаться от уведомлений";
    }

    public String unsubscribeAnswer(MessageContext ctx) {
        if (repository.findFirstByChatId(ctx.chatId()) == null)
            return "ты не имеешь подписки";
        repository.deleteById(ctx.chatId());
        return "ты больше не будешь получать уведомления";
    }
}