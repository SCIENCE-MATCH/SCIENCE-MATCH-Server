package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperDetailDto;
import com.sciencematch.sciencematch.dto.student.AnswerResponseDto;
import com.sciencematch.sciencematch.dto.student.AssignPaperTestResponseDto;
import com.sciencematch.sciencematch.dto.student.AssignPaperTestSolveDto;
import com.sciencematch.sciencematch.dto.student.AssignQuestionPaperResponseDto;
import com.sciencematch.sciencematch.dto.student.AssignQuestionPaperSolveDto;
import com.sciencematch.sciencematch.dto.student.PaperTestAnswerResponseDto;
import com.sciencematch.sciencematch.dto.student.SolvedQuestionPaperDto;
import com.sciencematch.sciencematch.dto.student.StudentMyPageDto;
import com.sciencematch.sciencematch.dto.student.StudentSummaryDto;
import com.sciencematch.sciencematch.enums.AssignStatus;
import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTestAnswer;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestAnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AssignQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.question.ConnectQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final ChapterRepository chapterRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PaperTestAnswerRepository paperTestAnswerRepository;


    public List<AssignQuestionPaperResponseDto> getMyQuestionPaper(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        return assignQuestionRepository.findAllByStudent(student).stream()
            .map(AssignQuestionPaperResponseDto::of).collect(Collectors.toList());
    }

    public QuestionPaperDetailDto getQuestionPaperDetail(Long assignQuestionPaperId) {
        AssignQuestions assignQuestions = assignQuestionRepository.getAssignQuestionsById(
            assignQuestionPaperId);
        List<Category> categories = connectQuestionRepository.getAllConnectQuestionByQuestionPaper(
                assignQuestions.getQuestionPaper()).stream().map(cq -> cq.getQuestion().getCategory())
            .collect(Collectors.toList());

        return QuestionPaperDetailDto.of(assignQuestions, categories);
    }

    public List<AssignPaperTestResponseDto> getMyPaperTest(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        return assignPaperTestRepository.findAllByStudentAndAssignStatus(student, AssignStatus.WAITING).stream()
            .map(AssignPaperTestResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public void solveAssignQuestionPaper(AssignQuestionPaperSolveDto assignQuestionPaperSolveDto) {
        //출제한 학습지 불러오기
        AssignQuestions questions = assignQuestionRepository.getAssignQuestionsById(assignQuestionPaperSolveDto.getAssignQuestionPaperId());
        //학습지에 연결된 문제들을 불러오기 + answer 객체로 변환
        List<Answer> answers = connectQuestionRepository.getAllConnectQuestionByQuestionPaper(
                questions.getQuestionPaper())
            .stream().map(cq -> Answer.builder()
                .solution(cq.getQuestion().getSolution())
                .solutionImg(cq.getQuestion().getSolutionImg())
                .category(cq.getQuestion().getCategory())
                .chapterId(cq.getQuestion().getChapterId())
                .score(cq.getScore())
                .questionId(cq.getQuestion().getId())
                .questionImg(cq.getQuestion().getImage())
                .assignQuestions(questions)
                .build()).collect(Collectors.toList());

        answerRepository.saveAll(answers);

        // 제출한 답
        List<String> solvingAnswer = assignQuestionPaperSolveDto.getAnswer();

        if (answers.size() != solvingAnswer.size()) throw new CustomException(ErrorStatus.INVALID_ANSWER_NUM_EXCEPTION, ErrorStatus.INVALID_ANSWER_NUM_EXCEPTION.getMessage());

        for (int i=0; i<(answers.size()); i++) {
            Answer answer = answers.get(i);
            answer.setSubmitAnswer(solvingAnswer.get(i));
            //제출한 답과 answer의 solution이 일치하면 정답처리
            if (Objects.equals(answer.getSolution(), solvingAnswer.get(i))) {
                answer.setRightAnswer(true);
                questions.setScore(true, answer.getScore());
            }
            questions.plusTotalScore(answer.getScore());
        }

        questions.setAnswerAndAssignStatus(answers, questions.getQuestionPaper().getCategory());
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

    public SolvedQuestionPaperDto getCompleteQuestionPaper(Long questionId) {
        AssignQuestions assignQuestions = assignQuestionRepository.getAssignQuestionsById(
            questionId);
        long correctNum = assignQuestions.getAnswer().stream().filter(Answer::getRightAnswer).count();

        List<AnswerResponseDto> answerResponseDtos = new ArrayList<>();
        for (Answer answer : assignQuestions.getAnswer()) {
            Question question = questionRepository.getQuestionById(answer.getQuestionId());
            Chapter chapter = chapterRepository.getChapterById(answer.getChapterId());
            answerResponseDtos.add(AnswerResponseDto.of(answer, question, chapter));
        }

        return new SolvedQuestionPaperDto(assignQuestions.getScore(), assignQuestions.getTotalScore(),
            (int) correctNum, assignQuestions.getQuestionNum(), assignQuestions.getCreateAt(),
            answerResponseDtos);
    }

    public PaperTestAnswerResponseDto getCompletePaperTest(Long paperTestId) {
        return PaperTestAnswerResponseDto.of(assignPaperTestRepository.getAssignPaperTestById(paperTestId).getPaperTestAnswer());
    }

    public StudentMyPageDto getMypage(String phoneNum) {
        return StudentMyPageDto.of(studentRepository.getStudentByPhoneNum(phoneNum));
    }

    public StudentSummaryDto getSummary(String phoneNum) {
    Student student = studentRepository.getStudentByPhoneNum(phoneNum);

    List<AssignQuestions> assignQuestions = assignQuestionRepository.findAllByStudentId(student.getId());

    int totalQuestions = 0;
    int correctAnswers = 0;
    int ungradedAnswers = 0;

    List<Integer> quesNumPerWeek = new ArrayList<>();
    List<Integer> correctNumPerWeek = new ArrayList<>();
    List<Integer> ungradedNumPerWeek = new ArrayList<>();

    List<Double> accuracyPerLevel = new ArrayList<>();

    LocalDateTime now = LocalDateTime.now();

    // ✅ 최근 4주간 주간 통계
    for (int week = 0; week < 4; week++) {
        int weeklyQuesNum = 0;
        int weeklyCorrectNum = 0;
        int weeklyUngradedNum = 0;

        for (AssignQuestions aq : assignQuestions) {
            if (aq.getAssignStatus() == AssignStatus.WAITING || aq.getAssignStatus() == AssignStatus.SOLVING) {
                continue; // 제외
            }

            if (aq.getUpdatedAt().isAfter(now.minusWeeks(week + 1)) &&
                aq.getUpdatedAt().isBefore(now.minusWeeks(week))) {

                weeklyQuesNum += aq.getQuestionNum();

                if (aq.getAssignStatus() == AssignStatus.COMPLETE) {
                    weeklyUngradedNum += aq.getQuestionNum();
                }

                if (aq.getAssignStatus() == AssignStatus.GRADED) {
                    weeklyCorrectNum += (int) aq.getAnswer().stream().filter(Answer::getRightAnswer).count();
                }
            }
        }

        quesNumPerWeek.add(weeklyQuesNum);
        correctNumPerWeek.add(weeklyCorrectNum);
        ungradedNumPerWeek.add(weeklyUngradedNum);
    }

    // ✅ 난이도별 정확도 (최근 한 달 + 채점 완료된 문제만)
    Level[] levels = {Level.HARD, Level.MEDIUM_HARD, Level.MEDIUM, Level.MEDIUM_LOW, Level.LOW};
    LocalDateTime oneMonthAgo = now.minusMonths(1);
    for (Level level : levels) {
        int levelTotalQues = 0;
        int levelCorrectQues = 0;

        for (AssignQuestions aq : assignQuestions) {
            if (aq.getAssignStatus() != AssignStatus.GRADED) continue;
            if (aq.getUpdatedAt().isBefore(oneMonthAgo)) continue; // 한 달 이전 데이터 제외

            for (Answer answer : aq.getAnswer()) {
                Question question = questionRepository.getQuestionById(answer.getQuestionId());
                if (question.getLevel() == level) {
                    levelTotalQues++;
                    if (answer.getRightAnswer()) {
                        levelCorrectQues++;
                    }
                }
            }
        }

        double accuracy = levelTotalQues == 0 ? 0.0 :
            (double) levelCorrectQues / levelTotalQues * 100;
        accuracyPerLevel.add(Math.round(accuracy * 100.0) / 100.0);
    }


    // ✅ 전체 통계
    LocalDateTime oneWeekAgo = now.minusWeeks(1);
    for (AssignQuestions aq : assignQuestions) {
        if (aq.getAssignStatus() == AssignStatus.WAITING || aq.getAssignStatus() == AssignStatus.SOLVING || aq.getUpdatedAt().isBefore(oneWeekAgo)) {
            continue;
        }

        totalQuestions += aq.getQuestionNum();

        if (aq.getAssignStatus() == AssignStatus.COMPLETE) {
            ungradedAnswers += aq.getQuestionNum();
        }

        if (aq.getAssignStatus() == AssignStatus.GRADED) {
            correctAnswers += (int) aq.getAnswer().stream().filter(Answer::getRightAnswer).count();
        }
    }

    return new StudentSummaryDto(
        totalQuestions,
        correctAnswers,
        ungradedAnswers,
        quesNumPerWeek,
        correctNumPerWeek,
        ungradedNumPerWeek,
        accuracyPerLevel
    );
}

}
