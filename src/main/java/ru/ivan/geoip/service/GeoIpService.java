package ru.ivan.geoip.service;


import ru.ivan.geoip.repository.entity.IPLocation;

public interface GeoIpService {

    String getLocationAsJSON(String IP);

    IPLocation getLocation(String IP);
}
