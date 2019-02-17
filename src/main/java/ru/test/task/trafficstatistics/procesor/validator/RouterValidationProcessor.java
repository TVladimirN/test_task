package ru.test.task.trafficstatistics.procesor.validator;

import org.springframework.stereotype.Component;
import ru.test.task.trafficstatistics.common.Month;
import ru.test.task.trafficstatistics.db.dao.ProfileDAO;
import ru.test.task.trafficstatistics.db.dao.ProfileFractionDAO;
import ru.test.task.trafficstatistics.db.dao.RouterDAO;
import ru.test.task.trafficstatistics.db.dao.RouterTrafficDAO;

@Component
public class RouterValidationProcessor implements ValidationProcessor<RouterDAO> {

    @Override
    public boolean validation(RouterDAO routerDAO) {
        if (routerDAO.getProfile() == null) {
            return false;
        }
        if (routerDAO.getTraffic().size() != 12) {
            return false;
        }
        return routerDAO.getTraffic().stream()
                .allMatch(traffic -> {
                    int curValue = traffic.getValue();
                    int precedingValue = getPrecedingValue(routerDAO, traffic.getMonth());
                    double fraction = getFraction(routerDAO.getProfile(), traffic.getMonth());
                    return checkValue(curValue, precedingValue, getTotalExpense(routerDAO), fraction);
                });
    }

    private double getFraction(ProfileDAO profileDAO, Month month) {
        return profileDAO.getFraction().stream()
                .filter(f -> f.getMonth().equals(month))
                .toArray(ProfileFractionDAO[]::new)[0].getFraction();
    }

    private int getPrecedingValue(RouterDAO routerDAO, Month month) {
        Month preceding = Month.getPreceding(month);
        if (preceding.equals(month)) {
            return 0;
        }
        return routerDAO.getTraffic().stream()
                .filter(t -> t.getMonth().equals(preceding))
                .toArray(RouterTrafficDAO[]::new)[0].getValue();
    }

    /**
     * Получение потребления за год.
     * Берем 11 (декабрь), так как у нас есть условие да и валидация что в данных все 12 месяцев.
     * @param routerDAO
     * @return
     */
    private int getTotalExpense(RouterDAO routerDAO) {
        return routerDAO.getTraffic().get(11).getValue();
    }

    private boolean checkValue(int curValue, int precedingValue, int total, double fraction) {
        //Значение потребления за месяц считаеться как curMonth - precedingMonth
        int expense = curValue - precedingValue;
        if (expense < 0) {
            return false;
        }
        int middle = (int) (total * fraction);
        int lowerThreshold = (int) (middle - middle * 0.25);
        int upperThreshold = (int) (middle + middle * 0.25);
        return expense >= lowerThreshold && expense <= upperThreshold;
    }

}
