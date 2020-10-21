package com.demo.demo4ddd.domain.shared;

public enum DomainExceptionCode {
    ID_SHOULD_BE_POSITIVE("ID必须大于0"),
    DATA_NOT_FOUND("找不到%s数据 ID:%s"),
    INVALID_DATA("数据异常 %s"),
    PRICE_SHOULD_BE_POSITIVE("价格不能小于0"),
    CAST_OBJECT_TO_JSON_FAIL("Cast object to json fail"),
    NOT_SUPPORT_OPERATION("不支持该操作 %s"),

    ;

    private String message;

    DomainExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage(Object... param) {
        return String.format(message, param);
    }
}
