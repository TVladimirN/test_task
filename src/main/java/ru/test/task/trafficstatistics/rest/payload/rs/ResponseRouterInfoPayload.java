package ru.test.task.trafficstatistics.rest.payload.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.test.task.trafficstatistics.common.Month;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRouterInfoPayload {

    private int status;
    private String msg;
    private Long id;
    private Month month;
    private Integer value;

    public static ResponseRouterInfoPayload info(long id, Month month, int value) {
        return ResponseRouterInfoPayload.builder()
                .status(0)
                .id(id)
                .month(month)
                .value(value)
                .build();
    }

    public static ResponseRouterInfoPayload notFound(long id) {
        return ResponseRouterInfoPayload.builder()
                .status(100)
                .id(id)
                .msg("Роутер не найден!")
                .build();
    }

    public static ResponseRouterInfoPayload incorrectMonth() {
        return ResponseRouterInfoPayload.builder()
                .status(105)
                .msg("Неверный номер месяца. (возможные варианты 1-12).")
                .build();
    }
}
