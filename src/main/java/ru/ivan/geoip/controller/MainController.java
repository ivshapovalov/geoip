package ru.ivan.geoip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ivan.geoip.service.GeoIpService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/")
public class MainController {

    private static final String ATTRIBUTE_IP = "IP";

    @Autowired
    GeoIpService service;

    private static final String WEB_SEPARTOR = "/";

    @RequestMapping(value = {WEB_SEPARTOR+"",WEB_SEPARTOR + "{" + ATTRIBUTE_IP + "}"}, method =
            RequestMethod.GET)
    public void main(Model model,
                     @PathVariable(ATTRIBUTE_IP) String IP,
                     HttpSession session, HttpServletResponse response) throws IOException {
        String location = "";
        if (!"".equals(IP)) {
            location = service.getLocation(IP);
        }
        response.setContentType("text/plain");
        response.getWriter().write(location);
    }

}


