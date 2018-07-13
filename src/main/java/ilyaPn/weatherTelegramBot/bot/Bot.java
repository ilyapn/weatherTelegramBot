package ilyaPn.weatherTelegramBot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class Bot extends AbilityBot {
    private BotAnswers botAnswers ;

    @Autowired
    Bot(@Value("${BOT_TOKEN}")String botToken,
        @Value("${BOT_NAME}")String botUsername,
        BotAnswers botAnswers) {
        super(botToken, botUsername);
        this.botAnswers = botAnswers;
    }


    public int creatorId() {
        return 0;
    }

    public Ability help(){
        return Ability.builder()
                .name("help")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(botAnswers.helpAnswer(),ctx.chatId()))
                .build();
    }
    public Ability start(){
        return Ability.builder()
                .name("start")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(botAnswers.startAnswer(), ctx.chatId()))
                .build();
    }
    public Ability setCity(){
        return Ability.builder()
                .name("город")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {silent.send(botAnswers.cityAddAnswer(ctx), ctx.chatId());
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

    public Ability unsubscribe(){
        return Ability.builder()
                .name("отписаться")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(botAnswers.unsubscribeAnswer(ctx),ctx.chatId()))
                .build();
    }

}

