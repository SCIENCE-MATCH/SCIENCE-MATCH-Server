package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.auth.response.MyPageDto;
import com.sciencematch.sciencematch.DTO.teacher.request.GradingRequestDto;
import com.sciencematch.sciencematch.DTO.teacher.request.SummaryRequestDto;
import com.sciencematch.sciencematch.DTO.teacher.response.MyStudentsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.SimpleStudentsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.SummaryAQResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.SummaryPTResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.SummaryResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.TeacherAssignPaperTestsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.TeacherAssignQuestionsResponseDto;
import com.sciencematch.sciencematch.DTO.team.TeamResponseDto;
import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTestAnswer;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestAnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AssignQuestionRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AssignQuestionRepository assignQuestionRepository;
    private final AssignPaperTestRepository assignPaperTestRepository;
    private final AnswerRepository answerRepository;
    private final PaperTestAnswerRepository paperTestAnswerRepository;
    private final S3Service s3Service;

    //로고 변경
    @Transactional
    public void changeLogo(MultipartFile logo, String email) throws IOException {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        if (teacher.getLogo() != null) {
            s3Service.deleteFile(teacher.getLogo());
        }
        String logoURL = s3Service.uploadFile(logo, "logo");
        teacher.changeLogo(logoURL);
    }

    //로고 변경
    @Transactional
    public void deleteLogo(String email) throws IOException {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        if (teacher.getLogo() != null) {
            s3Service.deleteFile(teacher.getLogo());
            teacher.changeLogo(null);
        }
    }

    //학생 관리 조회
    public List<MyStudentsResponseDto> getMyStudents(String email) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        return studentRepository.findAllByTeacher(teacher).stream().map(MyStudentsResponseDto::of)
            .collect(Collectors.toList());
    }

    //마이페이지 조회
    public MyPageDto getMypage(String email) {
        return MyPageDto.of(teacherRepository.getTeacherByEmail(email));
    }

    public SummaryResponseDto getStudentSummary(SummaryRequestDto summaryRequestDto) {
        List<AssignQuestions> assignQuestions = assignQuestionRepository.findAllForSummary(
            summaryRequestDto.getStudentId(), summaryRequestDto.getStartDate(),
            summaryRequestDto.getEndDate());
        List<AssignPaperTest> assignPaperTests = assignPaperTestRepository.findAllForSummary(
            summaryRequestDto.getStudentId(), summaryRequestDto.getStartDate(),
            summaryRequestDto.getEndDate(), AssignStatus.COMPLETE);
        return createSummaryResponseDto(assignQuestions, assignPaperTests);
    }

    private SummaryResponseDto createSummaryResponseDto(List<AssignQuestions> assignQuestions,
        List<AssignPaperTest> assignPaperTests) {
        int assignQuestionTotalNum = 0;
        int assignQuestionTotalScore = 0;
        int assignQuestionScore = 0;
        List<SummaryAQResponseDto> solvedAQDto = new ArrayList<>();
        List<SummaryAQResponseDto> notSolvedAQDto = new ArrayList<>();
        List<SummaryPTResponseDto> solvedPTDto = assignPaperTests.stream().map(SummaryPTResponseDto::of).collect(
            Collectors.toList());

        for (AssignQuestions assignQuestion : assignQuestions) {
            if (assignQuestion.getAssignStatus() == AssignStatus.GRADED) {
                assignQuestionTotalNum += assignQuestion.getQuestionNum();
                assignQuestionTotalScore += assignQuestion.getTotalScore();
                assignQuestionScore += assignQuestion.getScore();
                solvedAQDto.add(SummaryAQResponseDto.of(assignQuestion));
                continue;
            }
            notSolvedAQDto.add(SummaryAQResponseDto.of(assignQuestion));
        }


        int assignPaperTotalNum = assignPaperTests.size();
        int assignPaperCorrectNum = (int) assignPaperTests.stream()
            .filter(Objects::nonNull) // ap가 null인지 체크
            .map(AssignPaperTest::getPaperTestAnswer)
            .filter(Objects::nonNull) // getPaperTestAnswer()가 null인지 체크
            .filter(answer -> Boolean.TRUE.equals(answer.getRightAnswer())) // getRightAnswer()가 null인 경우를 대비
            .count();

        int assignQuestionAverageScore = assignQuestionTotalScore == 0 ? 0 : (int) (
                ((double) assignQuestionScore / assignQuestionTotalScore) * 100);
        int assignPaperCorrectPercent = assignPaperTotalNum == 0 ? 0 : (int) (
                ((double) assignPaperCorrectNum / assignPaperTotalNum) * 100);

        return new SummaryResponseDto(assignQuestionTotalNum,
            assignQuestionAverageScore, assignPaperTotalNum,
            assignPaperCorrectPercent, solvedAQDto, notSolvedAQDto, solvedPTDto);
    }

    //간단 학생 조회 (반 생성, 퀴즈 등)
    public List<SimpleStudentsResponseDto> findAllStudents(String email) {
        return teacherRepository.getTeacherByEmail(email).getStudents().stream()
            .filter(s -> !s.getDeleted()).map(
                SimpleStudentsResponseDto::of)
            .collect(Collectors.toList());
    }

    //나의 반 목록 조회
    public List<TeamResponseDto> getMyGroups(String email) {
        return teacherRepository.getTeacherByEmail(email).getTeam().stream()
            .map(TeamResponseDto::of)
            .collect(Collectors.toList());
    }

    public List<TeacherAssignQuestionsResponseDto> getAssignQuestionPaper(Long studentId) {
        return assignQuestionRepository.findAllByStudentId(studentId).stream().map(TeacherAssignQuestionsResponseDto::of)
            .collect(Collectors.toList());
    }

    public List<TeacherAssignPaperTestsResponseDto> getAssignPaperTest(Long studentId) {
        return assignPaperTestRepository.findAllByStudentId(studentId).stream()
            .map(TeacherAssignPaperTestsResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAssignPaperTest(Long paperTestId) {
        assignPaperTestRepository.deleteById(paperTestId);
    }

    @Transactional
    public void deleteAssignQuestion(Long assignQuestionId) {
        assignQuestionRepository.deleteById(assignQuestionId);
    }

    @Transactional
    public void gradingQuestionPaper(GradingRequestDto gradingRequestDto) {
        Answer answer = answerRepository.getAnswerById(gradingRequestDto.getAnswerId());
        answer.setRightAnswer(gradingRequestDto.getRightAnswer());
        answer.getAssignQuestions().setGraded(gradingRequestDto.getRightAnswer(), answer.getScore());
    }

    @Transactional
    public void gradingPaperTest(GradingRequestDto gradingRequestDto) {
        PaperTestAnswer answer = paperTestAnswerRepository.getAnswerById(
            gradingRequestDto.getAnswerId());
        answer.setRightAnswer(gradingRequestDto.getRightAnswer());
    }
}
