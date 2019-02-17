package ru.test.task.trafficstatistics.db;

import org.springframework.data.repository.CrudRepository;
import ru.test.task.trafficstatistics.db.dao.RouterDAO;

public interface RouteRepository extends CrudRepository<RouterDAO, Long> {
}
