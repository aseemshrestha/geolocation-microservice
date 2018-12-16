package com.geo.location.locationservice.resources.services;

import com.geo.location.locationservice.model.LocationDetails;
import org.springframework.stereotype.Service;
/**
 * This class provides service methods to work with cache.
 * @author  Aseem Shrestha
 */
@Service
public class LocationService extends CacheManager {

    public void saveLocation(String key, LocationDetails locationDetails) {

        super.put(key, locationDetails);
    }

    public Object getLocationByAddress(String key) {

        return super.get(key);
    }

    public boolean hasLocation(String key) {

        return super.containsKey(key);
    }

    public void deleteByAddress(String key) {

        super.remove(key);
    }

    public void deleteAll() {

        super.clear();
    }

}
