package ru.test.task.trafficstatistics.procesor;

import org.springframework.stereotype.Component;
import ru.test.task.trafficstatistics.common.Month;
import ru.test.task.trafficstatistics.db.dao.RouterDAO;
import ru.test.task.trafficstatistics.db.dao.RouterTrafficDAO;

@Component
public class RouteInfoProcessor {

    public int getInfoTraffic(RouterDAO router, Month month) {
        return router.getTraffic().stream().
                filter(t -> t.getMonth().equals(month))
                .toArray(RouterTrafficDAO[]::new)[0].getValue();
    }

}
