package cn.clouds.exception;


import cn.clouds.enums.ResponseEnums;
import cn.clouds.models.ResponseResult;
import cn.clouds.utils.Constants;
import cn.clouds.utils.Utils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Set;

/**
 * @author clouds
 * @version 1.0
 */
@ControllerAdvice
public class CommonExceptionHandler {
    private final int BASE_INT = 1000;

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Object missParamException(HttpServletResponse response, MissingServletRequestParameterException exception) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseResult.createExceptionResult(ResponseEnums.PARAM_MISSING_ERROR,
                "{\"" + exception.getParameterName() +
                        "\":\"" + exception.getMessage() + "\"}", exception)
                .string();
    }


    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    public Object missBindingException(HttpServletResponse response, ServletRequestBindingException exception) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseResult.createExceptionResult(ResponseEnums.PARAM_MISSING_ERROR,
                "{\"request binding error\":\"" + exception.getMessage() + "\"}", exception)
                .string();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object methodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException exception) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseResult.createResult(ResponseEnums.PARAM_VALID_ERROR, exception.getBindingResult().getFieldError());
    }


    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public Object handleNotFoundException(HttpServletResponse response, NoHandlerFoundException exception) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseResult.createExceptionResult(ResponseEnums.ERROR_REQUEST_URL,
                "{\"uri\":\"" + exception.getRequestURL() + "\"}", exception)
                .string();
    }


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Object handleBindException(HttpServletResponse response, BindException exception) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseResult.createExceptionResult(ResponseEnums.BAD_REQUEST_ERROR,
                "{\"" + exception.getFieldError().getObjectName() + "\":\"" +
                        exception.getFieldError().getRejectedValue() + "\"}", null)
                .string();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleHttpRequestMethodNotSupportedException(HttpServletResponse response,
                                                               HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String data = "{\"error\":\"此请求不支持 " + httpRequestMethodNotSupportedException.getMethod() + " 方法,支持 "
                + Arrays.toString(httpRequestMethodNotSupportedException.getSupportedMethods()) + " 方法\"}";
        return ResponseResult.createExceptionResult(ResponseEnums.BAD_REQUEST_ERROR, data, null).string();
    }

    @ExceptionHandler(value = Base4xxException.class)
    @ResponseBody
    public Object handleBase4xxException(HttpServletResponse response, Base4xxException exception) {
        int code = HttpStatus.BAD_REQUEST.value();
        try {
            code = exception.getRe().getCode() / BASE_INT;
        } catch (Exception e) {
        }
        response.setStatus(code);
        return ResponseResult.createExceptionResult(exception.getRe(), exception.getData(),
                null, exception.getBindingResult()).string();
    }


    @ExceptionHandler(value = Base5xxException.class)
    @ResponseBody
    public Object handleBase5xxException(HttpServletResponse response, Base5xxException exception) {
        response.setStatus(exception.getHttpStatus().value());
        return ResponseResult.createExceptionResult(exception.getRe(), exception.getData(),
                null).string();
    }


    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Object handleBaseException(HttpServletResponse response, BaseException exception) {
        response.setStatus(exception.getRe().getCode() / BASE_INT);
        return ResponseResult.createExceptionResult(exception.getRe(), exception.getData(), exception.getException())
                .string();
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Object handleAuthenticationException(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equals(Constants.LOGIN)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ResponseResult.createExceptionResult(ResponseEnums.USER_SIGN_IN_FAILED, null, null);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ResponseResult.createExceptionResult(ResponseEnums.USER_UNAUTHORIZED, null, null);
        }

    }


    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Object handleAccessDeniedException(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return ResponseResult.createExceptionResult(ResponseEnums.USER_HAS_NO_PRIVILEGE, null, null);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            ValidationException.class
    })
    @ResponseBody
    public Object handleIllegalArgumentException(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ResponseResult.createExceptionResult(ResponseEnums.PARAM_VALID_ERROR, null, null);
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public static Object handleException(HttpServletResponse response, Exception exception) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        StringBuilder sbReturn = new StringBuilder();
        StringBuilder sbTemp = new StringBuilder();
        sbReturn.append("{\"exception-info\":\"");
        if (exception != null) {
            StackTraceElement[] sts = exception.getStackTrace();
            sbTemp.insert(0, " > \n" + Utils.EncodeTextareaChar(exception.toString()));
            for (StackTraceElement st : sts) {
                sbTemp.insert(0, st.getLineNumber())
                        .insert(0, st.getMethodName() + " : (line) ")
                        .insert(0, " > \n" + st.getClassName() + ".");
            }
        }
        sbReturn.append(sbTemp).append("\"}");
        StackTraceElement[] stes = exception.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        int iBegin = Math.max((stes.length - 10), 0);
        for (int i = iBegin; i < stes.length; i++) {
            stringBuilder.append(stes[i].toString()).append(" >> ");
        }
        return ResponseResult.createExceptionResult(ResponseEnums.ERROR, sbReturn.toString(), exception).string();
    }

    @ExceptionHandler(value = TypeMismatchException.class)
    @ResponseBody
    public Object handleBaseException(HttpServletResponse response, TypeMismatchException exception) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        StringBuilder sbTemp = new StringBuilder();
        sbTemp.append("{\"reason\":\"").append("Failed to convert values of type '").
                append(exception.getRequiredType()).append("' for input value: '")
                .append(exception.getValue()).append("'\"}");
        return ResponseResult.createExceptionResult(ResponseEnums.BAD_REQUEST_ERROR, sbTemp.toString(), exception)
                .string();
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object handleParamException(HttpServletResponse response, ConstraintViolationException exception) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        Set<ConstraintViolation<?>> set = exception.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        if (set != null && set.size() > 0) {
            stringBuilder.append("{");
            int index = 1;
            for (ConstraintViolation con : set) {
                stringBuilder.append("\"");
                String invalidValue = (String) con.getInvalidValue();
                if (invalidValue == null || invalidValue.length() < 1) {
                    invalidValue = "param error" + (index++);
                }
                stringBuilder.append(invalidValue)
                        .append("\":\"")
                        .append(con.getMessage()).append("\",");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            stringBuilder.append("}");
        }
        return ResponseResult.createExceptionResult(ResponseEnums.PARAM_VALID_ERROR,
                stringBuilder.toString(), null).string();
    }

}
