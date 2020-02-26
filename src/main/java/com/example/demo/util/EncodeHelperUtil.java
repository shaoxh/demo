package com.example.demo.util;

import com.example.demo.exception.EncodingException;

import java.io.UnsupportedEncodingException;

public class EncodeHelperUtil {

    private static final EncodeHelper ENCODE_HELPER = new EncodeHelperImpl();

    public static String decodeInstall (String val) throws UnsupportedEncodingException, EncodingException {
        return ENCODE_HELPER.decodeInstall(val);
    }
}
