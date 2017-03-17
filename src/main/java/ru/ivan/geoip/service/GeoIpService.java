package ru.ivan.geoip.service;


import ru.ivan.geoip.repository.entity.IpPosition;

public interface GeoIpService {

    String convertPositionToJSON(IpPosition ipPosition);

    IpPosition getPosition(String ip);
}
