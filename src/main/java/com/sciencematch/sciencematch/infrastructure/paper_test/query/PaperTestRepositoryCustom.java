package com.sciencematch.sciencematch.infrastructure.paper_test.query;

import com.sciencematch.sciencematch.DTO.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSelectDto;
import java.util.List;

public interface PaperTestRepositoryCustom {

    List<PaperTestResponseDto> search(PaperTestSelectDto preLessonSelectDto);

}
