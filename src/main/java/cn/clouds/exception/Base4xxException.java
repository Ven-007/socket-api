package cn.clouds.exception;


import cn.clouds.enums.ResponseEnums;
import org.springframework.validation.BindingResult;

/**
 * @author clouds
 * @version 1.0
 */
public class Base4xxException extends BaseException {
    private BindingResult bindingResult = null;

    public Base4xxException(ResponseEnums responseEnums, Object data, BindingResult bindingResult) {
        super(responseEnums, data);
        if (bindingResult != null) {
            this.bindingResult = bindingResult;
        }
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
