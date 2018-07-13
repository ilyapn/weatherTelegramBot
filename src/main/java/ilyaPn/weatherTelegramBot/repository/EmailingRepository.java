package ilyaPn.weatherTelegramBot.repository;

import ilyaPn.weatherTelegramBot.model.Emailing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailingRepository extends CrudRepository<Emailing,Long> {
    Emailing findFirstByChatId(long chatId);

    List<Emailing> findAll();

    @Override
    void deleteById(Long chatId);
}
