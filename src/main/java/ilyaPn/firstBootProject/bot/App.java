package ilyaPn.firstBootProject.bot;

import ilyaPn.firstBootProject.Repository.EmailingRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
@Component
public class App {
    EmailingRepository repository;
    private static String BOT_NAME = "ForYotaBot";
    private static String BOT_TOKEN = "610785523:AAEbKHdzRpskMBF1Mtqvg_bMPJX_5DWOmpo";

    public App(EmailingRepository repository) {
        this.repository = repository;
    }

    public void app() {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            Bot bot = new Bot(BOT_TOKEN, BOT_NAME,repository);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
