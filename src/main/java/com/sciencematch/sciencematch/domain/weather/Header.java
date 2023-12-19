package com.sciencematch.sciencematch.domain.weather;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Header {

    @JacksonXmlProperty private String resultCode;
    @JacksonXmlProperty private String resultMsg;
}
