package ru.ivan.geoip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.ivan.geoip.repository.entity.IPLocation;
import ru.ivan.geoip.service.GeoIpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MainController {

    private static final String PAGE_MAIN = "main";
    private static final String PAGE_REST = "rest";
    private static final String ATTRIBUTE_IP = "ip";
    private static final String ATTRIBUTE_LOCATION = "location";
    private static final String WEB_SEPARTOR = "/";

    @Autowired
    GeoIpService service;

    @RequestMapping(value = {WEB_SEPARTOR}, method = RequestMethod.GET)
    public String main() {
        return PAGE_MAIN;
    }

    @RequestMapping(value = {WEB_SEPARTOR + "{" + ATTRIBUTE_IP + "}"},
            method = RequestMethod.GET)
    public void json(Model model,
                     @PathVariable(ATTRIBUTE_IP) String IP,
                     HttpSession session, HttpServletResponse response) throws IOException {
        String location = "";
        if (!"".equals(IP)) {
            location = service.getLocationAsJSON(IP);
        }
            response.setContentType("text/plain");
            response.getWriter().write(location);
        
    }

    @RequestMapping(value = WEB_SEPARTOR, method =
            RequestMethod.POST)
    public String getLocation(Model model,
                              HttpSession session, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        String ip = request.getParameter(ATTRIBUTE_IP);
        if (ip == null || "".equals(ip)) {
            return PAGE_MAIN;
        }
        ip = ip.trim();

        IPLocation location = null;
        if (!"".equals(ip)) {
            location = service.getLocation(ip);
        }
        model.addAttribute(ATTRIBUTE_IP, ip);
        model.addAttribute(ATTRIBUTE_LOCATION, location);
        return PAGE_MAIN;
    }


}


