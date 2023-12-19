package com.sciencematch.sciencematch.domain.weather;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Body {

    @JacksonXmlProperty private String dataType;
    @JacksonXmlProperty
    private List<WeatherXml> items = new ArrayList<>();
    @JacksonXmlProperty private int numOfRows;
    @JacksonXmlProperty private int pageNo;
    @JacksonXmlProperty private int totalCount;
}
