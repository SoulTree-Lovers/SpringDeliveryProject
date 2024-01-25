package org.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeInterface;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> OK(T data) {
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;

        return api;
    }

    public static Api<Object> ERROR(Result result) {
        var api = new Api<Object>();
        api.result = result;

        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface);

        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, Throwable throwable) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface, throwable);

        return api;
    }

    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, String message) {
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface, message);

        return api;
    }
}
