package ru.ivan.geoip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ivan.geoip.repository.entity.IpPosition;
import ru.ivan.geoip.service.GeoIpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private static final String PAGE_MAIN = "main";
    private static final String PAGE_REST = "rest";
    private static final String ATTRIBUTE_IP = "ip";
    private static final String ATTRIBUTE_JPOSITIONS = "jpositions";
    private static final String ATTRIBUTE_POINTS = "points";
    private static final String ATTRIBUTE_POSITIONS = "positions";
    private static final String WEB_SEPARTOR = "/";

    @Autowired
    GeoIpService service;

    @RequestMapping(value = WEB_SEPARTOR, method = RequestMethod.GET)
    public String main() {
        return PAGE_MAIN;
    }

    @RequestMapping(value = WEB_SEPARTOR + PAGE_REST, method = RequestMethod
            .GET)
    public void json(@RequestParam(value = ATTRIBUTE_IP, required = true) String ip,
                     HttpServletResponse
                             response) throws IOException {
        if (!"".equals(ip)) {
            IpPosition ipPosition = service.getPosition(ip);
            String position = service.convertPositionToJSON(ipPosition);
            response.setContentType("text/plain");
            response.getWriter().write(position);
        }

    }

    @RequestMapping(value = WEB_SEPARTOR, method =
            RequestMethod.POST)
    public String getLocation(Model model,
                              HttpSession session, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        String input = request.getParameter(ATTRIBUTE_IP);
        if (input == null || "".equals(input)) {
            return PAGE_MAIN;
        }
        input = input.trim();
        String[] ipsArray = input.split(",");
        Map<String, IpPosition> map = new TreeMap<>();
        for (String ip : ipsArray) {
            if (!"".equals(ip)) {
                IpPosition ipPosition;
                ipPosition = service.getPosition(ip);
                map.put(ip, ipPosition);
            }
        }

        List<IpPosition> positions = new ArrayList<>(
                map.entrySet().stream().map(entry ->
                        entry.getValue() == null ? new IpPosition(entry.getKey()) : entry.getValue()
                ).collect(Collectors.toList()));
        List<String> points = map.values().stream()
                .filter(pos -> pos != null)
                .map(position -> "[" + String.valueOf(position.getLatitude()) + "," +
                        String.valueOf(position.getLongitude() + "]"))
                .collect(Collectors.toList());
        List<String>jPositions = map.values().stream()
                .filter(pos -> pos != null)
                .map(ipPosition -> service.convertPositionToJSON(ipPosition))
                .collect(Collectors.toList());

        model.addAttribute(ATTRIBUTE_IP, input);
        model.addAttribute(ATTRIBUTE_JPOSITIONS, jPositions.toString());
        model.addAttribute(ATTRIBUTE_POINTS, points.toString());
        model.addAttribute(ATTRIBUTE_POSITIONS, positions);
        return PAGE_MAIN;
    }


}


