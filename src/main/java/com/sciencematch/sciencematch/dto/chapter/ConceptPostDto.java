package com.sciencematch.sciencematch.dto.chapter;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ConceptPostDto {

    private MultipartFile image;
    private MultipartFile blankImage;

    private Long chapterId;

}
