package com.hilamg.change.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AccessLogFilter extends AbstractPostZuulFilter{
    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        Long startTime = (Long) context.get("startTime");
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;
        // 从网关通过的请求都会打印日志记录 uri + duration
        log.info("uri : {}, duration : {} ",uri,duration);
        return success();
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        // 返回相应优先级-1
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER-1;
    }
}
