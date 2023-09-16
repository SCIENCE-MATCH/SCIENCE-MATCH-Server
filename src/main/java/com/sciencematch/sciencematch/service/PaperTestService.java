package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.DTO.paper_test.MultiplePaperTestSubmitDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSelectDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSubmitDto;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTestQuestion;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestQuestionDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestRequestDto;
import com.sciencematch.sciencematch.infrastructure.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaperTestService {

    private final PaperTestRepository paperTestRepository;
    private final StudentRepository studentRepository;
    private final AssignPaperTestRepository assignPaperTestRepository;

    public List<PaperTestResponseDto> getAllPaperTest(
        PaperTestSelectDto preLessonSelectDto) {
        return paperTestRepository.search(preLessonSelectDto);
    }

    @Transactional
    public void createPaperTest(PaperTestRequestDto paperTestRequestDto) {
        PaperTest paperTest = PaperTest.builder()
            .school(paperTestRequestDto.getSchool())
            .semester(paperTestRequestDto.getSemester())
            .chapterId(paperTestRequestDto.getChapterId())
            .title(paperTestRequestDto.getTitle())
            .build();

        for (PaperTestQuestionDto paperTestQuestionDto : paperTestRequestDto.getPaperTestQuestionDtos()) {
            PaperTestQuestion paperTestQuestion = PaperTestQuestion.builder()
                .question(paperTestQuestionDto.getQuestion())
                .solution(paperTestQuestionDto.getSolution())
                .build();
            paperTest.addQuestions(paperTestQuestion);
        }

        paperTestRepository.save(paperTest);
    }

    @Transactional
    public void submitPaperTest(PaperTestSubmitDto paperTestSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            paperTestSubmitDto.getStudentIds());
        PaperTest paperTest = paperTestRepository.getPaperTestById(
            paperTestSubmitDto.getQuestionPaperId());

        for (Student student : students) {
            assignPaperTestRepository.save(AssignPaperTest.builder()
                .paperTest(paperTest)
                .student(student)
                .subject(paperTest.getSubject())
                .build());
        }
    }

    @Transactional
    public void submitMultiplePaperTest(
        MultiplePaperTestSubmitDto multiplePaperTestSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            multiplePaperTestSubmitDto.getStudentIds());
        List<PaperTest> paperTests = paperTestRepository.getPaperTestsByList(
            multiplePaperTestSubmitDto.getPaperTestIds());

        for (PaperTest paperTest : paperTests) {
            for (Student student : students) {
                assignPaperTestRepository.save(AssignPaperTest.builder()
                    .paperTest(paperTest)
                    .student(student)
                    .subject(paperTest.getSubject())
                    .build());
            }
        }
    }

}
