package ru.ivan.geoip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.ivan.geoip.repository.GeoIPRepository;
import ru.ivan.geoip.repository.entity.IpPosition;

@Controller
@Qualifier(value = "service")
public class GeoIpServiceImpl implements GeoIpService {

    @Autowired
    private GeoIPRepository repository;

    public GeoIpServiceImpl() {
    }

    @Override
    public String convertPositionToJSON(IpPosition ipPosition) {
        String jLocation = "";
        try {
            jLocation = new ObjectMapper().writeValueAsString(
                    ipPosition);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jLocation;

    }

    @Override
    public IpPosition getPosition(String ip) {
        return repository.getPosition(ip);
    }
}
