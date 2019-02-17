package ru.test.task.trafficstatistics.rest.payload.rq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.test.task.trafficstatistics.common.Month;

import java.util.List;

@Getter
@Setter
public class ProfilePayload {

    @JsonProperty("id")
    private long id;
    @JsonProperty("fractions")
    private List<Fraction> fractions;


    public static class Fraction {

        @Getter
        @Setter
        @JsonProperty("month")
        private Month month;
        @Getter
        @JsonProperty("fraction")
        private double fraction;

        public int getIntFraction() {
            return (int) (this.fraction * 100);
        }

        public double getFraction() {
            return this.fraction;
        }
    }
}
