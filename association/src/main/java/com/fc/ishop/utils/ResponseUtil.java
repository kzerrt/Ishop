package com.fc.ishop.utils;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应工具类
 * @author florence
 * @date 2023/11/22
 */
@Slf4j
public class ResponseUtil {
    static final String ENCODING = "UTF-8";
    static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static void output(HttpServletResponse response, Integer status, Map<String, Object> resultMap) {
        response.setStatus(status);
        output(response, resultMap);
    }

    /**
     * 输出json
     */
    public static void output(HttpServletResponse response, Map<String, Object> resultMap) {
        response.setCharacterEncoding(ENCODING);
        response.setContentType(CONTENT_TYPE);
        try (
                ServletOutputStream outputStream = response.getOutputStream()
        ) {
            outputStream.write(new Gson().toJson(resultMap).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            log.error("response output error:", e);
        }

    }
    public static Map<String, Object> resultMap(boolean flag, Integer code, String msg) {
        return resultMap(flag, code, msg, null);
    }
    public static Map<String, Object> resultMap(boolean flag, Integer code, String msg, Object data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", flag);
        resultMap.put("message", msg);
        resultMap.put("code", code);
        resultMap.put("timestamp", System.currentTimeMillis());
        if (data != null) {
            resultMap.put("result", data);
        }
        return resultMap;
    }
}
