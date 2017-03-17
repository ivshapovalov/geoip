package ru.ivan.geoip.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.ivan.geoip.repository.entity.IpPosition;

import java.io.File;
import java.lang.reflect.Field;
import java.net.InetAddress;

import static ru.ivan.geoip.util.Constants.GEOIP_DB_FILE;

@Controller
@Qualifier(value = "repository")
public class GeoIpRepositoryImpl implements GeoIPRepository {

    public GeoIpRepositoryImpl() {
    }

    @Override
    public IpPosition getPosition(String ip) {
        try {
            File dbCityFile = new File(GEOIP_DB_FILE);
            DatabaseReader cityReader = new DatabaseReader.Builder(dbCityFile).build();
            InetAddress ipAddress = InetAddress.getByName(ip);

            CityResponse cityResponse = cityReader.city(ipAddress);

            IpPosition ipPosition = new IpPosition(ip);

            Country country = cityResponse.getCountry();
            ipPosition.setCountryCode(country.getIsoCode());
            ipPosition.setCountryName(country.getName());

            Subdivision subdivision = cityResponse.getMostSpecificSubdivision();
            ipPosition.setRegionName(subdivision.getName());
            ipPosition.setRegion(subdivision.getIsoCode());

            City city = cityResponse.getCity();
            ipPosition.setCity(city.getName());

            Postal postal = cityResponse.getPostal();
            ipPosition.setPostalCode(postal.getCode());

            Location location = cityResponse.getLocation();
            ipPosition.setLatitude(String.valueOf(location.getLatitude()));
            ipPosition.setLongitude(String.valueOf(location.getLongitude()));

            return ipPosition;
        } catch (Exception e) {
            return null;
        }
    }


}
