package ru.test.task.trafficstatistics.db;

import org.springframework.data.repository.CrudRepository;
import ru.test.task.trafficstatistics.db.dao.ProfileDAO;

public interface ProfileRepository extends CrudRepository<ProfileDAO, Long> {
}
