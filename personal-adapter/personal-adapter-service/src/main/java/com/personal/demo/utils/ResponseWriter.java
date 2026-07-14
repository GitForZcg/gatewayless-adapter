package com.personal.demo.utils;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.pojo.wrapper.AdapterDemoResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.personal.demo.consts.HttpConstant.HEADER_SIGN;
import static com.personal.demo.pojo.wrapper.AdapterRespCode.UNKNOWN_SYSTEM_ERROR;

@Component
@Slf4j
public class ResponseWriter {

    //    private final Gson gson = GsonFactory.getInstance();
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    public boolean writeForbiddenResponse(HttpServletResponse response, String message) {
        return writeErrorResponse(response, HttpStatus.FORBIDDEN.value() + "", message, HttpStatus.OK.value());
    }

    public boolean writeAccessDeniedResponse(HttpServletResponse response) {
        return writeErrorResponse(response, "WHITELIST_BLOCKED", "Access denied", HttpStatus.FORBIDDEN.value());
    }

    public boolean writeInternalErrorResponse(HttpServletResponse response) {
        return writeErrorResponse(response, UNKNOWN_SYSTEM_ERROR.code, UNKNOWN_SYSTEM_ERROR.message, HttpStatus.OK.value());
    }

    public boolean writeBusinessErrorResponse(HttpServletResponse response, String code, String message) {
        return writeErrorResponse(response, code, message, HttpStatus.OK.value());
    }

    public boolean writeNotImplementedResponse(HttpServletResponse response, String message) {
        return writeErrorResponse(response, HttpStatus.NOT_IMPLEMENTED.value() + "",
                message, HttpStatus.OK.value());
    }

    public boolean writeNotFoundResponse(HttpServletResponse response, String message) {
        return writeErrorResponse(response, HttpStatus.NOT_FOUND.value() + "",
                message, HttpStatus.OK.value());
    }

    public boolean writeSuccessResponse(HttpServletResponse response, Object data, String sign) {
        try {
            if (response.isCommitted()) {
                log.warn("Response already committed, cannot write success response");
                return false;
            }
            if (StringUtils.isNotBlank(sign)) {
                response.setHeader(HEADER_SIGN, sign);
            }

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(gson.toJson(data));
            return false; // 不继续后续处理，已经直接响应

        } catch (Exception e) {
            log.error("Error writing success response", e);
            return writeInternalErrorResponse(response);
        }
    }

    private boolean writeErrorResponse(HttpServletResponse response, String code, String message, int status) {
        try {
            if (response.isCommitted()) {
                log.warn("Response already committed, cannot write error response: {}", message);
                return false;
            }
            response.reset(); // 清除之前的响应内容
            response.setStatus(status);
            response.setContentType("application/json;charset=UTF-8");
            AdapterDemoResponse<Object> errorResponse = AdapterDemoResponse.fail(String.format("%s_%s", code, message));
            response.getWriter().write(gson.toJson(errorResponse));
            return Boolean.FALSE;

        } catch (Exception e) {
            log.error("Error writing error response", e);
            return false;
        }
    }
}