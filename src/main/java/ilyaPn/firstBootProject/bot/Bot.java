package ilyaPn.firstBootProject.bot;

import ilyaPn.firstBootProject.Repository.EmailingRepository;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;

import java.io.FileNotFoundException;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class Bot extends AbilityBot {
    final BotAnswers botAnswers = new BotAnswers();

    protected Bot(String botToken, String botUsername,EmailingRepository repository) {
        super(botToken, botUsername);
        this.botAnswers.setRepository(repository);
    }

    public int creatorId() {
        return 0;
    }

    public Ability help(){
        return Ability.builder()
                .name("help")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("/город название_города \n" +
                        "название города на английском\n" +
                        "/время частота_обновления_кол-во_часов \n" +
                        "по умолчанию время обновления 1 час\n" +
                        "/сейчас что бы узнать погоду",ctx.chatId()))
                .build();
    }
    public Ability start(){
        return Ability.builder()
                .name("start")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Для того что бы пользоваться" +
                        " ботом посмотри команды с помощью /help", ctx.chatId()))
                .build();
    }
    public Ability setCity(){
        return Ability.builder()
                .name("город")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {silent.send(botAnswers.getCity().cityAddAnswer(ctx), ctx.chatId());
                    try {
                        botAnswers.addEmailing(Math.toIntExact(ctx.chatId()), botAnswers.getCity().getCityId(ctx.firstArg()).get(0), 1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }
    public Ability now(){
        return Ability.builder()
                .name("сейчас")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(botAnswers.getWeatherAnswer(ctx.chatId()), ctx.chatId()))
                .build();
    }

    public Ability setTime(){
        return Ability.builder()
                .name("время")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(botAnswers.setTimeAnswer(ctx),ctx.chatId()))
                .build();
    }
    public void addEmailing(long chatId, long cityId, int updateFrequency){
        botAnswers.addEmailing(chatId, cityId, updateFrequency);
    }

    public String getWeatherAnswer(long chatId){
        return botAnswers.getWeatherAnswer(chatId);
    }

    public void setTime(MessageContext context){
        botAnswers.setTime(context);
    }

    public String setTimeAnswer(MessageContext context){
        return botAnswers.setTimeAnswer(context);
    }

}

