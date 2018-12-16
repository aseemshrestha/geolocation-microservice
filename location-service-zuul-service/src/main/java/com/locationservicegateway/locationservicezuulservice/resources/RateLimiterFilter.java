package com.locationservicegateway.locationservicezuulservice.resources;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import static org.apache.commons.lang.StringUtils.isBlank;
/**
 * @author  Aseem Shrestha
 */
@Component
public class RateLimiterFilter extends ZuulFilter {

   private static final Long MAX_TOKEN = 5L;
   private static final Long MAX_HOLDING_TIME = 900000L; // expires in 15 mins

   public RateLimiterFilter() {
       CustomRateLimiter.setMaxToken(MAX_TOKEN);
       CustomRateLimiter.setHoldingTimeForTokenRenew(MAX_HOLDING_TIME);
   }
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURI = ctx.getRequest().getRequestURI();
        String remoteAddr = ctx.getRequest().getHeader("X-FORWARDED-FOR");
        if(isBlank(remoteAddr)) {
            remoteAddr = ctx.getRequest().getRemoteAddr();
        }
        String key = requestURI+"?"+ctx.getRequest().getQueryString()+"-"+remoteAddr;
        System.out.println("KEY:" + key);
        boolean isPermitted = CustomRateLimiter.tryConsume(key);
        // if not found, set status to NOT_FOUND
        if(!isPermitted) {
            ctx.setResponseStatusCode(HttpStatus.NOT_FOUND.value());
            String json = "{\"status\" : \"NOT_FOUND\"}";
            ctx.setResponseBody(json);
            ctx.setSendZuulResponse(false);
        }
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
