package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.paper_test.MultiplePaperTestSubmitDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestRequestDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSelectDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSubmitDto;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestRepository;
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
    private final ChapterRepository chapterRepository;

    public List<PaperTestResponseDto> getAllPaperTest(
        PaperTestSelectDto preLessonSelectDto) {
        return paperTestRepository.search(preLessonSelectDto);
    }

    @Transactional
    public void deletePaperTest(List<Long> paperTestId) {
        for (Long id : paperTestId) {
            assignPaperTestRepository.deleteAllByPaperTestId(id);
        }
        assignPaperTestRepository.flush();
        paperTestRepository.deleteAllByIdInBatch(paperTestId);
    }

    @Transactional
    public void createPaperTest(List<PaperTestRequestDto> paperTestRequestDtos) {

        for (PaperTestRequestDto paperTestRequestDto : paperTestRequestDtos) {
            Chapter chapter = chapterRepository.getChapterById(paperTestRequestDto.getChapterId());
            PaperTest paperTest = PaperTest.builder()
                .school(paperTestRequestDto.getSchool())
                .semester(paperTestRequestDto.getSemester())
                .chapterDescription(chapter.getDescription())
                .question(paperTestRequestDto.getQuestion())
                .solution(paperTestRequestDto.getSolution())
                .build();
            paperTestRepository.save(paperTest);
        }
    }

    @Transactional
    public void submitPaperTest(PaperTestSubmitDto paperTestSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            paperTestSubmitDto.getStudentIds());
        PaperTest paperTest = paperTestRepository.getPaperTestById(
            paperTestSubmitDto.getQuestionPaperId());

        //해설 및 카테고리 등 기본 세팅이 되어있는 answer 객체 도입 (추후 답안 문제의 타입이나 정답 검사등에 사용)
        makeAssignPaperTest(students, paperTest);
    }

    @Transactional
    public void submitMultiplePaperTest(
        MultiplePaperTestSubmitDto multiplePaperTestSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            multiplePaperTestSubmitDto.getStudentIds());
        List<PaperTest> paperTests = paperTestRepository.getPaperTestsByList(
            multiplePaperTestSubmitDto.getPaperTestIds());

        for (PaperTest paperTest : paperTests) {
            makeAssignPaperTest(students, paperTest);
        }
    }

    private void makeAssignPaperTest(List<Student> students, PaperTest paperTest) {
        //학생마다 문제 할당
        for (Student student : students) {
            assignPaperTestRepository.save(AssignPaperTest.builder()
                .paperTest(paperTest)
                .student(student)
                .subject(paperTest.getSubject())
                .build());
        }
    }

}
