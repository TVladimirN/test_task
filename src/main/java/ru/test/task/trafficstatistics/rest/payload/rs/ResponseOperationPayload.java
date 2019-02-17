package ru.test.task.trafficstatistics.rest.payload.rs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOperationPayload {

    private int status;
    private String msg;

    public static ResponseOperationPayload ok(String msg) {
        return new ResponseOperationPayload(0, msg);
    }

    public static ResponseOperationPayload error(int code, String msg) {
        return new ResponseOperationPayload(code, msg);
    }
}
