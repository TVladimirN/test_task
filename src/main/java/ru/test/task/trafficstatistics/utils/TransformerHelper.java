package ru.test.task.trafficstatistics.utils;

import ru.test.task.trafficstatistics.db.dao.ProfileDAO;
import ru.test.task.trafficstatistics.db.dao.ProfileFractionDAO;
import ru.test.task.trafficstatistics.db.dao.RouterDAO;
import ru.test.task.trafficstatistics.db.dao.RouterTrafficDAO;
import ru.test.task.trafficstatistics.rest.payload.rq.ProfilePayload;
import ru.test.task.trafficstatistics.rest.payload.rq.RouterPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransformerHelper {

    private TransformerHelper() {
    }

    public static ProfileDAO convertToProfileDAO(ProfilePayload profilePayload) {
        ProfileDAO profile = new ProfileDAO();
        profile.setId(profilePayload.getId());
        List<ProfileFractionDAO> fractions = new ArrayList<>();
        profilePayload.getFractions().forEach(fraction -> {
            ProfileFractionDAO fraction1 = new ProfileFractionDAO();
            fraction1.setMonth(fraction.getMonth());
            fraction1.setFraction(fraction.getFraction());
            fractions.add(fraction1);
        });
        profile.setFraction(fractions);
        return profile;
    }

    public static List<ProfileDAO> convertToProfileDAO(List<ProfilePayload> profilePayloads) {
        ArrayList<ProfileDAO> profiles = new ArrayList<>();
        profilePayloads.forEach(pp -> profiles.add(TransformerHelper.convertToProfileDAO(pp)));
        return profiles;
    }

    public static RouterDAO convertToRouterDAO(RouterPayload routerPayload, ProfileDAO profile) {
        RouterDAO router = new RouterDAO();
        router.setProfile(profile);
        router.setId(routerPayload.getId());
        List<RouterTrafficDAO> traffics = routerPayload.getIndications().stream().map(indication -> {
            RouterTrafficDAO profileTraffic = new RouterTrafficDAO();
            profileTraffic.setMonth(indication.getMonth());
            profileTraffic.setValue(indication.getValue());
            return profileTraffic;
        }).collect(Collectors.toList());
        router.setTraffic(traffics);
        return router;
    }

    public static List<RouterDAO> convertToRouterDAO(List<RouterPayload> routerPayloads, List<ProfileDAO> profiles) {
        Map<Long, ProfileDAO> profilesMap = profiles.stream().collect(Collectors.toMap(ProfileDAO::getId, o -> o));
        return routerPayloads.stream()
                .map(routerPayload -> convertToRouterDAO(routerPayload, profilesMap.get(routerPayload.getId())))
                .collect(Collectors.toList());
    }

}
