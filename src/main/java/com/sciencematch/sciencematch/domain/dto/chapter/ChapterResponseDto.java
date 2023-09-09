package com.sciencematch.sciencematch.domain.dto.chapter;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.enumerate.Semester;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
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
