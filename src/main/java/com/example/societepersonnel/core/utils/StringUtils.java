package com.example.societepersonnel.core.utils;

public class StringUtils {

    private StringUtils() {
    }

    /* public static boolean isNotNullOrNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }*/

    public static boolean isNotNullOrNotEmpty(Long str) {
        return str != null;
    }
}
