package ru.test.task.trafficstatistics.procesor.validator;

import org.springframework.stereotype.Component;
import ru.test.task.trafficstatistics.rest.payload.rq.ProfilePayload;

@Component("profileValidationProcessor")
public class ProfileValidationProcessor implements ValidationProcessor<ProfilePayload> {


    public boolean validation(ProfilePayload profile) {
        if (profile.getFractions().size() != 12) {
            return false;
        }
        int sum = profile.getFractions().stream().mapToInt(ProfilePayload.Fraction::getIntFraction).sum();
        return sum == 100;
    }

}
