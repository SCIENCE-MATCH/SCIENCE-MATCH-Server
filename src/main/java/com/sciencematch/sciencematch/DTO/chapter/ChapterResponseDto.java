package com.sciencematch.sciencematch.DTO.chapter;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChapterResponseDto {

    private Long id;
    private School school;
    private Semester semester;
    private Subject subject;

    private String description;

    private Integer listOrder;
    private List<ChapterResponseDto> children;

    public static ChapterResponseDto of(Chapter chapter) {
        return new ChapterResponseDto(chapter.getId(), chapter.getSchool(), chapter.getSemester(),
            chapter.getSubject(), chapter.getDescription(), chapter.getListOrder(),
            chapter.getChildren().stream().map(ChapterResponseDto::of).collect(Collectors.toList()));
    }

}
