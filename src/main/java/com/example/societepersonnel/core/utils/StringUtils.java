package com.example.societepersonnel.core.utils;

public class StringUtils {

    private StringUtils() {
        super();
    }

    public static Boolean isNullOrEmpty(String str) {
        return str.isEmpty() || str == null;
    }

    public static Boolean isEqual(String str1, String str2) {
        return str1.equals(str2);
    }
}
