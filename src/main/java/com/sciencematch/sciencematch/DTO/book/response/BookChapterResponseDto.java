package com.sciencematch.sciencematch.DTO.book.response;

import com.sciencematch.sciencematch.domain.Chapter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookChapterResponseDto {

    private Long chapterId;
    private String description;
    private List<Integer> pages;

    public static BookChapterResponseDto of(Chapter chapter, List<Integer> pages) {
        return new BookChapterResponseDto(chapter.getId(), chapter.getDescription(), pages);
    }

}
