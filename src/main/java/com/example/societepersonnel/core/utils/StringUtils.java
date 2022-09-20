package com.example.societepersonnel.core.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEqual(String str1, String str2) {
        return str1.equals(str2);
    }
}
