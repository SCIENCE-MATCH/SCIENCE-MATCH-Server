package com.sciencematch.sciencematch.DTO.book.request;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import lombok.Data;

@Data
public class CreateBookDto {

    private School school;
    private Semester semester;
    private String title;
    private Integer editionNum;
    private String publisher;

}
