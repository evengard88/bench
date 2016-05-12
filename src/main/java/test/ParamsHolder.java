package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParamsHolder {
    final static int IP_COUNT = 10_000;
    private static ParamsHolder ourInstance = new ParamsHolder();

    public static ParamsHolder getInstance() {
        return ourInstance;
    }

    private ParamsHolder() {
        try {
            init();
        } catch (Exception e) {

        }
    }

    InetAddress[] inetAddresses;

    public void init()  {

        Random random = new Random();
        byte[] address = new byte[4];

        List<InetAddress> inetAddressList = new ArrayList<>(IP_COUNT);
        for (int i = 0; i < IP_COUNT; i++) {
            random.nextBytes(address);
            InetAddress ip = null;
            try {
                ip = InetAddress.getByAddress(address);
            } catch (UnknownHostException e) {
                System.out.println("Unknown IP Address");
            }
            inetAddressList.add(ip);
        }
        inetAddresses = inetAddressList.toArray(new InetAddress[IP_COUNT]);
    }

    public static ParamsHolder getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(ParamsHolder ourInstance) {
        ParamsHolder.ourInstance = ourInstance;
    }

    public InetAddress[] getInetAddresses() {
        return inetAddresses;
    }

}
