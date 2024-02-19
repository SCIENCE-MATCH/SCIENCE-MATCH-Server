package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.student.AnswerResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignPaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignPaperTestSolveDto;
import com.sciencematch.sciencematch.DTO.student.AssignQuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignQuestionPaperSolveDto;
import com.sciencematch.sciencematch.DTO.student.PaperTestAnswerResponseDto;
import com.sciencematch.sciencematch.DTO.student.SolvedPaperTestDto;
import com.sciencematch.sciencematch.DTO.student.StudentMyPageDto;
import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTestAnswer;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestAnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AssignQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.question.ConnectQuestionRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AssignQuestionRepository assignQuestionRepository;
    private final AssignPaperTestRepository assignPaperTestRepository;
    private final ConnectQuestionRepository connectQuestionRepository;
    private final AnswerRepository answerRepository;
    private final PaperTestAnswerRepository paperTestAnswerRepository;


    public List<AssignQuestionPaperResponseDto> getMyQuestionPaper(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        return assignQuestionRepository.findAllByStudent(student).stream()
            .map(AssignQuestionPaperResponseDto::of).collect(Collectors.toList());
    }

    public List<Category> getQuestionPaperStructure(Long assignQuestionPaperId) {
        return assignQuestionRepository.getAssignQuestionsById(assignQuestionPaperId)
            .getAnswer().stream().map(Answer::getCategory).collect(Collectors.toList());
    }

    public List<AssignPaperTestResponseDto> getMyPaperTest(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        return assignPaperTestRepository.findAllByStudentAndAssignStatus(student, AssignStatus.WAITING).stream()
            .map(AssignPaperTestResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public void solveAssignQuestionPaper(AssignQuestionPaperSolveDto assignQuestionPaperSolveDto) {

        AssignQuestions questions = assignQuestionRepository.getAssignQuestionsById(assignQuestionPaperSolveDto.getAssignQuestionPaperId());

        List<Answer> answers = connectQuestionRepository.getAllConnectQuestionByQuestionPaper(
                questions.getQuestionPaper())
            .stream().map(cq -> Answer.builder()
                .solution(cq.getQuestion().getSolution())
                .solutionImg(cq.getQuestion().getSolutionImg())
                .category(cq.getQuestion().getCategory())
                .chapterId(cq.getQuestion().getChapterId())
                .score(cq.getScore())
                .build()).collect(Collectors.toList());

        List<String> solvingAnswer = assignQuestionPaperSolveDto.getAnswer();

        if (answers.size() != solvingAnswer.size()) throw new CustomException(ErrorStatus.INVALID_ANSWER_NUM_EXCEPTION, ErrorStatus.INVALID_ANSWER_NUM_EXCEPTION.getMessage());

        for (int i=0; i<(answers.size()); i++) {
            Answer answer = answers.get(i);
            answer.setSubmitAnswer(solvingAnswer.get(i));
            //제출한 답과 answer의 solution이 일치하면 정답처리
            if (Objects.equals(answer.getSolution(), solvingAnswer.get(i))) {
                answer.setRightAnswer(true);
                questions.plusScore(answer.getScore());
            }
            questions.plusTotalScore(answer.getScore());
        }

        questions.setAnswerAndAssignStatus(answers);
        answerRepository.saveAll(answers);

    }

    @Transactional
    public void solveAssignPaperTest(AssignPaperTestSolveDto assignPaperTestSolveDto) {
        AssignPaperTest assignPaperTest = assignPaperTestRepository.getAssignPaperTestById(
            assignPaperTestSolveDto.getAssignPaperTestId());
        PaperTest paperTest = assignPaperTest.getPaperTest();

        PaperTestAnswer answer = PaperTestAnswer.builder()
            .solution(paperTest.getSolution())
            .build();

        String solvingAnswer = assignPaperTestSolveDto.getAnswer();

        answer.setSubmitAnswer(solvingAnswer);

        if (solvingAnswer.equals(answer.getSolution())) {
            answer.setRightAnswer(true);
        }

        assignPaperTest.setPaperTestAnswerAndAssignStatus(answer);
        paperTestAnswerRepository.save(answer);
    }

    public SolvedPaperTestDto getCompleteQuestionPaper(Long questionId) {
        AssignQuestions assignQuestions = assignQuestionRepository.getAssignQuestionsById(
            questionId);
        long correctNum = assignQuestions.getAnswer().stream().filter(Answer::getRightAnswer).count();
        List<AnswerResponseDto> answerResponseDtos = assignQuestions.getAnswer().stream()
            .map(AnswerResponseDto::of).collect(
                Collectors.toList());
        return new SolvedPaperTestDto(assignQuestions.getScore(), assignQuestions.getTotalScore(),
            (int) correctNum, assignQuestions.getQuestionNum(),
            answerResponseDtos);
    }

    public PaperTestAnswerResponseDto getCompletePaperTest(Long paperTestId) {
        return PaperTestAnswerResponseDto.of(assignPaperTestRepository.getAssignPaperTestById(paperTestId).getPaperTestAnswer());
    }

    public StudentMyPageDto getMypage(String phoneNum) {
        return StudentMyPageDto.of(studentRepository.getStudentByPhoneNum(phoneNum));
    }

}
