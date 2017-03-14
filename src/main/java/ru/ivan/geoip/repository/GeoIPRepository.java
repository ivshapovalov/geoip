package ru.ivan.geoip.repository;

import ru.ivan.geoip.repository.entity.IPLocation;

public interface GeoIPRepository {
    String getLocationAsJSON(String ip);

    IPLocation getLocation(String ip);
}
