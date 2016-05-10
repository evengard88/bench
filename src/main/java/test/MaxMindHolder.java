package test;

import com.maxmind.geoip.LookupService;
import test.wrapper.GeoServiceWrapper;
import test.wrapper.LocationData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MaxMindHolder {
    private static MaxMindHolder ourInstance = new MaxMindHolder();

    public static MaxMindHolder getInstance() {
        return ourInstance;
    }

    GeoServiceWrapper service;

    private MaxMindHolder() {
        final LookupService lookupService;
        try {
            String file = "GeoIPCity.dat";
            lookupService = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE);
            service = new GeoServiceWrapper() {
                @Override
                public LocationData resolve(InetAddress inetAddress) {
                    if (inetAddress == null) {
                        return new LocationData(inetAddress);
                    } else {
                        com.maxmind.geoip.Location location = lookupService.getLocation(inetAddress);
                        if (location == null) {
                            return new LocationData(inetAddress);
                        } else {
                            return new LocationData(
                                    inetAddress, location.countryCode,
                                    location.region,
                                    location.city,
                                    location.postalCode,
                                    location.dma_code > 0 ? Integer.toString(location.dma_code) : null,
                                    location.latitude, location.longitude
                            );
                        }
                    }
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Can't read ip db from " + ": " + e.getMessage(), e);
        }

    }

    public GeoServiceWrapper getService() {
        return service;
    }
}
