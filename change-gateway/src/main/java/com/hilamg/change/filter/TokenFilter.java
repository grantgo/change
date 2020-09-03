package com.hilamg.change.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * 校验请求中的token
 */

@Slf4j
@Component
public class TokenFilter extends AbstractPreZuulFilter{


    @Override
    protected Object cRun() {
        HttpServletRequest request = context.getRequest();
        log.info(String.format("%s request to %s"),request.getMethod(),request.getRequestURL().toString());
        Object token = request.getParameter("token");
        // TODO token 可做更多逻辑
        if (null == token){
           return fail(401,"token is null");
        }
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
        return 1;
    }
}
