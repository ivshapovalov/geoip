package ru.ivan.geoip.repository;

import ru.ivan.geoip.repository.entity.IpPosition;

public interface GeoIPRepository {

    IpPosition getPosition(String ip);
}
