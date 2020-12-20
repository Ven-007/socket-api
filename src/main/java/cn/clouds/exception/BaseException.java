package cn.clouds.exception;


import cn.clouds.enums.ResponseEnums;

/**
 * @author clouds
 * @version 1.0
 */
public class BaseException extends RuntimeException{

    private ResponseEnums responseEnums ;
    private Object data = null;
    private Exception exception = null;
    public BaseException(ResponseEnums responseEnums){
        super(responseEnums.getMsg());
        this.responseEnums = responseEnums;
    }

    public BaseException(ResponseEnums responseEnums, Object data){
        super(responseEnums.getMsg());
        this.responseEnums = responseEnums;
        this.data = data;
    }

    public BaseException(ResponseEnums responseEnums, Object data, Exception exception){
        super();
        this.responseEnums = responseEnums;
        this.data = data;
        this.exception = exception;
    }

    public ResponseEnums getRe() {
        return responseEnums;
    }

    public Object getData() {
        return data;
    }

    public Exception getException() {
        return exception;
    }
}
