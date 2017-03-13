package ru.ivan.geoip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GetLocationExample {
    public static void main(String[] args) throws IOException, GeoIp2Exception {

        final String DATABASE_CITY_PATH = System.getenv("GEOIP_DB_FILE");

        File dbCityFile = new File(DATABASE_CITY_PATH);
        DatabaseReader cityReader = new DatabaseReader.Builder(dbCityFile).build();


        InetAddress ipAddress = InetAddress.getByName("25.25.11.42");

        CityResponse cityResponse = cityReader.city(ipAddress);

        Country country = cityResponse.getCountry();
        System.out.println("Country IsoCode: " + country.getIsoCode()); // 'US'
        System.out.println("Country Name: " + country.getName()); // 'United States'

        Subdivision subdivision = cityResponse.getMostSpecificSubdivision();
        System.out.println("Subdivision Name: " + subdivision.getName()); // 'Minnesota'
        System.out.println("Subdivision IsoCode: " + subdivision.getIsoCode()); // 'MN'


        City city = cityResponse.getCity();
        System.out.println("City Name: " + city.getName()); // 'Minneapolis'

        // Postal info
        Postal postal = cityResponse.getPostal();
        System.out.println(postal.getCode()); // '55455'

        // Geo IPLocation info.
        Location location = cityResponse.getLocation();

        // Latitude
        System.out.println("Latitude: " + location.getLatitude()); // 44.9733

        // Longitude
        System.out.println("Longitude: " + location.getLongitude()); // -93.2323

    }

}


