package com.personal.demo.utils;


import com.personal.demo.pojo.base.RequestContext;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class RequestContextUtil {
    private RequestContext context;

    public void setContext(RequestContext context) {
        this.context = context;
    }

}