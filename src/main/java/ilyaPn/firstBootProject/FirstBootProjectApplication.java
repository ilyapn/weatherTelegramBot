package ilyaPn.firstBootProject;

import ilyaPn.firstBootProject.Repository.EmailingRepository;
import ilyaPn.firstBootProject.bot.App;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class FirstBootProjectApplication{

	public static void main(String[] args) {
		SpringApplication.run(FirstBootProjectApplication.class, args);

	}
	@Bean
	public CommandLineRunner AppBotStart(EmailingRepository repository){
		return args -> {
			new App(repository).app();
		};
	}

}
