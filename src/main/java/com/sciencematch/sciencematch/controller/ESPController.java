package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.domain.weather.Body;
import com.sciencematch.sciencematch.domain.weather.Header;
import com.sciencematch.sciencematch.domain.weather.Response;
import com.sciencematch.sciencematch.domain.weather.WeatherXml;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/weatherTest")
public class ESPController {

    public static int weatherCode = 0;

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public Response getWeatherCode() {
        Header header = new Header("00", "NORMAL_SERVICE");
        List<WeatherXml> list = new ArrayList<>();
        list.add(new WeatherXml("20231220", "1430", "LGT", "20231220", "1500", 0, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "LGT", "20231220", "1600", 0, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "LGT", "20231220", "1700", 0, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "LGT", "20231220", "1800", 0, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "LGT", "20231220", "1900", 0, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "LGT", "20231220", "2000", 0, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "PTY", "20231220", "1500", 3, 62, 127));
        list.add(new WeatherXml("20231220", "1430", "PTY", "20231220", "1500", weatherCode, 62, 127));
        Body body = new Body("XML", list, 8, 1, 60);
        return new Response(header, body);
    }

    @PostMapping
    public void changeWeatherCode(@RequestBody int weatherCode) {
        changeWeather(weatherCode);
    }

    private void changeWeather(int code) {
        weatherCode = code;
    }
}
