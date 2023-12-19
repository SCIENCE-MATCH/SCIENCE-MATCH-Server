package com.sciencematch.sciencematch.domain.weather;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "response")
public class Response {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty
    Header header;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    Body body;
}
