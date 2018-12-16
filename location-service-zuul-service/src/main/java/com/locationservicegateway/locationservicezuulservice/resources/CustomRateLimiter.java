package com.locationservicegateway.locationservicezuulservice.resources;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
/**
 * This class is handling rate limits using topic bucket algorithm.
 * @author  Aseem Shrestha
 */
@Component
public class CustomRateLimiter {

    private static final int TOKENS = 0;
    private static final int LAST_CHECKED = 1;
    private static long maxToken;
    private static long holdingTimeForTokenRenew;

    private static ConcurrentHashMap<String, AtomicLong[]> tokenBucket = new ConcurrentHashMap<>();


    public static boolean tryConsume(final String key)  {

        // if bucket doesn't have key, populate the bucket
        if (!tokenBucket.containsKey(key)) {
            populateBucket(key, new AtomicLong(getMaxToken()));
        }
        else {
            //get the current bucket for key
            AtomicLong[] currentBucket = tokenBucket.get(key);
            // if numTokens is 0 then all token is used so return false
            long numTokens = currentBucket[TOKENS].get() + calculateNewTokens(currentBucket[LAST_CHECKED]);
            if(numTokens <= 0) {
                return false;
            } else {
                populateBucket(key, new AtomicLong(numTokens));
            }
        }
        return true;
    }

   /*
     if timeFromLastCheck is >= tokenRenewalTime then reset the token
    */
    private static long calculateNewTokens(AtomicLong lastCheckTime) {
        return timeSinceLastCheck(lastCheckTime) >= getHoldingTimeForTokenRenew()? getMaxToken() : 0;
    }

   /*
      this method returns the time difference from current time to last check time
    */
    private static long timeSinceLastCheck(AtomicLong lastCheck) {
        return System.currentTimeMillis() - lastCheck.get();
    }

    /*
      this method populates the bucket
      buckets will have items stored in following manner e.g.
      key          tokenValue   timeStampMillis
      127.0.01          5        121313
     */
    private static void populateBucket(final String key, AtomicLong numTokens) {
        long newTokenValue = numTokens.decrementAndGet();
        tokenBucket.put(key, new AtomicLong[] {new AtomicLong(newTokenValue),
                new AtomicLong(System.currentTimeMillis())});
    }

    public static long getMaxToken() {
        return maxToken;
    }

    public static void setMaxToken(long maxToken) {
        CustomRateLimiter.maxToken = maxToken;
    }

    public static long getHoldingTimeForTokenRenew() {
        return holdingTimeForTokenRenew;
    }

    public static void setHoldingTimeForTokenRenew(long holdingTimeForTokenRenew) {
        CustomRateLimiter.holdingTimeForTokenRenew = holdingTimeForTokenRenew;
    }

}