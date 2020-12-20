package cn.clouds.exception;


import cn.clouds.enums.ResponseEnums;
import org.springframework.http.HttpStatus;

/**
 * @author clouds
 * @version 1.0
 */
public class Base5xxException extends BaseException {
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public Base5xxException(HttpStatus httpStatus, ResponseEnums responseEnums, Object data, Exception e) {
        super(responseEnums, data, e);
        this.httpStatus = httpStatus;

    }
}
