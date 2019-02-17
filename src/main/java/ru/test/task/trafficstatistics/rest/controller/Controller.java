package ru.test.task.trafficstatistics.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.task.trafficstatistics.common.Month;
import ru.test.task.trafficstatistics.db.ProfileRepository;
import ru.test.task.trafficstatistics.db.RouteRepository;
import ru.test.task.trafficstatistics.db.dao.ProfileDAO;
import ru.test.task.trafficstatistics.db.dao.RouterDAO;
import ru.test.task.trafficstatistics.procesor.RouteInfoProcessor;
import ru.test.task.trafficstatistics.procesor.validator.ValidationProcessor;
import ru.test.task.trafficstatistics.rest.payload.rq.ProfilePayload;
import ru.test.task.trafficstatistics.rest.payload.rq.RouterPayload;
import ru.test.task.trafficstatistics.rest.payload.rs.ResponseOperationPayload;
import ru.test.task.trafficstatistics.rest.payload.rs.ResponseRouterInfoPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.test.task.trafficstatistics.utils.TransformerHelper.convertToProfileDAO;
import static ru.test.task.trafficstatistics.utils.TransformerHelper.convertToRouterDAO;

@RestController("/")
public class Controller {

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private RouteInfoProcessor routeInfoProcessor;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    @Qualifier("profileValidationProcessor")
    private ValidationProcessor<ProfilePayload> profileValidationProcessor;
    @Autowired
    @Qualifier("routerValidationProcessor")
    private ValidationProcessor<RouterDAO> routerValidationProcessor;

    /**
     * Получение данных потребления трафика роутером.
     * @param id          - идентификатор роутера
     * @param monthNumber - номер месяца (1-12)
     * @return - значение потребления
     */
    @GetMapping(path = "/getTrafficInfo/{id}/{month}")
    public @ResponseStatus ResponseEntity getTrafficInfo(@PathVariable("id") long id,
                                                         @PathVariable("month") int monthNumber) {
        Optional<RouterDAO> router = routeRepository.findById(id);
        if (monthNumber < 1 || monthNumber > 12) {
            return ResponseEntity.ok(ResponseRouterInfoPayload.incorrectMonth());
        }
        return router.map(r -> {
                    Month month = Month.byNumber(monthNumber);
                    int value = routeInfoProcessor.getInfoTraffic(r, month);
                    return ResponseEntity.ok(ResponseRouterInfoPayload.info(id, month, value));
                }
        ).orElse(ResponseEntity.ok(ResponseRouterInfoPayload.notFound(id)));

    }

    @PutMapping(path = "/loadProfiles")
    public @ResponseStatus ResponseEntity loadProfiles(@RequestBody List<ProfilePayload> profilePayloads) {
        boolean isValid = profilePayloads.stream()
                .allMatch(profile -> profileValidationProcessor.validation(profile));
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseOperationPayload.error(2, "Ошибка загрузки профилей, данные невалидны!"));
        }
        List<ProfileDAO> profiles = convertToProfileDAO(profilePayloads);

        //fixme загрузка только записей которых нет
//        List<Long> notSaveIds = new ArrayList<>();
//
//        profiles.forEach(profile -> {
//            try {
//                this.profileRepository.save(profile);
//            } catch (DataIntegrityViolationException e) {
//                notSaveIds.add(profile.getId());
//            }
//        });
//        if (!notSaveIds.isEmpty()) {
//            return ResponseEntity.ok(ResponseOperationPayload.error(4, "Профили " + notSaveIds.toString() + " уже существую!"));
//        }
        //fixme если в списке будет запись которая существует то, нечего не добавится
        try {
            this.profileRepository.saveAll(profiles);
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseOperationPayload.error(3,
                    "Ошибка загрузки профилей, в списке есть существующие профилию"));
        }
        return ResponseEntity.ok(ResponseOperationPayload.ok("Данные успешно загружены."));
    }

    @PutMapping(path = "/loadRouters")
    public @ResponseStatus ResponseEntity loadRouter(@RequestBody List<RouterPayload> routerPayloads) {
        List<RouterDAO> valid = new ArrayList<>();
        List<RouterDAO> invalid = new ArrayList<>();
        List<ProfileDAO> profiles = (List<ProfileDAO>) this.profileRepository.findAllById(routerPayloads.stream()
                .map(RouterPayload::getProfileId).collect(Collectors.toList()));
        List<RouterDAO> routers = convertToRouterDAO(routerPayloads, profiles);

        routers.forEach(router -> {
            if (this.routerValidationProcessor.validation(router)) {
                valid.add(router);
            } else {
                invalid.add(router);
            }
        });

        this.routeRepository.saveAll(valid);
        if (!invalid.isEmpty()) {
            String ids = invalid.stream().map(RouterDAO::getId).collect(Collectors.toList()).toString();
            return ResponseEntity.ok(ResponseOperationPayload.error(1, "Данные роутеров " + ids + " не загружены."));
        }
        return ResponseEntity.ok(ResponseOperationPayload.ok("Все данные роутеров загружены."));
    }

}
