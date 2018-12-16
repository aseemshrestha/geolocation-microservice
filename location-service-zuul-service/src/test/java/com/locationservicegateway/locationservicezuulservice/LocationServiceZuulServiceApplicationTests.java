package com.locationservicegateway.locationservicezuulservice;

import com.locationservicegateway.locationservicezuulservice.resources.CustomRateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author  Aseem Shrestha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceZuulServiceApplicationTests {

    @Test
    public void test_rate_limiter_burst() {
        CustomRateLimiter.setMaxToken(2L);
        CustomRateLimiter.setHoldingTimeForTokenRenew(100000L);
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.11"));
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.11"));
        assertFalse(CustomRateLimiter.tryConsume("127.0.0.11"));
        assertFalse(CustomRateLimiter.tryConsume("127.0.0.11"));
    }

    @Test
    public void test_rate_limiter_renew() throws Exception {
        CustomRateLimiter.setMaxToken(2L);
        CustomRateLimiter.setHoldingTimeForTokenRenew(1000L);
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.1"));
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.1"));
        Thread.sleep(2000);
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.1"));
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.1"));
    }

    @Test
    public void test_rate_limiter_fail_renew() throws Exception {
        CustomRateLimiter.setMaxToken(2L);
        CustomRateLimiter.setHoldingTimeForTokenRenew(5000L);
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.1"));
        assertTrue(CustomRateLimiter.tryConsume("127.0.0.1"));
        Thread.sleep(2000);
        assertFalse(CustomRateLimiter.tryConsume("127.0.0.1"));
        assertFalse(CustomRateLimiter.tryConsume("127.0.0.1"));
    }
}

