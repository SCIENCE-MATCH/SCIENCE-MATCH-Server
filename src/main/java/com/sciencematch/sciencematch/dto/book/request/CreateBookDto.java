package com.sciencematch.sciencematch.dto.book.request;

import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Semester;
import lombok.Data;

@Data
public class CreateBookDto {

    private School school;
    private Semester semester;
    private String title;
    private Integer editionNum;
    private String publisher;

}
