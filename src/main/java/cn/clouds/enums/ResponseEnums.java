package cn.clouds.enums;

/**
 * @author clouds
 * @version 1.0
 */
public enum ResponseEnums {

    SUCCESS(200000, "success"),
    BAD_REQUEST_ERROR(400000, "无效的请求"),
    PARAM_VALID_ERROR(400010, "参数验证错误"),
    ERROR_REQUEST_URL(400011, "url not found"),
    PARAM_MISSING_ERROR(400021, "参数缺失"),
    ERROR(500001, "未知业务异常"),
    USER_UNAUTHORIZED(401002, "无权访问，请登录后再试"),
    RESOURCE_NOT_FOUND(404001, "NOT FOUND"),
    ATTACHMENT_READ_ERROR(500001, "文件读取错误,请重试"),
    APPLICATION_CONTEXT_NULL(500002, "applicationContext为空"),
    USER_SIGN_IN_FAILED(500003, "未登录,无权访问"),
    USER_HAS_NO_PRIVILEGE(500004, "无权限访问");

    private final int code;
    private final String msg;

    ResponseEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public static ResponseEnums getResponseEnumByCode(int code) {
        for (ResponseEnums re : ResponseEnums.values()) {
            if (re.getCode() == code) {
                return re;
            }
        }
        return null;
    }
}
