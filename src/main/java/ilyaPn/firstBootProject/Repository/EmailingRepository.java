package ilyaPn.firstBootProject.Repository;

import ilyaPn.firstBootProject.model.Emailing;
import org.springframework.data.repository.CrudRepository;

public interface EmailingRepository extends CrudRepository<Emailing,Integer> {
    Emailing findFirstByChatId(long chatId);
}
