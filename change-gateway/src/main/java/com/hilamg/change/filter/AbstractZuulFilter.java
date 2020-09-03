package com.hilamg.change.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 通用抽象过滤器类
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

    // 过滤器之间传递消息，数据保存在ThreadLocal中
    // 扩展map
    RequestContext context;

    private final static String NEXT = "next";


    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        // 获取当前线程的 RequestContext
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.getOrDefault(NEXT,true);
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     * @throws ZuulException if an error occurs during execution.
     */
    @Override
    public Object run() throws ZuulException {
        context = RequestContext.getCurrentContext();
        return cRun();
    }

    protected abstract Object cRun();

    /**
     * 失败方法
     * @param code
     * @param msg
     * @return
     */
    Object fail (int code,String msg){
        context.set(NEXT,false);
        context.setSendZuulResponse(false);
        context.getResponse().setContentType("text/html;charset=UTF-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(String.format("{\"result\":\"%s!\"}",msg));
        return null;
    }

    /**
     * 成功
     * @return
     */
    Object success(){
        context.set(NEXT,true);
        return null;
    }
}
