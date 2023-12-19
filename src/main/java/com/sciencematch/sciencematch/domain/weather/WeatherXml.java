package com.sciencematch.sciencematch.domain.weather;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherXml {

    @JacksonXmlProperty private String baseDate;
    @JacksonXmlProperty private String baseTime;
    @JacksonXmlProperty private String category;
    @JacksonXmlProperty private String fcstDate;
    @JacksonXmlProperty private String fcstTime;
    @JacksonXmlProperty private int fcstValue;
    @JacksonXmlProperty private int nx;
    @JacksonXmlProperty private int ny;

}
