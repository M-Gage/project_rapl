package com.qingting.middleware.enums;


public enum Constant {
    FILED_SPLIT("\\|"),
    FILED_UTF_8("UTF-8"),
    FILED_COMMA(","),
    FILED_BLANK(" "),
    FILED_FACTOR_SEPARATOR("&"),
    FILED_NO_STR("");



    private  String constant;

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    Constant(String constant) {
        this.constant = constant;
    }
}
