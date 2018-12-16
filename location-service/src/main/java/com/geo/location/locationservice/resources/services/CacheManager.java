package com.geo.location.locationservice.resources.services;

import java.util.concurrent.ConcurrentHashMap;
/**
 * This class stores custom API Response. Incoming address will be used
 * as key and custom is stored as value.
 * KEY is made CASE INSENSITIVE.
 * @apiNote This class can be later replaced by external storage
 * @author  Aseem Shrestha
 */
public class CacheManager extends ConcurrentHashMap<String, Object> {

    @Override
    public Object put(String key, Object value) {
        return super.put(key.toLowerCase(), value);
    }

    @Override
    public Object get(Object key) {
        return super.get(key.toString().toLowerCase());
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key.toString().toLowerCase());
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return super.putIfAbsent(key.toLowerCase(), value);
    }

    @Override
    public Object remove(Object key) { return super.remove(key.toString().toLowerCase());}

    @Override
    public void clear() { super.clear();}
}
