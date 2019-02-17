package ru.test.task.trafficstatistics.rest.payload.rq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.test.task.trafficstatistics.common.Month;

import java.util.List;

@Getter
@Setter
public class RouterPayload {

    @JsonProperty
    private long id;
    @JsonProperty
    private long profileId;
    @JsonProperty
    private List<Indication> indications;

    @Getter
    @Setter
    public static class Indication {

        @JsonProperty
        private Month month;
        @JsonProperty
        private int value;

    }
}
