package test.wrapper;


import java.net.InetAddress;


public class LocationData {
    private InetAddress inetAddress;

    private String countryCode;

    private String regionCode;

    private String cityName;

    private String district;

    private String zipCode;

    private String dmaCode;

    private Double latitude = Double.NaN;

    private Double longitude = Double.NaN;

    private LocationData() {
    }

    public LocationData(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public LocationData(InetAddress inetAddress, String countryCode, String regionCode, String cityName,
                        String zipCode, String dmaCode) {
        this(inetAddress, countryCode, regionCode, cityName, zipCode, dmaCode, Double.NaN, Double.NaN);
    }

    public LocationData(InetAddress inetAddress, String countryCode, String regionCode, String cityName,
                        String zipCode, String dmaCode, double latitude, double longitude) {
        this.inetAddress = inetAddress;
        this.countryCode = countryCode;
        this.regionCode = regionCode;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.dmaCode = dmaCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getZipCode() { return zipCode; }

    public String getDmaCode() { return dmaCode; }

    public String getSignature() {
        return inetAddress == null ? "" : inetAddress.toString();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setDmaCode(String dmaCode) {
        this.dmaCode = dmaCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean hasCoordinate() { return latitude != null && longitude != null && !Double.isNaN(latitude) && !Double.isNaN(longitude); }


    @Override
    public String toString() {
        return String.format("%s/%s/%s/%s/%s/%s/%s", inetAddress, countryCode, regionCode, cityName, district, zipCode, dmaCode);
    }
}
