package ilyaPn.weatherTelegramBot.runners;

import ilyaPn.weatherTelegramBot.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


@Component
public class BotRunner implements CommandLineRunner {
    private Bot bot;

    @Autowired
    public BotRunner(Bot bot) {
        this.bot = bot;
    }

    private void app() {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        app();
    }
}
