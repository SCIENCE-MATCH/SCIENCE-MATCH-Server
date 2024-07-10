package com.sciencematch.sciencematch.DTO.chapter;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConceptPostDto {

    private MultipartFile image;
    private MultipartFile blankImage;

    private Long chapterId;

}
