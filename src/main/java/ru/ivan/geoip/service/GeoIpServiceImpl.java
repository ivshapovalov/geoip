package ru.ivan.geoip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.ivan.geoip.repository.GeoIPRepository;

@Controller
@Qualifier(value = "service")
public class GeoIpServiceImpl implements GeoIpService {

    @Autowired
    private GeoIPRepository repository;

    public GeoIpServiceImpl() {
    }

    @Override
    public String getLocation(String IP) {
        return repository.getLocation(IP);
    }
}
