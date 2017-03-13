package ru.ivan.geoip.repository;

public interface GeoIPRepository {
    String getLocation(String ip);
}
