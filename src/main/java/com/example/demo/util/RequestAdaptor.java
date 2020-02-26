package com.example.demo.util;

//import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.example.demo.exception.EncodingException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Yugandhar Gangu
 */
public interface RequestAdaptor {

    Map<Long, HttpServletRequest> REQUEST_MAP = Collections.synchronizedMap(new WeakHashMap<Long, HttpServletRequest>());

    /**
     * @return string
     * @throws EncodingException e
     */
    String generateToken() throws EncodingException;

}
