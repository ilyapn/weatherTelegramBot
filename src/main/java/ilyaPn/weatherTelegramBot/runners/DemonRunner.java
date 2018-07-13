package ilyaPn.weatherTelegramBot.runners;

import ilyaPn.weatherTelegramBot.demons.EmailingDemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemonRunner implements CommandLineRunner {
    @Autowired
    private EmailingDemon emailingDemon;

    @Override
    public void run(String... args) throws Exception {
        emailingDemon.execute();
    }
}
