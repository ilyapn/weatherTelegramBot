package ilyaPn.weatherTelegramBot.Repository;

import ilyaPn.weatherTelegramBot.model.Emailing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailingRepository extends CrudRepository<Emailing,Integer> {
    Emailing findFirstByChatId(long chatId);

    List<Emailing> findAll();
}
