package test.wrapper;

import java.net.InetAddress;

public interface GeoServiceWrapper extends AutoCloseable
{

    default void close() throws Exception {

    }
    public LocationData resolve(InetAddress inetAddress);
}
