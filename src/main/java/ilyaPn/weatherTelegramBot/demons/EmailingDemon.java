package ilyaPn.weatherTelegramBot.demons;


import ilyaPn.weatherTelegramBot.repository.EmailingRepository;
import ilyaPn.weatherTelegramBot.bot.Bot;
import ilyaPn.weatherTelegramBot.bot.BotAnswers;
import ilyaPn.weatherTelegramBot.model.Emailing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@Component
public class EmailingDemon{
    private EmailingRepository repository;
    private LocalDate currentDate;
    private LocalDate oldDate;
    private LocalDate date;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z y",Locale.ENGLISH);
    private Bot bot;
    private BotAnswers botAnswers;
    @Autowired
    public EmailingDemon(EmailingRepository repository, Bot bot,BotAnswers botAnswers) {
        this.repository = repository;
        this.bot = bot;
        this.botAnswers = botAnswers;
    }

    public void execute(){
        Thread thread = new Thread(EmailingDemon.this::run);
        thread.setDaemon(true);
        thread.setName("EmailingDemon");
        thread.start();

    }

    private void run(){
        while (true){
            currentDate = LocalDate.now();
            sendOut();
            oldDate = currentDate;
            try {
                Thread.sleep(20000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void sendOut(){
        List<Emailing> emailingList = repository.findAll();

        if (emailingList.isEmpty()) {
            return;
        }

        for (Emailing emailing : emailingList){
            date = LocalDate.parse(emailing.getNextTime(), formatter);
            if (date.isAfter(oldDate) && (date.isBefore(currentDate)|| date.isEqual(currentDate))) {
                sendMessage(emailing);
                rewriteEmailingNextTime(emailing);
            }
        }
    }

    private void rewriteEmailingNextTime(Emailing emailing){
        date = LocalDate.parse(emailing.getNextTime(), formatter);
        date.plus(emailing.getUpdateFrequency(), ChronoUnit.HOURS);
        emailing.setNextTime(date.format(formatter));
        repository.save(emailing);
    }

    private void sendMessage(Emailing emailing){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(emailing.getChatId());
        sendMessage.setText(botAnswers.getWeatherAnswer(emailing.getChatId()));
        DefaultAbsSender defaultAbsSender = new DefaultAbsSender(bot.getOptions()) {
            @Override
            public String getBotToken() {
                return bot.getBotToken();
            }
        };
        try {
            defaultAbsSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


}
