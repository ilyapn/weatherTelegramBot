package ilyaPn.weatherTelegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("keys.properties")
@SpringBootApplication
@EnableAutoConfiguration
public class FirstBootProjectApplication{
	public static void main(String[] args) {
		SpringApplication.run(FirstBootProjectApplication.class, args);
	}
}
