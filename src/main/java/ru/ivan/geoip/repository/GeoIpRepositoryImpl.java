package ru.ivan.geoip.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.ivan.geoip.repository.entity.IPLocation;

import java.io.File;
import java.net.InetAddress;

@Controller
@Qualifier(value = "repository")
public class GeoIpRepositoryImpl implements GeoIPRepository {

    private final String DATABASE_CITY_PATH = System.getenv("GEOIP_DB_FILE");

    public GeoIpRepositoryImpl() {
    }

    @Override
    public IPLocation getLocation(String ip) {
        try {
            File dbCityFile = new File(DATABASE_CITY_PATH);
            DatabaseReader cityReader = new DatabaseReader.Builder(dbCityFile).build();
            InetAddress ipAddress = InetAddress.getByName(ip);

            CityResponse cityResponse = cityReader.city(ipAddress);

            IPLocation ipLocation = new IPLocation();

            Country country = cityResponse.getCountry();
            ipLocation.setCountryCode(country.getIsoCode());
            ipLocation.setCountryName(country.getName());

            Subdivision subdivision = cityResponse.getMostSpecificSubdivision();
            ipLocation.setRegionName(subdivision.getName());
            ipLocation.setRegion(subdivision.getIsoCode());

            City city = cityResponse.getCity();
            ipLocation.setCity(city.getName());

            Postal postal = cityResponse.getPostal();
            ipLocation.setPostalCode(postal.getCode());

            Location location = cityResponse.getLocation();
            ipLocation.setLatitude(String.valueOf(location.getLatitude()));
            ipLocation.setLongitude(String.valueOf(location.getLongitude()));


            return ipLocation;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public String getLocationAsJSON(String ip) {
        IPLocation ipLocation = getLocation(ip);
        String jLocation = "";
        try {
            jLocation = new ObjectMapper().writeValueAsString(
                    ipLocation);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jLocation;
    }
}
