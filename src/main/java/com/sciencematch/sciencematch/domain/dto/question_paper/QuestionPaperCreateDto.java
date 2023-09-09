package com.sciencematch.sciencematch.domain.dto.question_paper;

import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.QuestionTag;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Semester;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import java.util.List;
import lombok.Data;

@Data
public class QuestionPaperCreateDto {

    private String title;
    private String makerName;
    private List<Long> questionIds;
    private Integer questionNum;

    private School school;
    private Semester semester;

    private Category category; //객관, 주관, 서술
    private QuestionTag questionTag; //단원별, 시중교재, 모의고사
    private Subject subject; //과목

}
