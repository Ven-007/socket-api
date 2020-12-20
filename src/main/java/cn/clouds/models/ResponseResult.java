package cn.clouds.models;


import cn.clouds.enums.ResponseEnums;
import cn.clouds.exception.Base4xxException;
import cn.clouds.utils.Constants;
import cn.clouds.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

/**
 * @param <T>
 * @author clouds
 * @version 1.0
 */
public class ResponseResult<T> {

    private int code = ResponseEnums.ERROR.getCode();

    private String msg = ResponseEnums.ERROR.getMsg();

    private T data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult() {
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseResult<Object> createExceptionResult(ResponseEnums responseEnums, Object data, Exception exception) {
        ResponseResult<Object> result = new ResponseResult<Object>();

        result.setCode(responseEnums.getCode());
        result.setMsg(responseEnums.getMsg());
        try {
            result.setData(createExpJsonString(data, exception));
        } catch (JsonProcessingException e) {
            throw new Base4xxException(ResponseEnums.ERROR, null, null);
        }
        return result;
    }


    public static ResponseResult<Object> createExceptionResult(ResponseEnums responseEnums, Object data,
                                                               Exception exception, BindingResult bindingResult) {
        if (responseEnums == null) responseEnums = ResponseEnums.PARAM_VALID_ERROR;
        StringBuilder sbTemp = new StringBuilder();
        if (bindingResult != null && bindingResult.hasErrors()) {
            sbTemp.append("{");
            for (FieldError error : bindingResult.getFieldErrors()) {
                sbTemp.append("\"").append(error.getField()).append("\":\"")
                        .append(error.getDefaultMessage()).append("\",");
            }
            sbTemp.deleteCharAt(sbTemp.length() - 1).append("}");
        } else {
            try {
                sbTemp.append(createExpJsonString(data, exception));
            } catch (JsonProcessingException e) {
                throw new Base4xxException(ResponseEnums.ERROR, null, null);
            }
        }
        return createExceptionResult(responseEnums, sbTemp.toString(), null);
    }


    @Override
    public String toString() {
        return string();
    }

    public String string() {
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = "{\"code\":" + code + ",\"msg\":\"" + msg + "\",\"data\":" +
                    (data == null ? null : (data instanceof String ? data + "" : mapper.writeValueAsString(data))) + "}";
        } catch (JsonProcessingException e) {
            throw new Base4xxException(ResponseEnums.ERROR, null, null);
        }
        return StringEscapeUtils.unescapeJson(str);
    }

    public static <T> ResponseResult<T> createResult(ResponseEnums responseEnums, T data) {
        ResponseResult result = new ResponseResult();
        responseEnums = (responseEnums == null ? responseEnums.SUCCESS : responseEnums);
        result.setCode(responseEnums.getCode());
        result.setMsg(responseEnums.getMsg());
        result.setData(data);
        return result;
    }

    public static String createExpJsonString(Object data, Exception exception) throws JsonProcessingException {
        if (data == null) {
            if (exception != null) {
                StringBuilder sbTemp = new StringBuilder();
                sbTemp.append("{\"").append(Constants.CLASS_METHOD).append("\":\"")
                        .append(exception.getStackTrace()[Constants.STACKTRACE_CLASS_INDEX].getClassName()
                                + "." + exception.getStackTrace()[Constants.STACKTRACE_CLASS_INDEX].getMethodName()).append("\",")
                        .append("\"").append(Constants.EXCEPTION).append("\":\"")
                        .append(exception.getMessage()).append("\"}");
                return sbTemp.toString();
            }
        } else {
            ObjectMapper mapper = new ObjectMapper();
            if (data instanceof String) {
                if (Utils.isJsonString(((String) data).toString())) {
                    return data.toString();
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("error", data.toString());
                    return mapper.writeValueAsString(hashMap);
                }
            } else {
                return mapper.writeValueAsString(data);
            }
        }
        return null;
    }
}
