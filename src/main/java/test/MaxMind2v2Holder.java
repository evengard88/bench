package test;

import com.maxmind.db.CHMCache;
import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import test.wrapper.GeoServiceWrapper;
import test.wrapper.LocationData;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

public class MaxMind2v2Holder {
    private static MaxMind2v2Holder ourInstance = new MaxMind2v2Holder();

    public static MaxMind2v2Holder getInstance() {
        return ourInstance;
    }

    GeoServiceWrapper service;

    private MaxMind2v2Holder() {
        final DatabaseReader mindMaxDB;
        try {
            String file = "GeoIP2-City.mmdb";
            mindMaxDB = new DatabaseReader.Builder(new File(file))
                    .withCache(new CHMCache())
                    .fileMode(Reader.FileMode.MEMORY_MAPPED) // FileMode.MEMORY can be used only with InputStream
                    .build();
            service = new GeoServiceWrapper() {
                @Override
                public LocationData resolve(InetAddress inetAddress) {
                    if (inetAddress == null) {
                        return new LocationData(inetAddress);
                    } else {
                        CityResponse cityResponse = null;
                        try {
                            cityResponse = mindMaxDB.city(inetAddress);
                        } catch (Exception e) {
                            e.fillInStackTrace();
                            //swallow and return new LocationData(inetAddress);
                        }
                        if (cityResponse == null) {
                            return new LocationData(inetAddress);
                        } else {
                            Location location = cityResponse.getLocation();
                            String metroCode = Optional.ofNullable(location)
                                    .map(Location::getMetroCode)
                                    .filter(a -> a > 0)
                                    .map(a -> Integer.toString(a))
                                    .orElse(null);
                            Double latitude = Optional.ofNullable(location)
                                    .map(Location::getLatitude).orElse(0D);
                            Double longitude = Optional.ofNullable(location)
                                    .map(Location::getLongitude).orElse(0D);
                            return new LocationData(
                                    inetAddress, cityResponse.getCountry().getIsoCode(),
                                    cityResponse + "",
                                    cityResponse.getCity().getName(),
                                    cityResponse.getPostal().getCode(),
                                    metroCode,
                                    latitude, longitude);
                        }

                    }
                }

                @Override
                public void close() {
                    try {
                        mindMaxDB.close();
                    } catch (IOException e) {
                        System.out.println("closing max mind2 error!");
                    }
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Can't read ip db from file " + ": " + e.getMessage(), e);
        }

    }

    public GeoServiceWrapper getService() {
        return service;
    }
}
