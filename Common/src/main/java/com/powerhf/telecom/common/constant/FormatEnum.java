package com.powerhf.telecom.common.constant;

/**
 * 格式枚举类
 */
public enum  FormatEnum {

    DATE_YMDHMS("yyyyMMddHHmmss");

    private String format;
    private FormatEnum(String f) {
      format = f;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
